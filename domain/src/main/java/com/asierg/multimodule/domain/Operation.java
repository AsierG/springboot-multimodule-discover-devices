package com.asierg.multimodule.domain;

import com.asierg.multimodule.domain.enums.InterfaceType;
import com.asierg.multimodule.domain.enums.OperationType;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import java.util.Date;

@Entity
public class Operation {

    public static final Long OPERATION_ID = 1L;

    @Id
    private Long id;

    @Enumerated(EnumType.STRING)
    private InterfaceType launcherInterface;

    @Enumerated(EnumType.STRING)
    private OperationType operationType;

    private Date start;

    private Date end;

    public Operation() {
        super();
    }

    public Operation(InterfaceType launcherInterface, OperationType operationType) {
        super();
        this.id = OPERATION_ID;
        this.launcherInterface = launcherInterface;
        this.operationType = operationType;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public InterfaceType getLauncherInterface() {
        return launcherInterface;
    }

    public void setLauncherInterface(InterfaceType launcherInterface) {
        this.launcherInterface = launcherInterface;
    }

    public OperationType getOperationType() {
        return operationType;
    }

    public void setOperationType(OperationType operationType) {
        this.operationType = operationType;
    }

    public Date getStart() {
        return start;
    }

    public void setStart(Date start) {
        this.start = start;
    }

    public Date getEnd() {
        return end;
    }

    public void setEnd(Date end) {
        this.end = end;
    }

    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }

}
