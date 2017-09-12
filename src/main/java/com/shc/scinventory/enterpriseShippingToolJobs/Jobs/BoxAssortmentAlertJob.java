package com.shc.scinventory.enterpriseShippingToolJobs.Jobs;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.shc.scinventory.enterpriseShippingToolJobs.Bindings.BOS.GetBoxInfo.BoxInfo;
import com.shc.scinventory.enterpriseShippingToolJobs.Bindings.BOS.GetBoxInfo.GetAllBoxInfoResponse;
import com.shc.scinventory.enterpriseShippingToolJobs.Clients.BosClient;
import com.shc.scinventory.enterpriseShippingToolJobs.Clients.SmtpClient;
import com.shc.scinventory.enterpriseShippingToolJobs.Daos.UnitInfoDao;
import com.shc.scinventory.enterpriseShippingToolJobs.Utilities.JSONSerializer;
import com.shc.scinventory.enterpriseShippingToolJobs.Utilities.ListUtils;

@Component
public class BoxAssortmentAlertJob {
    private static final Logger LOG = Logger.getLogger(BoxAssortmentAlertJob.class);

    @Autowired
    UnitInfoDao unitInfoDao;
    
    @Autowired
    BosClient bosClient;
    
    @Autowired
    SmtpClient smtpClient;
    
    @Value("${SEARS_EMAIL_LIST}")
    private String searsEmailList;
    
    @Value("${KMART_EMAIL_LIST}")
    private String kmartEmailList;
    
    public void run() {
		System.out.println("Box Assortment Alert job running" );
		LOG.error("Box Assortment Alert job running" );

		try {			
			Map<String, Map<String, Boolean> > boxAssortmentData = gatherBoxAssortmentData ();
			processSearsBoxAssortment(boxAssortmentData);
			processKmartBoxAssortment(boxAssortmentData);

		} catch (Exception e) {
			e.printStackTrace();
			LOG.error("An exception has occred in run method for auto ship date job with msg " + e.getMessage(), e);
		}
	}
    
    public Map<String, Map<String, Boolean> > gatherBoxAssortmentData () {
		Map<String, Map<String, Boolean> > boxAssortmentData = new HashMap<String, Map<String, Boolean> > ();
		try {
			List<String> dcUnitList = unitInfoDao.getListOfDcs();

			for(String dcUnit : dcUnitList) {
				Map<String, Boolean> boxAssortmentMap = new HashMap<String, Boolean> ();
				String response = bosClient.getBoxInfo(dcUnit);
				GetAllBoxInfoResponse getAllBoxInfoResponse = JSONSerializer.deserialize(response, GetAllBoxInfoResponse.class);
				if(getAllBoxInfoResponse.getStatus().equals("SUCCESS")) {
					//boxAssortmentMap.put(key, value)
					for(BoxInfo boxInfo: getAllBoxInfoResponse.getBoxInfo()) {
						boxAssortmentMap.put(boxInfo.getBoxId(), boxInfo.isActive());
					}
				}
				if(boxAssortmentMap.size() >0) {
					boxAssortmentData.put(dcUnit, boxAssortmentMap);
				}
			}
		} catch (Exception e) {
			LOG.error("An exception has occred in gatherBoxAssortmentData with msg " + e.getMessage(), e);
		}
		return boxAssortmentData;
    }
    
    public void processSearsBoxAssortment (Map<String, Map<String, Boolean> > boxAssortmentData) {
    	try {
    		StringBuilder sb = new StringBuilder();
    		for (String dcUnitId : boxAssortmentData.keySet()) {
    			if(dcUnitId.length()==7) {
    				//System.out.println(dcUnitId);
    				sb.append("DC Unit: ").append(dcUnitId).append("\n");
    				//.append().append()
    				for(String boxId : boxAssortmentData.get(dcUnitId).keySet()) {
    					sb.append(boxId).append(" : ").append(boxAssortmentData.get(dcUnitId).get(boxId)).append("\n");
    				}
    				sb.append("\n");
    			}
    		}
    		
    		//smtpClient.send(sb.toString(), searsEmailList, "Sears Box Assortment");
    		for(String email : ListUtils.lineToList(searsEmailList, ",")) {
    			smtpClient.send(sb.toString(), email, "Sears Box Assortment");
    		}
    	} catch (Exception e) {
			LOG.error("An exception has occred in processSearsBoxAssortment with msg " + e.getMessage(), e);
    	}
    }
    
    public void processKmartBoxAssortment (Map<String, Map<String, Boolean> > boxAssortmentData) {
    	try {
    		StringBuilder sb = new StringBuilder();
    		for (String dcUnitId : boxAssortmentData.keySet()) {
    			if(dcUnitId.length()==4) {
    				//System.out.println(dcUnitId);
    				sb.append("DC Unit: ").append(dcUnitId).append("\n");
    				//.append().append()
    				for(String boxId : boxAssortmentData.get(dcUnitId).keySet()) {
    					sb.append(boxId).append(" : ").append(boxAssortmentData.get(dcUnitId).get(boxId)).append("\n");
    				}
    				sb.append("\n");
    			}
    		}
    		
    		//smtpClient.send(sb.toString(), kmartEmailList, "Kmart Box Assortment");
    		for(String email : ListUtils.lineToList(kmartEmailList, ",")) {
    			smtpClient.send(sb.toString(), email, "Sears Box Assortment");
    		}
    	} catch (Exception e) {
			LOG.error("An exception has occred in processKmartBoxAssortment with msg " + e.getMessage(), e);
    	}
    }
}
