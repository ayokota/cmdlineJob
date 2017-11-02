package com.shc.scinventory.enterpriseShippingToolJobs.Beans;

public class AuditBean {

    private String dcUnitId;
    private String userId;
    private String eventType;
    private String workFlow;
    private String deviceInfo;
    private String msg;

    public AuditBean () {
        dcUnitId ="";
        userId = "";
        eventType = "";
        workFlow= "";
        deviceInfo = "";
        msg = "";
    }
    
    public String getDcUnitId() {
        return dcUnitId;
    }

    public void setDcUnitId(String dcUnitId) {
        this.dcUnitId = dcUnitId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public String getWorkFlow() {
        return workFlow;
    }

    public void setWorkFlow(String workFlow) {
        this.workFlow = workFlow;
    }

    public String getDeviceInfo() {
        return deviceInfo;
    }

    public void setDeviceInfo(String deviceInfo) {
        this.deviceInfo = deviceInfo;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}