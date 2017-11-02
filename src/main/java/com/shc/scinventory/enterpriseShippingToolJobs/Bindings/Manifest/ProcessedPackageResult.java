package com.shc.scinventory.enterpriseShippingToolJobs.Bindings.Manifest;

import java.util.List;

public class ProcessedPackageResult {
	private String returnCode;
	private String message;
	private String shipDate;
	private Integer totalPackages;
	private Integer closedOutPackages;
	private List<PackageInfo> packageInfo;
	
	
	public String getReturnCode() {
		return returnCode;
	}
	public void setReturnCode(String returnCode) {
		this.returnCode = returnCode;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getShipDate() {
		return shipDate;
	}
	public void setShipDate(String shipDate) {
		this.shipDate = shipDate;
	}
	public Integer getTotalPackages() {
		return totalPackages;
	}
	public void setTotalPackages(Integer totalPackages) {
		this.totalPackages = totalPackages;
	}
	public Integer getClosedOutPackages() {
		return closedOutPackages;
	}
	public void setClosedOutPackages(Integer closedOutPackages) {
		this.closedOutPackages = closedOutPackages;
	}
	public List<PackageInfo> getPackageInfo() {
		return packageInfo;
	}
	public void setPackageInfo(List<PackageInfo> packageInfo) {
		this.packageInfo = packageInfo;
	}
	
	
}
