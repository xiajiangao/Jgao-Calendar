package top.jgao.advice;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;
import top.jgao.basic.Constants;
import top.jgao.basic.Result;

@ControllerAdvice
@Slf4j
public class CustomerResponseBodyAdvice implements ResponseBodyAdvice {

    @Override
    public boolean supports(MethodParameter returnType, Class converterType) {
        //false 为不拦截，true为拦截。可更具需求更改
        return true;
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType, Class selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {
        if (body instanceof Result) {
            ServletServerHttpRequest req = (ServletServerHttpRequest) request;
            ((Result) body).setRequestId(String.valueOf(req.getServletRequest().getAttribute(Constants.REQUEST_ID_KEY)));
            ((Result) body).setResultServerTime(System.currentTimeMillis());
            return body;
        } else {
            return body;
        }
    }
}
