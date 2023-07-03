package io.github.panxiaochao.operate.log.core.aspect;

import io.github.panxiaochao.operate.log.core.annotation.OperateLog;
import io.github.panxiaochao.operate.log.core.context.MethodCostContext;
import io.github.panxiaochao.operate.log.utils.OperateLogUtil;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;

/**
 * {@code OperateLogAspect}
 * <p> 操作日志 Aspect处理
 *
 * @author Lypxc
 * @since 2023-07-03
 */
@Aspect
@Order(1)
public class OperateLogAspect {

    private static final Logger LOGGER = LoggerFactory.getLogger(OperateLogAspect.class);

    public OperateLogAspect() {
        LOGGER.info("配置[RepeatSubmitLimiterAspect]成功！");
    }

    /**
     * 前置拦截
     */
    @Before("@annotation(operatorLog)")
    public void before(JoinPoint joinPoint, OperateLog operatorLog) {
        // 设置请求方法执行开始时间
        MethodCostContext.setMethodCostTime(System.currentTimeMillis());
    }

    /**
     * 返回拦截
     */
    @AfterReturning(pointcut = "@annotation(operatorLog)", returning = "returnValue")
    public void afterReturning(JoinPoint joinPoint, OperateLog operatorLog, Object returnValue) {
        OperateLogUtil.handleOperateLog(joinPoint, operatorLog, returnValue, null);
    }

    /**
     * 错误异常拦截
     */
    @AfterThrowing(pointcut = "@annotation(operatorLog)", throwing = "ex")
    public void afterThrowing(JoinPoint joinPoint, OperateLog operatorLog, Exception ex) {
        OperateLogUtil.handleOperateLog(joinPoint, operatorLog, null, ex);
    }
}
