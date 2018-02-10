package com.asierg.multimodule.domain;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import javax.persistence.*;
import java.util.List;

import static javax.persistence.FetchType.LAZY;

@Entity
public class Model {

    @Id
    private String productIdentifier;

    private String name;

    private String description;

    @OneToMany(mappedBy = "model", fetch = FetchType.LAZY)
    private List<Equipment> equipments;

    @OneToMany(mappedBy = "model", fetch = LAZY)
    private List<FirmwareModel> firmwareModels;

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "FK_FAMILY_ID"))
    private Family family;

    public Model() {
    }

    public String getProductIdentifier() {
        return productIdentifier;
    }

    public void setProductIdentifier(String productIdentifier) {
        this.productIdentifier = productIdentifier;
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

    public List<Equipment> getEquipments() {
        return this.equipments;
    }

    public void setEquipments(List<Equipment> equipments) {
        this.equipments = equipments;
    }

    public List<FirmwareModel> getFirmwareModels() {
        return firmwareModels;
    }

    public void setFirmwareModels(List<FirmwareModel> firmwareModels) {
        this.firmwareModels = firmwareModels;
    }

    public Family getFamily() {
        return this.family;
    }

    public void setFamily(Family family) {
        this.family = family;
    }

    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }

}