package ruoli.work.common.advice;

import ruoli.work.common.entity.CommonException;
import ruoli.work.common.entity.CommonResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ruoli.work.common.util.ExceptionUtil;

import javax.servlet.http.HttpServletRequest;

/**
 * <h2>全局异常捕获处理</h2>
 * */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionAdvice {

    @ExceptionHandler(value = Exception.class)
    public CommonResponse<String> handlerCommerceException(
            HttpServletRequest req, Exception ex) {

        CommonResponse<String> response = new CommonResponse<>(
                500, ex.getMessage()
        );
        if(ex instanceof CommonException){
            CommonException exception=(CommonException) ex;
            response = new CommonResponse<>(
                    exception.getErrorCode(),exception.getErrorMsg()
            );
        }
        response.setData(ExceptionUtil.getStackTrace(ex));
        log.error("commerce service has error: [{}]", ex.getMessage(), ex);
        return response;
    }
}