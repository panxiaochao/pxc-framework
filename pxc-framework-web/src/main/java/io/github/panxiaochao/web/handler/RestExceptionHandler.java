package io.github.panxiaochao.web.handler;

import io.github.panxiaochao.core.enums.CommonResponseEnum;
import io.github.panxiaochao.core.exception.BaseException;
import io.github.panxiaochao.core.exception.BaseRuntimeException;
import io.github.panxiaochao.core.exception.ext.ApiServerException;
import io.github.panxiaochao.core.response.R;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * {@code RestExceptionHandler}
 * <p> 统一异常处理器类增强, 默认不拦截4xx错误, 比如(400,405,404)等
 *
 * @author Lypxc
 * @since 2023-06-26
 */
@RestControllerAdvice
public class RestExceptionHandler {
    private static final Logger LOG = LoggerFactory.getLogger(RestExceptionHandler.class);

    /**
     * 生产环境
     */
    private final static String ENV_PROD = "prod";

    /**
     * 当前环境
     */
    @Value("${spring.profiles.active:}")
    private String profile;

    /**
     * 常规兜底报错
     *
     * @param e Exception
     * @return R
     */
    @ExceptionHandler(value = Exception.class)
    public R<String> exception(Exception e) {
        LOG.error(e.getMessage(), e);
        if (ENV_PROD.equals(profile)) {
            return R.fail(CommonResponseEnum.INTERNAL_SERVER_ERROR.getMessage(), null);
        }
        return R.fail(e.getMessage(), null);
    }

    /**
     * 常规业务异常
     *
     * @param e 异常
     * @return 异常结果
     */
    @ExceptionHandler(value = ApiServerException.class)
    public R<String> handleBusinessException(BaseException e) {
        LOG.error(e.getMessage(), e);
        return R.fail(e.getMessage(), null);
    }

    /**
     * 自定义异常 BaseException
     *
     * @param e 异常
     * @return 异常结果
     */
    @ExceptionHandler(value = BaseException.class)
    public R<String> handleBaseException(BaseException e) {
        LOG.error(e.getMessage(), e);
        return R.fail(e.getResponseEnum().getCode(), e.getMessage(), null);
    }

    /**
     * 自定义异常 BaseRuntimeException
     *
     * @param e 异常
     * @return 异常结果
     */
    @ExceptionHandler(value = BaseRuntimeException.class)
    public R<String> handleBaseException(BaseRuntimeException e) {
        LOG.error(e.getMessage(), e);
        return R.fail(e.getResponseEnum().getCode(), e.getMessage(), null);
    }

    /**
     * 参数绑定异常
     *
     * @param e 异常
     * @return 异常结果
     */
    @ExceptionHandler(value = BindException.class)
    public R<String> handleBindException(BindException e) {
        LOG.error(e.getMessage(), e);
        return wrapperBindingResult(e.getBindingResult());
    }

    /**
     * 参数校验异常，将校验失败的所有异常组合成一条错误信息
     *
     * @param e 异常
     * @return 异常结果
     */
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public R<String> handleValidException(MethodArgumentNotValidException e) {
        LOG.error(e.getMessage(), e);
        return wrapperBindingResult(e.getBindingResult());
    }

    /**
     * 包装绑定异常结果
     *
     * @param bindingResult 绑定结果
     * @return 异常结果
     */
    private R<String> wrapperBindingResult(BindingResult bindingResult) {
        StringBuilder msg = new StringBuilder();
        for (ObjectError error : bindingResult.getAllErrors()) {
            msg.append(", ");
            if (error instanceof FieldError) {
                msg.append(((FieldError) error).getField()).append(": ");
            }
            msg.append(error.getDefaultMessage() == null ? "" : error.getDefaultMessage());
        }
        return R.fail(CommonResponseEnum.INTERNAL_SERVER_ERROR.getCode(), msg.substring(2), null);
    }
}
