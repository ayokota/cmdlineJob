package com.shc.scinventory.enterpriseShippingToolJobs.Bindings.BOS.GetBoxInfo;

import java.util.List;

public class GetAllBoxInfoResponse {
	private String status;
	private String dcunitid;
	private List<BoxInfo> boxInfo;
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getDcunitid() {
		return dcunitid;
	}
	public void setDcunitid(String dcunitid) {
		this.dcunitid = dcunitid;
	}
	public List<BoxInfo> getBoxInfo() {
		return boxInfo;
	}
	public void setBoxInfo(List<BoxInfo> boxInfo) {
		this.boxInfo = boxInfo;
	}
}
