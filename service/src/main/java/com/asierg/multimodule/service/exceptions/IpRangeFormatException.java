package com.asierg.multimodule.service.exceptions;

public class IpRangeFormatException extends RuntimeException {

    public IpRangeFormatException(Throwable e) {
        super(e);
    }

    public IpRangeFormatException(String s) {
        super(s);
    }
}