package com.devteria.profile.exception;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class AppException extends RuntimeException {

    public AppException(ERROR_CODE errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }

    private ERROR_CODE errorCode;

}
