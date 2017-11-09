package com.shc.scinventory.enterpriseShippingToolJobs.Bindings.MPU;

import java.util.List;

public class MPUKmartShippedUpdateRequest {
    private String  storeNumber;
    private String  associateId;
    private List<String> orders;

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
