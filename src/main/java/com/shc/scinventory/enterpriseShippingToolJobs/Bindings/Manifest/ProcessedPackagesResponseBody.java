package com.shc.scinventory.enterpriseShippingToolJobs.Bindings.Manifest;

import java.util.List;

public class ProcessedPackagesResponseBody {
	List<ProcessedPackageResult> results;

	public List<ProcessedPackageResult> getResults() {
		return results;
	}

	public void setResults(List<ProcessedPackageResult> results) {
		this.results = results;
	}
}
