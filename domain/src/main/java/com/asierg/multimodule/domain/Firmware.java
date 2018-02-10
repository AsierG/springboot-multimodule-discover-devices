package com.asierg.multimodule.domain;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import javax.persistence.*;
import java.util.List;

import static javax.persistence.FetchType.LAZY;

@Entity
public class Firmware {

    @Id
    private String firmwareVersion;

    private String name;

    private String description;

    @Lob
    @Basic(fetch = LAZY)
    private byte[] imageFile;

    @OneToMany(mappedBy = "firmware", fetch = LAZY)
    private List<Equipment> equipments;

    @OneToMany(mappedBy = "firmware", fetch = LAZY)
    private List<FirmwareModel> firmwareModels;

    public Firmware() {
    }

    public String getFirmwareVersion() {
        return firmwareVersion;
    }

    public void setFirmwareVersion(String firmwareVersion) {
        this.firmwareVersion = firmwareVersion;
    }

    public List<Equipment> getEquipments() {
        return this.equipments;
    }

    public void setEquipments(List<Equipment> equipments) {
        this.equipments = equipments;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public byte[] getImageFile() {
        return this.imageFile;
    }

    public void setImageFile(byte[] imageFile) {
        this.imageFile = imageFile;
    }

    public List<FirmwareModel> getFirmwareModels() {
        return this.firmwareModels;
    }

    public void setFirmwareModels(List<FirmwareModel> firmwareModels) {
        this.firmwareModels = firmwareModels;
    }

    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }

}
