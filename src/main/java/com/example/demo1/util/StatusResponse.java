package com.example.demo1.util;

public class StatusResponse {

    private boolean oldStatus;
    private boolean currentStatus;
    private Integer id;

    public StatusResponse() {
    }

    public StatusResponse setOldStatus(boolean oldStatus) {
        this.oldStatus = oldStatus;
        return this;
    }

    public StatusResponse setCurrentStatus(boolean currentStatus) {
        this.currentStatus = currentStatus;
        return this;
    }

    public StatusResponse setId(Integer id) {
        this.id = id;
        return this;
    }

    public boolean isOldStatus() {
        return oldStatus;
    }

    public boolean isCurrentStatus() {
        return currentStatus;
    }

    public Integer getId() {
        return id;
    }
}
