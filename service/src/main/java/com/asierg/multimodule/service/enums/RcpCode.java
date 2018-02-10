package com.asierg.multimodule.service.enums;

/*
 * Code:The code determines the type of the message,
 * depending on this there can be more fields in the data field.
 * */
public enum RcpCode {

    REQUEST_MESSAGE((byte) 0),
    RESPONSE_MESSAGE((byte) 1),
    REFLASH_MESSAGE((byte) 2);

    private final byte code;

    public byte getCode() {
        return this.code;
    }

    RcpCode(byte code) {
        this.code = code;
    }
}
