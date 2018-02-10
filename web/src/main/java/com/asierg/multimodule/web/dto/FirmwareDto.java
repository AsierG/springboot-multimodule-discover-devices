package com.asierg.multimodule.web.dto;

import java.io.Serializable;

public class FirmwareDto implements Serializable {

    private String firmwareVersion;

    private String name;

    public String getFirmwareVersion() {
        return firmwareVersion;
    }

    public void setFirmwareVersion(String firmwareVersion) {
        this.firmwareVersion = firmwareVersion;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
