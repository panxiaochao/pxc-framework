package io.github.panxiaochao.repeatsubmit.aspect;

import com.alibaba.ttl.TransmittableThreadLocal;
import io.github.panxiaochao.core.exception.ServerRuntimeException;
import io.github.panxiaochao.core.ienums.IEnum;
import io.github.panxiaochao.core.response.R;
import io.github.panxiaochao.core.utils.JacksonUtil;
import io.github.panxiaochao.core.utils.ObjectUtil;
import io.github.panxiaochao.redis.utils.RedissonUtil;
import io.github.panxiaochao.repeatsubmit.annotation.RepeatSubmitLimiter;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.util.DigestUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.Collection;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * {@code RepeatSubmitLimiterAspect}
 * <p> 重复提交 Aspect 处理
 *
 * @author Lypxc
 * @since 2023-06-29
 */
@Aspect
@Order(1)
public class RepeatSubmitLimiterAspect {

    private static final Logger LOGGER = LoggerFactory.getLogger(RepeatSubmitLimiterAspect.class);

    private static final TransmittableThreadLocal<String> CACHE_KEY_SET = new TransmittableThreadLocal<>();

    public RepeatSubmitLimiterAspect() {
        LOGGER.info("配置[RepeatSubmitLimiterAspect]成功！");
    }

    /**
     * 限重复提交 redis key
     */
    private static final String RATE_LIMITER_KEY = "repeat_submit_limiter:";

    @Before("@annotation(repeatSubmitLimiter)")
    public void before(JoinPoint joinPoint, RepeatSubmitLimiter repeatSubmitLimiter) {
        long interval = repeatSubmitLimiter.interval();
        TimeUnit timeUnit = repeatSubmitLimiter.timeUnit();
        if (timeUnit.toMillis(interval) < 1000) {
            throw new ServerRuntimeException(RepeatSubmitLimiterErrorEnum.REPEAT_SUBMIT_LIMITER_TIME_ERROR);
        }
        // 获取限重复提交KEY
        String repeatSubmitLimiterKey = getRepeatSubmitLimiterKey(joinPoint, repeatSubmitLimiter);
        String rslValue = RedissonUtil.INSTANCE().get(repeatSubmitLimiterKey);
        if (Objects.isNull(rslValue)) {
            RedissonUtil.INSTANCE().set(repeatSubmitLimiterKey, "", Duration.ofMillis(interval));
            CACHE_KEY_SET.set(repeatSubmitLimiterKey);
        } else {
            throw new ServerRuntimeException(RepeatSubmitLimiterErrorEnum.REPEAT_SUBMIT_LIMITER_ERROR);
        }
    }

    /**
     * 处理完请求, 释放缓存
     *
     * @param joinPoint           joinPoint
     * @param repeatSubmitLimiter repeatSubmitLimiter
     * @param returnValue         returnValue
     */
    @AfterReturning(pointcut = "@annotation(repeatSubmitLimiter)", returning = "returnValue")
    public void doAfterReturning(JoinPoint joinPoint, RepeatSubmitLimiter repeatSubmitLimiter, Object returnValue) {
        try {
            if (returnValue instanceof R) {
                R r = (R) returnValue;
                // 请求成功后不删除操作，保存还在有效时间内继续防止重复提交
                if (R.isFail(r)) {
                    RedissonUtil.INSTANCE().delete(CACHE_KEY_SET.get());
                }
            }
        } finally {
            // 不管是不是R类型返回，最后统一清除，以免内存泄漏
            CACHE_KEY_SET.remove();
        }
    }

    /**
     * 接口异常，直接移除缓存
     *
     * @param joinPoint           joinPoint
     * @param repeatSubmitLimiter repeatSubmitLimiter
     * @param ex                  ex
     */
    @AfterThrowing(pointcut = "@annotation(repeatSubmitLimiter)", throwing = "ex")
    public void afterThrowing(JoinPoint joinPoint, RepeatSubmitLimiter repeatSubmitLimiter, Exception ex) {
        RedissonUtil.INSTANCE().delete(CACHE_KEY_SET.get());
        CACHE_KEY_SET.remove();
    }

    private String getRepeatSubmitLimiterKey(JoinPoint joinPoint, RepeatSubmitLimiter repeatSubmitLimiter) {
        Object target = joinPoint.getTarget();
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        // 参数
        Object[] args = joinPoint.getArgs();
        // Method
        Method method = methodSignature.getMethod();
        // 设置方法名称
        String className = target.getClass().getName();
        String methodName = method.getName();
        String classMethodName = className + "." + methodName;
        // 请求地址
        // String requestUrl = RequestUtil.getRequest().getRequestURI();
        // 拼接参数
        String argsString = argsArrayToString(args);
        // 是否自定义请求头
        // if (StringUtils.hasText(repeatSubmitLimiter.headerName())) {
        //     String headerName = RequestUtil.getRequest().getHeader(repeatSubmitLimiter.headerName());
        //     if (StringUtils.hasText(headerName )){
        //         argsString += "."+ headerName;
        //     }
        // }
        String combineKey = DigestUtils.md5DigestAsHex((classMethodName + "." + argsString).getBytes(StandardCharsets.UTF_8));
        return RATE_LIMITER_KEY + combineKey;

    }

    /**
     * 参数拼装
     */
    private static String argsArrayToString(Object[] args) {
        StringBuilder params = new StringBuilder();
        if (ObjectUtil.isNotEmpty(args)) {
            for (Object object : args) {
                if (ObjectUtil.isNotEmpty(object) && !isFilterObject(object)) {
                    params.append(JacksonUtil.toString(object)).append(" ");
                }
            }
        }
        return params.toString();
    }

    /**
     * 判断是否需要过滤的对象。
     *
     * @param o 对象信息。
     * @return 如果是需要过滤的对象，则返回true；否则返回false。
     */
    private static boolean isFilterObject(final Object o) {
        Class<?> clazz = o.getClass();
        if (clazz.isArray()) {
            return clazz.getComponentType().isAssignableFrom(MultipartFile.class);
        } else if (Collection.class.isAssignableFrom(clazz)) {
            Collection<?> collection = (Collection<?>) o;
            for (Object value : collection) {
                return value instanceof MultipartFile;
            }
        } else if (Map.class.isAssignableFrom(clazz)) {
            Map map = (Map) o;
            for (Object value : map.entrySet()) {
                Map.Entry entry = (Map.Entry) value;
                return entry.getValue() instanceof MultipartFile;
            }
        }
        return (o instanceof MultipartFile ||
                o instanceof HttpServletRequest ||
                o instanceof HttpServletResponse ||
                o instanceof BindingResult);
    }


    /**
     * 限重复提交错误码
     */
    @Getter
    @AllArgsConstructor
    enum RepeatSubmitLimiterErrorEnum implements IEnum<Integer> {
        /**
         * 重复提交间隔时间设置不能小于1秒
         */
        REPEAT_SUBMIT_LIMITER_TIME_ERROR(6051, "重复提交间隔时间设置不能小于1秒！"),
        /**
         * 限流KEY解析异常
         */
        REPEAT_SUBMIT_LIMITER_ERROR(6052, "不允许重复提交，请稍后再试!");

        private final Integer code;

        private final String message;
    }

}