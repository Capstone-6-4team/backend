package com.example.capstone2.common.response;

import lombok.Getter;

@Getter
public enum ResultCode {
    OK(200, "성공"),

    BAD_REQUEST(400, "잘못된 요청입니다."),

    UNAUTHORIZED(401, "인증되지 않았습니다."),

    FORBIDDEN(403, "권한이 없습니다.");

    int code;
    String message;

    ResultCode(int code, String message) {
        this.code = code;
        this.message = message;
    }
}
