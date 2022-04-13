package com.example.capstone2.common.response;

import lombok.Getter;

@Getter
public class ApiResponse<T> {
    private final int statusCode;
    private final ResultCode code;
    private final String message;
    private final T data;

    private ApiResponse(ResultCode code, String message, T data) {
        this.statusCode = code.getCode();
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public static <T> ApiResponse<T> success() {
        return new ApiResponse<>(
                ResultCode.OK,
                ResultCode.OK.getMessage(),
                null
        );
    }

    public static <T> ApiResponse<T> success(T data) {
        return new ApiResponse<>(
                ResultCode.OK,
                ResultCode.OK.getMessage(),
                data
        );
    }

    public static <T> ApiResponse<T> failure(ResultCode code) {
        return new ApiResponse<>(
                code,
                code.getMessage(),
                null
        );
    }

    public static <T> ApiResponse<T> failure(ResultCode code, String message) {
        return new ApiResponse<>(
                code,
                message,
                null
        );
    }
}
