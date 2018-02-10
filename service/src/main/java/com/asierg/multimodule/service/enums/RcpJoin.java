package com.asierg.multimodule.service.enums;

/*
Join: If it's value is 1 the next message is part of this one,
if it is 0, this is the last fragment of the message.
* */
public enum RcpJoin {

    LAST_FRAGMENT((byte) 0),
    MORE_FRAGMENT((byte) 1);

    private final byte fragement;

    public byte getFragement() {
        return this.fragement;
    }

    RcpJoin(byte fragement) {
        this.fragement = fragement;
    }
}
