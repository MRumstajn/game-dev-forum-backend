package com.mrumstajn.gamedevforum.common.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class BaseResponseWrapper<T> {
    private T data;

    private String errorMessage;

    private ErrorCode errorCode;

    public BaseResponseWrapper(T data){
        this.data = data;
    }
}
