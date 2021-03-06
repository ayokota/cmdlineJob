package com.shc.scinventory.enterpriseShippingToolJobs.Handlers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.shc.scinventory.enterpriseShippingToolJobs.CmdParsers.MainCommandLineParser;
import com.shc.scinventory.enterpriseShippingToolJobs.Jobs.AdHocHistoryUpload;
import com.shc.scinventory.enterpriseShippingToolJobs.Jobs.AdHocUploadJob;
import com.shc.scinventory.enterpriseShippingToolJobs.Jobs.AutoManifest;
import com.shc.scinventory.enterpriseShippingToolJobs.Jobs.AutoShipDateJob;
import com.shc.scinventory.enterpriseShippingToolJobs.Jobs.BoxAssortmentAlertJob;
import com.shc.scinventory.enterpriseShippingToolJobs.Jobs.DBcheck;
import com.shc.scinventory.enterpriseShippingToolJobs.Jobs.DcUnitInfoJob;
import com.shc.scinventory.enterpriseShippingToolJobs.Jobs.PendingShipperCleanupJob;
import com.shc.scinventory.enterpriseShippingToolJobs.Jobs.RefreshNextShipDateJob;
import com.shc.scinventory.enterpriseShippingToolJobs.Jobs.StatusUpdateJob;
import com.shc.scinventory.enterpriseShippingToolJobs.Jobs.UpdatePickupTimeJob;
import com.shc.scinventory.enterpriseShippingToolJobs.Jobs.UploadFaqJob;
import com.shc.scinventory.enterpriseShippingToolJobs.Jobs.UploadHistoryJob;
import com.shc.scinventory.enterpriseShippingToolJobs.Jobs.UploadHistoryJobV2;
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
	
	@Autowired
	BoxAssortmentAlertJob boxAssortmentAlertJob;
	
	@Autowired
	AdHocUploadJob adHocUploadJob;
	
	@Autowired
	UploadHistoryJobV2 uploadHistoryJobV2;
	
	@Autowired
	DcUnitInfoJob dcUnitInfoJob;
	
	@Autowired
	AdHocHistoryUpload adHistoryUpload;
	
	@Autowired
	AutoManifest autoManifest;
	
	@Autowired
	DBcheck dBcheck;
	
	@Autowired
	StatusUpdateJob statusUpdateJob;
	
	@Autowired
	PendingShipperCleanupJob pendingShipperCleanupJob;
	
	@Autowired
	UploadFaqJob uploadFaqJob;
	
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
		else if(mainCommandLineParser.getJob().equalsIgnoreCase(EnterpriseShippingToolConstants.BOX_ASSORTMENT_ALERT) || mainCommandLineParser.getJob().equals(EnterpriseShippingToolConstants.BOX_ASSORTMENT_ALERT_NUM)){
			boxAssortmentAlertJob.run();
		}
		else if(mainCommandLineParser.getJob().equalsIgnoreCase(EnterpriseShippingToolConstants.ADHOC_ADDR_UPLOAD) || mainCommandLineParser.getJob().equals(EnterpriseShippingToolConstants.ADHOC_ADDR_UPLOAD_NUM)){
			adHocUploadJob.run();
		}
		else if(mainCommandLineParser.getJob().equalsIgnoreCase(EnterpriseShippingToolConstants.UPLOAD_HISTORY_V2) || mainCommandLineParser.getJob().equals(EnterpriseShippingToolConstants.UPLOAD_HISTORY_V2_NUM)){
			uploadHistoryJobV2.run(mainCommandLineParser.getFile(), mainCommandLineParser.getStore());
		}
		else if(mainCommandLineParser.getJob().equalsIgnoreCase(EnterpriseShippingToolConstants.DCUNIT_INFO) || mainCommandLineParser.getJob().equals(EnterpriseShippingToolConstants.DCUNIT_INFO_NUM)){
			dcUnitInfoJob.run();
		} 
		else if(mainCommandLineParser.getJob().equalsIgnoreCase(EnterpriseShippingToolConstants.ADHOC_HISTORY_UPLOAD) || mainCommandLineParser.getJob().equals(EnterpriseShippingToolConstants.ADHOC_HISTORY_UPLOAD_NUM)){
			adHistoryUpload.run();
		}		
		else if(mainCommandLineParser.getJob().equalsIgnoreCase(EnterpriseShippingToolConstants.AUTO_MANIFEST) || mainCommandLineParser.getJob().equals(EnterpriseShippingToolConstants.AUTO_MANIFEST_NUM)){
			autoManifest.run();
		} 
		else if(mainCommandLineParser.getJob().equalsIgnoreCase("11")){
			dBcheck.run();
		}
		else if(mainCommandLineParser.getJob().equalsIgnoreCase("12")){
			statusUpdateJob.run();
		}
		else if(mainCommandLineParser.getJob().equalsIgnoreCase("13")){
			pendingShipperCleanupJob.run();
		}
		else if(mainCommandLineParser.getJob().equalsIgnoreCase(EnterpriseShippingToolConstants.UPLOAD_FAQ) || mainCommandLineParser.getJob().equals(EnterpriseShippingToolConstants.UPLOAD_FAQ_NUM)){
			uploadFaqJob.run(mainCommandLineParser.getFile());
		}
		else {
			System.out.println(mainCommandLineParser.getJob() + " is not found ");
		}
		//autoShipDateJob.run();
	}
}
