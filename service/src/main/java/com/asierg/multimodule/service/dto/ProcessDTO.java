package com.asierg.multimodule.service.dto;

import com.asierg.multimodule.service.enums.OperationStatus;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import java.util.Date;

public class ProcessDTO {

    private Integer futuresSize = 0;
    private Integer finishedFutures = 0;
    private Integer completedFutures = 0;
    private Integer canceledFutures = 0;
    private Integer foundDevices = 0;
    private boolean processRunning;
    private Date start;
    private Date end;
    private OperationStatus operationStatus;

    public Integer getFuturesSize() {
        return futuresSize;
    }

    public void setFuturesSize(Integer futuresSize) {
        this.futuresSize = futuresSize;
    }

    public Integer getFinishedFutures() {
        return finishedFutures;
    }

    public void setFinishedFutures(Integer finishedFutures) {
        this.finishedFutures = finishedFutures;
    }

    public boolean getProcessRunning() {
        return processRunning;
    }

    public void setProcessRunning(boolean processRunning) {
        this.processRunning = processRunning;
    }

    public Integer getCanceledFutures() {
        return canceledFutures;
    }

    public void setCanceledFutures(Integer canceledFutures) {
        this.canceledFutures = canceledFutures;
    }

    public Integer getCompletedFutures() {
        return completedFutures;
    }

    public void setCompletedFutures(Integer completedFutures) {
        this.completedFutures = completedFutures;
    }

    public Integer getFoundDevices() {
        return foundDevices;
    }

    public void setFoundDevices(Integer foundDevices) {
        this.foundDevices = foundDevices;
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

    public OperationStatus getOperationStatus() {
        return operationStatus;
    }

    public void setOperationStatus(OperationStatus operationStatus) {
        this.operationStatus = operationStatus;
    }

    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }

}
