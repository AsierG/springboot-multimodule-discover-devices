package com.asierg.multimodule.service.dto;

import com.asierg.multimodule.service.enums.RcpResultCode;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

public class RcpResponse {

    private byte code;
    private int length;
    private byte join;
    private String data;
    private String resultInHex;

    public byte getCode() {
        return code;
    }

    public void setCode(byte code) {
        this.code = code;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public byte getJoin() {
        return join;
    }

    public void setJoin(byte join) {
        this.join = join;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getResultInHex() {
        return resultInHex;
    }

    public RcpResultCode getResult() {
        if (resultInHex == null) {
            return RcpResultCode.ERROR;
        } else if (resultInHex.startsWith("0")) {
            return RcpResultCode.SUCCESS;
        } else if (resultInHex.startsWith("1")) {
            return RcpResultCode.ERROR;
        } else if (resultInHex.startsWith("2")) {
            return RcpResultCode.WARNING;
        } else {
            return RcpResultCode.ERROR;
        }
    }

    public void setResultInHex(String resultInHex) {
        this.resultInHex = resultInHex;
    }

    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}
