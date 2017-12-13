package com.shc.scinventory.enterpriseShippingToolJobs.Jobs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.shc.ecom.fastpromise.dao.casimpl.CarrierZipDaoImpl;
import com.shc.ecom.fastpromise.dao.caspojo.CarrierZipBean;
import com.shc.ecom.fastpromise.data.cache.DCUnitCacheBuilder;

@Component
public class DcUnitInfoJob {
	
	@Autowired
	private DCUnitCacheBuilder dcUnitCacheBuilder;
	
	@Autowired
	private CarrierZipDaoImpl carrierZipDaoImpl;
	
	public void run() {
//		dcUnitCacheBuilder.init();
//		Map<String, DcUnitBean> dcUnits = dcUnitCacheBuilder.getAllDcUnitInfoFromCache();
//		
//		List<String> dcUnitList = new LinkedList<String>();
//		
//		for(String dcUnitId : dcUnits.keySet()) {
//			DcUnitBean DC  = dcUnits.get(dcUnitId);
//			//System.out.println(DC.getDcUnitId() + " : " + DC.getStoreType() + " : " + DC.getStatus() + " :" + DC.getFfm());
////			if(DC.getStoreType().equals("KMART")
////					&&
////					DC.getStatus().equals("OPEN")
////					&&
////					 DC.getFfm().equals("SFS")) {
////				dcUnitList.add(dcUnitId);
////			}
//			if(DC.getCarrier().containsKey("IslandWide")) {
//				dcUnitList.add(dcUnitId);
//				System.out.println(dcUnitId);
//			}
//		}
//		System.out.println(dcUnitList.size());
		carrierZipDaoImpl.init();
		
		for(String zip : carrierZipDaoImpl.getCarriersInfoCache().keySet()) {
			CarrierZipBean zipBean =  carrierZipDaoImpl.getCarriersInfoCache().get(zip);
			if(zipBean.getCarrierconfig().containsKey("islandwide")) {
				System.out.println(zip);
			}
		}
	}
}
