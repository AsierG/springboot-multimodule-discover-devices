package com.asierg.multimodule.service.dto;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import java.io.Serializable;

public class DeviceDTO implements Serializable {

    private String ipAddress;
    private String serialNumber;
    private String productIdentifier;
    private String firmwareVersion;
    private String manufacturer;
    private String sysDescription;

    public DeviceDTO() {
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public String getSerialNumber() {
        return this.serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public String getProductIdentifier() {
        return this.productIdentifier;
    }

    public void setProductIdentifier(String productIdentifier) {
        this.productIdentifier = productIdentifier;
    }

    public String getFirmwareVersion() {
        return this.firmwareVersion;
    }

    public void setFirmwareVersion(String firmwareVersion) {
        this.firmwareVersion = firmwareVersion;
    }

    public String getManufacturer() {
        return this.manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public String getSysDescription() {
        return this.sysDescription;
    }

    public void setSysDescription(String sysDescription) {
        this.sysDescription = sysDescription;
    }

    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}
