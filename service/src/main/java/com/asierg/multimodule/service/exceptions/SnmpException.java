package com.asierg.multimodule.service.exceptions;

public class SnmpException extends RuntimeException {

    public SnmpException(Throwable e) {
        super(e);
    }

    public SnmpException(String s) {
        super(s);
    }
}
