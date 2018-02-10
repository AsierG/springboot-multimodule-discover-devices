package com.asierg.multimodule.service.exceptions;

public class RcpException extends RuntimeException {

    public RcpException(Throwable e) {
        super(e);
    }

    public RcpException(String s) {
        super(s);
    }
}
