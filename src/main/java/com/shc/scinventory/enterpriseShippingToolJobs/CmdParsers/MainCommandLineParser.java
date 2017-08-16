package com.shc.scinventory.enterpriseShippingToolJobs.CmdParsers;

import com.beust.jcommander.Parameter;

public class MainCommandLineParser {
	@Parameter(names = { "-j", "-job" }, required = true, description = "Job name or Id")
	private String job ;

	@Parameter(names = { "-f", "-file" }, required = false, description = "file")
	private String file ;
	
	public String getJob() {
		return job;
	}

	public void setJob(String job) {
		this.job = job;
	}

	public String getFile() {
		return file;
	}

	public void setFile(String file) {
		this.file = file;
	}
}
