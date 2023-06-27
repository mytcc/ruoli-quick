package ruoli.work.core.advice;

import org.apache.skywalking.apm.toolkit.trace.TraceContext;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;
import ruoli.work.core.annotation.IgnoreResponse;
import ruoli.work.core.entity.CommonResponse;


/**
 * <h1>实现统一响应</h1>
 * */
@RestControllerAdvice(basePackages = "ruoli.work")
public class CommonResponseDataAdvice implements ResponseBodyAdvice<Object> {

    /**
     * <h2>判断是否需要对响应进行处理</h2>
     * */
    @Override
    @SuppressWarnings("all")
    public boolean supports(MethodParameter methodParameter,
                            Class<? extends HttpMessageConverter<?>> aClass) {

        if (methodParameter.getDeclaringClass()
                .isAnnotationPresent(IgnoreResponse.class)) {
            return false;
        }

        if (methodParameter.getMethod().isAnnotationPresent(IgnoreResponse.class)) {
            return false;
        }

        return true;
    }

    @Override
    @SuppressWarnings("all")
    public Object beforeBodyWrite(Object o, MethodParameter methodParameter,
                                  MediaType mediaType,
                                  Class<? extends HttpMessageConverter<?>> aClass,
                                  ServerHttpRequest serverHttpRequest,
                                  ServerHttpResponse serverHttpResponse) {

        // 定义最终的返回对象
        CommonResponse<Object> response = new CommonResponse<>(200, "all operations completed.");

        if (null == o) {
            response.setTraceId(TraceContext.traceId());
            return response;
        } else if (o instanceof CommonResponse) {
            response = (CommonResponse<Object>) o;
            response.setTraceId(TraceContext.traceId());
        } else {
            response.setData(o);
        }
        response.setTraceId(TraceContext.traceId());
        return response;
    }
}
