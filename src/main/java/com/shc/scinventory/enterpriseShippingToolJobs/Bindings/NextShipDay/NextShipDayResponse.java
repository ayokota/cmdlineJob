package com.shc.scinventory.enterpriseShippingToolJobs.Bindings.NextShipDay;

import com.shc.scinventory.enterpriseShippingToolJobs.Bindings.ShippingServiceResponseHeader;

public class NextShipDayResponse {
	private ShippingServiceResponseHeader responseHeader;
	private NextShipDayResponseBody shipDateResponseBody;
	public ShippingServiceResponseHeader getResponseHeader() {
		return responseHeader;
	}
	public void setResponseHeader(ShippingServiceResponseHeader responseHeader) {
		this.responseHeader = responseHeader;
	}
	public NextShipDayResponseBody getShipDateResponseBody() {
		return shipDateResponseBody;
	}
	public void setShipDateResponseBody(NextShipDayResponseBody shipDateResponseBody) {
		this.shipDateResponseBody = shipDateResponseBody;
	}
	
	
	
}
