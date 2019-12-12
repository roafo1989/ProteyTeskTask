package com.example.demo1.util;


public class StatusResponse {

    private String oldStatus;
    private String currentStatus;
    private Integer id;

    public StatusResponse() {
    }

    public StatusResponse setOldStatus(String oldStatus) {
        this.oldStatus = oldStatus;
        return this;
    }

    public StatusResponse setCurrentStatus(String currentStatus) {
        this.currentStatus = currentStatus;
        return this;
    }

    public StatusResponse setId(Integer id) {
        this.id = id;
        return this;
    }

    public String getOldStatus() {
        return oldStatus;
    }

    public String getCurrentStatus() {
        return currentStatus;
    }

    public Integer getId() {
        return id;
    }
}
