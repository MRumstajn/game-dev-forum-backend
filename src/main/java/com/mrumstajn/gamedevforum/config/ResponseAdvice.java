package com.mrumstajn.gamedevforum.config;

import com.mrumstajn.gamedevforum.common.response.BaseResponseWrapper;
import com.mrumstajn.gamedevforum.common.response.ErrorResponse;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

@ControllerAdvice
public class ResponseAdvice implements ResponseBodyAdvice<Object> {
    @SuppressWarnings("NullableProblems")
    @Override
    public boolean supports(MethodParameter returnType, Class converterType) {
        return true;
    }

    @SuppressWarnings("NullableProblems")

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType,
                                  Class selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {

        if (body instanceof ErrorResponse errorResponse){
            BaseResponseWrapper<Object> wrapper = new BaseResponseWrapper<>();
            wrapper.setData(null);
            wrapper.setErrorMessage(errorResponse.getError());
            wrapper.setErrorCode(errorResponse.getErrorCode());

            return wrapper;
        }

        return new BaseResponseWrapper<>(body);
    }
}
