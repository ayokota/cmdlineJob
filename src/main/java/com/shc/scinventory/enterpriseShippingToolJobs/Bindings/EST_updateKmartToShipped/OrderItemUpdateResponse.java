package com.shc.scinventory.enterpriseShippingToolJobs.Bindings.EST_updateKmartToShipped;

public class OrderItemUpdateResponse {
    private int responseCode;
    private String responseDesc;
    private String responseBody;

    public int getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(int responseCode) {
        this.responseCode = responseCode;
    }

    public String getResponseDesc() {
        return responseDesc;
    }

    public void setResponseDesc(String responseDesc) {
        this.responseDesc = responseDesc;
    }

    public String getResponseBody() {
        return responseBody;
    }

    public void setResponseBody(String responseBody) {
        this.responseBody = responseBody;
    }
}
