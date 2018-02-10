package com.asierg.multimodule.service.enums;

public enum ReflashResultCode {

    OK_RESPONSE(200),
    NOT_FOUND_RESPONSE(401),
    UNKNOWN_RESPONSE(-1),
    NO_CONNECTION_RESPONSE(null);

    private final Integer code;

    public Integer getCode() {
        return this.code;
    }

    ReflashResultCode(Integer code) {
        this.code = code;
    }

    public static ReflashResultCode getReflashResultCode(Integer code) {
        if (OK_RESPONSE.code.equals(code)) {
            return OK_RESPONSE;
        } else if (NOT_FOUND_RESPONSE.code.equals(code)) {
            return NOT_FOUND_RESPONSE;
        }
        return UNKNOWN_RESPONSE;
    }

}
