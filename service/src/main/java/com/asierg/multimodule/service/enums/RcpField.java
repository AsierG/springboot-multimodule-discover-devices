package com.asierg.multimodule.service.enums;

public enum RcpField {

    SERIAL_NUMBER("main/serialnumber"),
    MAIN_PRODUCT("main/product"),
    MAIN_VERSION("main/version");

    private final String value;

    public String getRequestString() {
        return "get /" + this.value;
    }

    public String getFieldName() {
        return this.value;
    }

    RcpField(String value) {
        this.value = value;
    }
}
