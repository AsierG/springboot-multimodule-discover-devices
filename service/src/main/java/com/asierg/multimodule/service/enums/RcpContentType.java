package com.asierg.multimodule.service.enums;

public enum RcpContentType {

    USYSCOM_REMOTE_CONF("x-usyscom-remoteconf"),
    REMOTE_CONF("x-remote-conf");

    private final String type;

    public String getType() {
        return this.type;
    }

    RcpContentType(String type) {
        this.type = type;
    }
}
