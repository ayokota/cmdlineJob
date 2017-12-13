package com.shc.scinventory.enterpriseShippingToolJobs.Bindings.EST_updateKmartToShipped;

import java.util.LinkedList;
import java.util.List;

public class EST_updateKmartToShippedRequest {
    
    private String storeNumber;
    private String associateId;
    private List<String>  orders;
    
    public EST_updateKmartToShippedRequest () {
    	orders = new LinkedList<String>();
    }

    
    public EST_updateKmartToShippedRequest (
    		String storeNumber,
    		String associateId,
    		List<String>  orders
    		) {
    	this.storeNumber = storeNumber;
    	this.associateId = associateId;
    	this.orders = orders;
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


	public List<String> getOrders() {
		return orders;
	}


	public void setOrders(List<String> orders) {
		this.orders = orders;
	}

    
}
