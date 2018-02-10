package com.asierg.multimodule.domain;

import com.asierg.multimodule.domain.enums.ErrorType;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
public class DiscoverError {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(foreignKey = @ForeignKey(name = "FK_SERIALNUMBER_EQUIPMENT"))
    @NotNull
    private Equipment equipment;

    @Enumerated(EnumType.STRING)
    private ErrorType errorType;

    private Date createDate;

    public DiscoverError() {
    }

    public DiscoverError(Equipment equipment, ErrorType errorType) {
        this.equipment = equipment;
        this.errorType = errorType;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Equipment getEquipment() {
        return equipment;
    }

    public void setEquipment(Equipment equipment) {
        this.equipment = equipment;
    }

    public ErrorType getErrorType() {
        return errorType;
    }

    public void setErrorType(ErrorType errorType) {
        this.errorType = errorType;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    @PrePersist
    public void updateTimeStamps() {
        if (this.createDate == null) {
            this.createDate = new Date();
        }
    }

    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }

}
