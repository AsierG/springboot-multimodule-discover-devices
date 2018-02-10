package com.asierg.multimodule.domain;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
public class Equipment {

    @Id
    private String serialNumber;

    private String ipAddress;

    private Boolean active;

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "FK_MODEL_ID_EQUIPMENT"))
    private Model model;

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "FK_FIRMWARE_ID_EQUIPMENT"))
    private Firmware firmware;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY,
            mappedBy = "equipment")
    @Fetch(FetchMode.SUBSELECT)
    private List<DiscoverError> discoverErrors = new ArrayList<>();

    private Date createDate;
    private Date updateDate;

    public Equipment() {
    }

    public Equipment(String serialNumber, String ipAddress, Model model, Firmware firmware) {
        this.serialNumber = serialNumber;
        this.ipAddress = ipAddress;
        this.model = model;
        this.firmware = firmware;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public Model getModel() {
        return model;
    }

    public void setModel(Model model) {
        this.model = model;
    }

    public Firmware getFirmware() {
        return firmware;
    }

    public void setFirmware(Firmware firmware) {
        this.firmware = firmware;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public List<DiscoverError> getDiscoverErrors() {
        return discoverErrors;
    }

    public void setDiscoverErrors(List<DiscoverError> discoverErrors) {
        this.discoverErrors = discoverErrors;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    @PreUpdate
    @PrePersist
    public void updateTimeStamps() {
        Date now = new Date();
        this.updateDate = now;
        if (this.createDate == null) {
            this.createDate = now;
            this.active = Boolean.TRUE;
        }
    }

    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }

}