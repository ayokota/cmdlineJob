package com.shc.scinventory.enterpriseShippingToolJobs.Bindings;

public class ShippingServiceResponseHeader {
	private String returnCode;
	private String errorMessage;
	private String timestamp;
	public String getReturnCode() {
		return returnCode;
	}
	public void setReturnCode(String returnCode) {
		this.returnCode = returnCode;
	}
	public String getErrorMessage() {
		return errorMessage;
	}
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
	public String getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}
}
