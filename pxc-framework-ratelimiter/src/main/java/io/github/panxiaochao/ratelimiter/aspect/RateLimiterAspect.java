package io.github.panxiaochao.ratelimiter.aspect;

import io.github.panxiaochao.core.exception.ServerRuntimeException;
import io.github.panxiaochao.core.ienums.IEnum;
import io.github.panxiaochao.core.utils.RequestUtil;
import io.github.panxiaochao.core.utils.StrUtil;
import io.github.panxiaochao.core.utils.StringPoolUtil;
import io.github.panxiaochao.ratelimiter.annotation.RateLimiter;
import io.github.panxiaochao.redis.utils.RedissonUtil;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.redisson.api.RateType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.DefaultParameterNameDiscoverer;
import org.springframework.core.ParameterNameDiscoverer;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.ParserContext;
import org.springframework.expression.common.TemplateParserContext;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.util.DigestUtils;
import org.springframework.util.StringUtils;

import java.lang.reflect.Method;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

/**
 * {@code RateLimiterAspect}
 * <p> 限流 Aspect 处理
 *
 * @author Lypxc
 * @since 2023-06-28
 */
@Aspect
public class RateLimiterAspect {

    private static final Logger LOGGER = LoggerFactory.getLogger(RateLimiterAspect.class);

    public RateLimiterAspect() {
        LOGGER.info("配置[RateLimiterAspect]成功！");
    }

    /**
     * 限流 redis key
     */
    private static final String RATE_LIMITER_KEY = "rate_limiter:";

    /**
     * 定义EL表达式解析器
     */
    private final ExpressionParser expressionParser = new SpelExpressionParser();
    /**
     * 定义EL解析模版
     */
    private final ParserContext parserContext = new TemplateParserContext();
    /**
     * 定义EL上下文对象进行解析
     */
    private final EvaluationContext evaluationContext = new StandardEvaluationContext();
    /**
     * 方法参数解析器
     */
    private final ParameterNameDiscoverer parameterNameDiscoverer = new DefaultParameterNameDiscoverer();

    @Before("@annotation(rateLimiter)")
    public void before(JoinPoint joinPoint, RateLimiter rateLimiter) {
        int maxCount = rateLimiter.maxCount();
        int limitSecond = rateLimiter.limitSecond();
        // 后去限流 KEY
        String rateLimiterKey = getRateLimiterKey(joinPoint, rateLimiter);
        // RateType.OVERALL 全局限流
        // RateType.PER_CLIENT 客户端单独计算限流
        long availableCount = RedissonUtil.INSTANCE().setRateLimiter(rateLimiterKey, RateType.OVERALL, maxCount, limitSecond);
        if (availableCount == -1) {
            throw new ServerRuntimeException(RateLimiterErrorEnum.RATE_LIMITER_ERROR);
        }
        LOGGER.info("缓存key: {}, 限制数: {}, 剩余数: {}", rateLimiterKey, maxCount, availableCount);
    }

    /**
     * 获取限流 key
     *
     * @param joinPoint   joinPoint
     * @param rateLimiter rateLimiter
     * @return obtain the key
     */
    private String getRateLimiterKey(JoinPoint joinPoint, RateLimiter rateLimiter) {
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        Method method = methodSignature.getMethod();
        String key = rateLimiter.key();
        String classMethodName = method.getDeclaringClass().getName() + "." + method.getName();
        String combineKey = RequestUtil.ofRequestIp() + "." + classMethodName;
        combineKey = DigestUtils.md5DigestAsHex(combineKey.getBytes(StandardCharsets.UTF_8));
        if (StrUtil.containsAny(key, StringPoolUtil.HASH)) {
            // 参数
            Object[] args = joinPoint.getArgs();
            // 获取方法上参数的名称
            String[] parameterNames = parameterNameDiscoverer.getParameterNames(method);
            Objects.requireNonNull(parameterNames, "限流Key解析异常！");
            for (int i = 0; i < parameterNames.length; i++) {
                evaluationContext.setVariable(parameterNames[i], args[i]);
            }
            Expression expression;
            if (StringUtils.startsWithIgnoreCase(key, parserContext.getExpressionPrefix())
                    && StringUtils.endsWithIgnoreCase(key, parserContext.getExpressionSuffix())) {
                expression = expressionParser.parseExpression(key, parserContext);
            } else {
                expression = expressionParser.parseExpression(key);
            }
            key = expression.getValue(evaluationContext, String.class) + ":";
        } else {
            key = StrUtil.EMPTY;
        }
        return RATE_LIMITER_KEY + key + combineKey;
    }

    /**
     * 限流错误码
     */
    @Getter
    @AllArgsConstructor
    enum RateLimiterErrorEnum implements IEnum<Integer> {
        /**
         * 请求频繁，请过会儿再试
         */
        RATE_LIMITER_ERROR(60001, "访问过于频繁，请稍后再试!");

        private final Integer code;

        private final String message;
    }
}
