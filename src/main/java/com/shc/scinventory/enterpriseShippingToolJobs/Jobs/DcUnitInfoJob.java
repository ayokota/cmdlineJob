package com.shc.scinventory.enterpriseShippingToolJobs.Jobs;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.shc.ecom.fastpromise.dao.caspojo.DcUnitBean;
import com.shc.ecom.fastpromise.data.cache.DCUnitCacheBuilder;

@Component
public class DcUnitInfoJob {
	
	@Autowired
	private DCUnitCacheBuilder dcUnitCacheBuilder;
	
	public void run() {
		dcUnitCacheBuilder.init();
		Map<String, DcUnitBean> dcUnits = dcUnitCacheBuilder.getAllDcUnitInfoFromCache();
		
		List<String> dcUnitList = new LinkedList<String>();
		
		for(String dcUnitId : dcUnits.keySet()) {
			DcUnitBean DC  = dcUnits.get(dcUnitId);
			//System.out.println(DC.getDcUnitId() + " : " + DC.getStoreType() + " : " + DC.getStatus() + " :" + DC.getFfm());
			if(DC.getStoreType().equals("KMART")
					&&
					DC.getStatus().equals("OPEN")
					&&
					 DC.getFfm().equals("SFS")) {
				dcUnitList.add(dcUnitId);
			}
		}
		System.out.println(dcUnitList.size());

	}
}
