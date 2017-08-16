package com.shc.scinventory.enterpriseShippingToolJobs.Jobs;

import java.time.LocalTime;
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
import com.shc.scinventory.enterpriseShippingToolJobs.Utilities.JSONSerializer;

@Component
public class AutoShipDateJob {

    private static final Logger LOG = Logger.getLogger(AutoShipDateJob.class);

	@Autowired
	UnitInfoDao unitInfoDao;

	@Autowired
	ShipipngServiceClient shipipngServiceClient;
	
	private static final String next_ship_date_req = "{ \"shipperCode\": \"?\" }";
	
	
	public void run() {
		System.out.println("Auto Ship Date job running" );
		try {
			List<UnitInfoBean> unitInfoBeans = unitInfoDao.getAllUnitInfo();
			
			for(UnitInfoBean unitInfoBean: unitInfoBeans) {
				processUnit(unitInfoBean);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void processUnit (UnitInfoBean unitInfoBean) {
		System.out.println("processing dcunitid: " + unitInfoBean.getUnit_id());

		try {
			String nextShipDate = "";
			
			/* fetch next ship date */
			String shippingServiceResponse = 
					shipipngServiceClient.postAPI(next_ship_date_req.replace("?", EnterpriseShippingToolUtil.getShipperCode(unitInfoBean.getUnit_id())), 
							EnterpriseShippingToolConstants.NEXT_SHIP_DATE_API);
			NextShipDayResponse nextShipDayResponse = JSONSerializer.deserialize(shippingServiceResponse, NextShipDayResponse.class);
			nextShipDate = nextShipDayResponse.getShipDateResponseBody().getShipDate() ;
			
            LocalTime pickUpTime = unitInfoDao.getUnitInfo(unitInfoBean.getUnit_id()).getPickup_time().toLocalTime();
            LocalTime beforePickUpTime = pickUpTime.minusHours(2);
            LocalTime afterPickUpTime = pickUpTime.plusHours(2);
            LocalTime currentTime = LocalTime.now();
            
            if(!currentTime.isBefore(beforePickUpTime) && !currentTime.isAfter(afterPickUpTime)) {
                updateShipDate( unitInfoBean, nextShipDate);
            }
            
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void updateShipDate (UnitInfoBean unitInfoBean, String nextShipDate) {
		try {
			//unitInfoBean.setShip_date(EnterpriseShippingToolUtil.dateToString(nextShipDate));
			//unitInfoBean.setType_of_update("AUTO UPDATE");
			unitInfoDao.autoUpdateShipDate(unitInfoBean.getUnit_id(), EnterpriseShippingToolUtil.dateToString(nextShipDate), "AUTO UPDATE");

            
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
		
}
