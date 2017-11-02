package com.shc.scinventory.enterpriseShippingToolJobs.Bindings.EST_updateKmartToShipped;

import java.util.List;

public class EST_updateKmartToShippedRequest {
    
    private String storeNumber;
    private String associateId;
    private List<String>  trackingList;
    
    public EST_updateKmartToShippedRequest (
    		String storeNumber,
    		String associateId,
    		List<String>  trackingList
    		) {
    	this.storeNumber = storeNumber;
    	this.associateId = associateId;
    	this.trackingList = trackingList;
    }
    
	public String getStoreNumber() {
		return storeNumber;
	}
	public void setStoreNumber(String storeNumber) {
		this.storeNumber = storeNumber;
	}
	public String getAssociateId() {
		return associateId;
	}
	public void setAssociateId(String associateId) {
		this.associateId = associateId;
	}
	public List<String> getTrackingList() {
		return trackingList;
	}
	public void setTrackingList(List<String> trackingList) {
		this.trackingList = trackingList;
	}
    
}
