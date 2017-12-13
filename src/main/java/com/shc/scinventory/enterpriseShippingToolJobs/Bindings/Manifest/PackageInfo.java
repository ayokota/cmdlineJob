package com.shc.scinventory.enterpriseShippingToolJobs.Bindings.Manifest;

public class PackageInfo {
    private String trackingNum;
    private Integer msn;
    private Boolean voided;

    public String getTrackingNum() {
        return trackingNum;
    }

    public void setTrackingNum(String trackingNum) {
        this.trackingNum = trackingNum;
    }

    public Integer getMsn() {
        return msn;
    }

    public void setMsn(Integer msn) {
        this.msn = msn;
    }

    public Boolean getVoided() {
        return voided;
    }

    public void setVoided(Boolean voided) {
        this.voided = voided;
    }
	
}
