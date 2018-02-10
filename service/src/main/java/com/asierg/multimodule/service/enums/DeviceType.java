package com.asierg.multimodule.service.enums;

public enum DeviceType {

    TYPE_1("uSysCom"),
    TYPE_2("ajr");

    private final String type;

    public String getType() {
        return this.type;
    }

    DeviceType(String type) {
        this.type = type;
    }
}
