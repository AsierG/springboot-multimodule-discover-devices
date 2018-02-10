package com.asierg.multimodule.domain;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import javax.persistence.*;

@Entity
public class FirmwareModel {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "FK_FIRMWARE_ID_FIRMWARE_MODEL"))
    private Firmware firmware;

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "FK_MODEL_ID_FIRMWARE_MODEL"))
    private Model model;

    private String statusSchema;

    private String configSchema;

    public FirmwareModel() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStatusSchema() {
        return this.statusSchema;
    }

    public void setStatusSchema(String statusSchema) {
        this.statusSchema = statusSchema;
    }

    public String getConfigSchema() {
        return this.configSchema;
    }

    public void setConfigSchema(String configSchema) {
        this.configSchema = configSchema;
    }

    public Firmware getFirmware() {
        return this.firmware;
    }

    public void setFirmware(Firmware firmware) {
        this.firmware = firmware;
    }

    public Model getModel() {
        return model;
    }

    public void setModel(Model model) {
        this.model = model;
    }

    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }

}
