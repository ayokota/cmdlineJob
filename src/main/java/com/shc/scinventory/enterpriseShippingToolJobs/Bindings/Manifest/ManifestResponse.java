package com.shc.scinventory.enterpriseShippingToolJobs.Bindings.Manifest;

import com.shc.scinventory.enterpriseShippingToolJobs.Bindings.ShippingServiceResponseHeader;

public class ManifestResponse {
	ShippingServiceResponseHeader responseHeader;
	ProcessedPackagesResponseBody processedPackagesResponseBody;
	public ShippingServiceResponseHeader getResponseHeader() {
		return responseHeader;
	}
	public void setResponseHeader(ShippingServiceResponseHeader responseHeader) {
		this.responseHeader = responseHeader;
	}
	public ProcessedPackagesResponseBody getProcessedPackagesResponseBody() {
		return processedPackagesResponseBody;
	}
	public void setProcessedPackagesResponseBody(ProcessedPackagesResponseBody processedPackagesResponseBody) {
		this.processedPackagesResponseBody = processedPackagesResponseBody;
	}
	
	

}
