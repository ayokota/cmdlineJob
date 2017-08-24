package com.shc.scinventory.enterpriseShippingToolJobs.Jobs;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;
import com.shc.ecom.fastpromise.dao.caspojo.DcUnitBean;
import com.shc.ecom.fastpromise.data.cache.DCUnitCacheBuilder;
import com.shc.scinventory.enterpriseShippingToolJobs.Beans.UnitInfoBean;
import com.shc.scinventory.enterpriseShippingToolJobs.Daos.UnitInfoDao;
import com.shc.scinventory.enterpriseShippingToolJobs.Utilities.FileReader;
import com.shc.scinventory.enterpriseShippingToolJobs.Utilities.ListUtils;

import liquibase.util.StringUtils;

@Component
public class UpdatePickupTimeJob {
    private static final Logger LOG = Logger.getLogger(UpdatePickupTimeJob.class);

	@Autowired
	UnitInfoDao unitInfoDao;
	
	@Autowired
	DCUnitCacheBuilder dcUnitCacheBuilder;

	
	public void init() {
		System.out.println("Initializing...");
		dcUnitCacheBuilder.init();
		System.out.println("Initializing complete");

	}
	
	public void run(String file) {
		if(file==null || file.isEmpty()) {
			System.out.println("Please provide a file of a list of dc units for update");
			return;
		}
		
		System.out.println("Update Pickup Time job running" );
		try {
			init();
			
			String content = FileReader.readResource(file);
			List<String> searsList = ListUtils.rawToList(content);
			
			//List<UnitInfoBean> unitInfoBeans = unitInfoDao.getAllUnitInfo();
			
			for(String dcUnitId : searsList) {
				//System.out.println(unitInfoBean.getUnit_id());
				//String dcUnitId = unitInfoBean.getUnit_id();
				DcUnitBean dcUnitBean = dcUnitCacheBuilder.getDcUnitInfo(dcUnitId);
				//System.out.println(new Gson().toJson(dcUnitBean));
				if(dcUnitBean!=null) {
					String pickupTIme = getPickupTime(dcUnitBean.getCutoff());
					unitInfoDao.updatePickupTime(pickupTIme, dcUnitId);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void runFull() {
		
		System.out.println("Update Pickup Time job running" );
		try {
			init();
			List<UnitInfoBean> unitInfoBeans = unitInfoDao.getAllUnitInfo();
			
			for(UnitInfoBean unitInfoBean : unitInfoBeans) {
				System.out.println(unitInfoBean.getUnit_id());
				String dcUnitId = unitInfoBean.getUnit_id();
				DcUnitBean dcUnitBean = dcUnitCacheBuilder.getDcUnitInfo(dcUnitId);
				//System.out.println(new Gson().toJson(dcUnitBean));
				if(dcUnitBean!=null) {
					String pickupTIme = getPickupTime(dcUnitBean.getCutoff());
					unitInfoDao.updatePickupTime(pickupTIme, dcUnitId);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public String getPickupTime(String cutOffTime) {
		String pickupTime = "";
		
		System.out.println("cutoff time: " + cutOffTime);
        List<String>arr =  StringUtils.splitAndTrim(cutOffTime, ":");
        Integer hour = Integer.parseInt(arr.get(0)) + 2;
		String minuts = arr.get(1);
		
		pickupTime = hour.toString() + ":" + minuts + ":00";
		System.out.println("pickup time: " + pickupTime);
		
		return pickupTime;
	}
}
