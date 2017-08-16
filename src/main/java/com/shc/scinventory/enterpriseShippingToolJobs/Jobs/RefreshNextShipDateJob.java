package com.shc.scinventory.enterpriseShippingToolJobs.Jobs;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.shc.scinventory.enterpriseShippingToolJobs.Beans.UnitInfoBean;
import com.shc.scinventory.enterpriseShippingToolJobs.Bindings.NextShipDay.NextShipDayResponse;
import com.shc.scinventory.enterpriseShippingToolJobs.Clients.ShipipngServiceClient;
import com.shc.scinventory.enterpriseShippingToolJobs.Daos.UnitInfoDao;
import com.shc.scinventory.enterpriseShippingToolJobs.Utilities.EnterpriseShippingToolConstants;
import com.shc.scinventory.enterpriseShippingToolJobs.Utilities.EnterpriseShippingToolUtil;
import com.shc.scinventory.enterpriseShippingToolJobs.Utilities.FileReader;
import com.shc.scinventory.enterpriseShippingToolJobs.Utilities.JSONSerializer;
import com.shc.scinventory.enterpriseShippingToolJobs.Utilities.ListUtils;

@Component
public class RefreshNextShipDateJob {
    private static final Logger LOG = Logger.getLogger(RefreshNextShipDateJob.class);

	@Autowired
	ShipipngServiceClient shipipngServiceClient;
	
	@Autowired
	UnitInfoDao unitInfoDao;
	
	private static final String next_ship_date_req = "{ \"shipperCode\": \"?\" }";
	
	public void run(String file) {
		if(file==null || file.isEmpty()) {
			System.out.println("Please provide a file of a list of dc units for update");
			return;
		}
		
		System.out.println("running job refresh next ship date" );
		try {
			String content = FileReader.readResource(file);
			List<String> searsList = ListUtils.rawToList(content);

			for(String dcUnitId : searsList) {
				refreshNextShipDate(dcUnitId);
			}
			//refreshNextShipDate(searsList.get(0));
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void refreshNextShipDate(String dcUnitId) {
		System.out.println("processing dcunitid: " + dcUnitId);
		try {
			String nextShipDate = "";
			
			/* fetch next ship date */
			String shippingServiceResponse = 
					shipipngServiceClient.postAPI(next_ship_date_req.replace("?", EnterpriseShippingToolUtil.getShipperCode(dcUnitId)), 
							EnterpriseShippingToolConstants.NEXT_SHIP_DATE_API);
			NextShipDayResponse nextShipDayResponse = JSONSerializer.deserialize(shippingServiceResponse, NextShipDayResponse.class);
			nextShipDate = nextShipDayResponse.getShipDateResponseBody().getShipDate() ;
			if(!nextShipDayResponse.getResponseHeader().getErrorMessage().equals("SUCCESS")) {
				LOG.error("DcUnit: " + dcUnitId + " did not return success from getNextShipDate API " );
				return;
			}
			
			/* if database has no record */
			if(!unitInfoDao.getUnitInfoCache().containsKey(dcUnitId)) {
				UnitInfoBean unitInfoBean = new UnitInfoBean(dcUnitId);
				unitInfoBean.setShip_date(EnterpriseShippingToolUtil.dateToString(nextShipDate));
				unitInfoBean.setType_of_update("Initialization");
				unitInfoDao.insert(unitInfoBean);
			}
			/* if database has record */
//			else {
//				unitInfoDao.updateShipDate(dcUnitId, EnterpriseShippingToolUtil.dateToString(nextShipDate));
//			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
