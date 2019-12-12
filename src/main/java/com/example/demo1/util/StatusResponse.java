package com.example.demo1.util;


import com.example.demo1.model.StatusOfEnable;

public class StatusResponse {

    private StatusOfEnable oldStatus;
    private StatusOfEnable currentStatus;
    private Integer id;

    public StatusResponse() {
    }

    public StatusResponse setOldStatus(StatusOfEnable oldStatus) {
        this.oldStatus = oldStatus;
        return this;
    }

    public StatusResponse setCurrentStatus(StatusOfEnable currentStatus) {
        this.currentStatus = currentStatus;
        return this;
    }

    public StatusResponse setId(Integer id) {
        this.id = id;
        return this;
    }

    public StatusOfEnable getOldStatus() {
        return oldStatus;
    }

    public StatusOfEnable getCurrentStatus() {
        return currentStatus;
    }

    public Integer getId() {
        return id;
    }
}
