package com.shc.scinventory.enterpriseShippingToolJobs.Handlers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.shc.scinventory.enterpriseShippingToolJobs.CmdParsers.MainCommandLineParser;
import com.shc.scinventory.enterpriseShippingToolJobs.Jobs.AutoShipDateJob;
import com.shc.scinventory.enterpriseShippingToolJobs.Jobs.RefreshNextShipDateJob;
import com.shc.scinventory.enterpriseShippingToolJobs.Jobs.UpdatePickupTimeJob;
import com.shc.scinventory.enterpriseShippingToolJobs.Jobs.UploadHistoryJob;
import com.shc.scinventory.enterpriseShippingToolJobs.Utilities.EnterpriseShippingToolConstants;

@Component
public class JobsHandler {
	@Value("${BOS_URL}")
	private String bosUrl;
	
	@Autowired
	RefreshNextShipDateJob refreshNextShipDateJob;
	
	@Autowired
	UploadHistoryJob uploadHistoryJob;
	
	@Autowired
	AutoShipDateJob autoShipDateJob;
	
	@Autowired
	UpdatePickupTimeJob updatePickupTimejob;
	
	public void test () {
		System.out.println(bosUrl);
	}
	
	public void executeJob (MainCommandLineParser mainCommandLineParser) {
		if(mainCommandLineParser.getJob().equalsIgnoreCase(EnterpriseShippingToolConstants.REFRESH_NEXT_SHIP_DATE) || mainCommandLineParser.getJob().equals(EnterpriseShippingToolConstants.REFRESH_NEXT_SHIP_DATE_NUM)){
			refreshNextShipDateJob.run(mainCommandLineParser.getFile());
		} 
		else if(mainCommandLineParser.getJob().equalsIgnoreCase(EnterpriseShippingToolConstants.UPLOAD_HISTORY) || mainCommandLineParser.getJob().equals(EnterpriseShippingToolConstants.UPLOAD_HISTORY_NUM)){
			uploadHistoryJob.run(mainCommandLineParser.getFile(), mainCommandLineParser.getStore());
		}
		else if(mainCommandLineParser.getJob().equalsIgnoreCase(EnterpriseShippingToolConstants.AUTO_SHIPDATE) || mainCommandLineParser.getJob().equals(EnterpriseShippingToolConstants.AUTO_SHIPDATE_NUM)){
			autoShipDateJob.run();
		}
		else if(mainCommandLineParser.getJob().equalsIgnoreCase(EnterpriseShippingToolConstants.UPDATE_PICKUP_TIME) || mainCommandLineParser.getJob().equals(EnterpriseShippingToolConstants.UPDATE_PICKUP_TIME_NUM)){
			updatePickupTimejob.run(mainCommandLineParser.getFile());
		}
		else {
			System.out.println(mainCommandLineParser.getJob() + " is not found ");
		}
		//autoShipDateJob.run();
	}
}
