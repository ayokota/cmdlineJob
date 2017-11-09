package com.shc.scinventory.enterpriseShippingToolJobs.Jobs;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.shc.scinventory.enterpriseShippingToolJobs.Beans.AuditBean;
import com.shc.scinventory.enterpriseShippingToolJobs.Beans.UnitInfoBean;
import com.shc.scinventory.enterpriseShippingToolJobs.Bindings.MPU.MPUKmartShippedUpdateRequest;
import com.shc.scinventory.enterpriseShippingToolJobs.Bindings.Manifest.ManifestResponse;
import com.shc.scinventory.enterpriseShippingToolJobs.Bindings.Manifest.PackageInfo;
import com.shc.scinventory.enterpriseShippingToolJobs.Bindings.Manifest.ProcessedPackageResult;
import com.shc.scinventory.enterpriseShippingToolJobs.Clients.OrderRetrievalClient;
import com.shc.scinventory.enterpriseShippingToolJobs.Clients.ShipipngServiceClient;
import com.shc.scinventory.enterpriseShippingToolJobs.Clients.ShippingToolClient;
import com.shc.scinventory.enterpriseShippingToolJobs.Daos.AuditDao;
import com.shc.scinventory.enterpriseShippingToolJobs.Daos.AuditDaoImpl;
import com.shc.scinventory.enterpriseShippingToolJobs.Daos.PackageInfoDao;
import com.shc.scinventory.enterpriseShippingToolJobs.Daos.UnitInfoDao;
import com.shc.scinventory.enterpriseShippingToolJobs.Utilities.EnterpriseShippingToolConstants;
import com.shc.scinventory.enterpriseShippingToolJobs.Utilities.EnterpriseShippingToolUtil;
import com.shc.scinventory.enterpriseShippingToolJobs.Utilities.JSONSerializer;

@Component
public class AutoManifest {
    private static final Logger LOG = Logger.getLogger(AutoManifest.class);
    private static final Logger MANIFEST_LOGGER = Logger.getLogger("manifestLogger");

    @Autowired
    PackageInfoDao packageInfoDao;
    
    @Autowired
    AuditDao auditDao;
    
    @Autowired
    ShipipngServiceClient shipipngServiceClient;
    
    @Autowired
    ShippingToolClient shippingToolClient;
    
    @Autowired 
    UnitInfoDao unitInfoDao;
    
    @Autowired
    AuditDaoImpl auditDaoImpl;
    
    @Autowired
    OrderRetrievalClient orderRetrievalClient;
    
	public void run() {
		System.out.println("AutoManifest job processor");
		
		try {
			List<UnitInfoBean> unitInfoBeans = unitInfoDao.getAllUnitInfo();
			for(UnitInfoBean unitInfoBean: unitInfoBeans) {
				manifest(unitInfoBean.getUnit_id());
			}
		} catch (Exception e) {
			LOG.error("An exception has occured in method run() with error: " + e.getMessage());
		}
	}

	private void auditAndLogError(String unit_id, String msg) {
		String errorMsg = "Error during manifest: " + msg;
		AuditBean auditBean = new AuditBean();
		auditBean.setDcUnitId(unit_id);
		auditBean.setEventType("Exception");
		auditBean.setWorkFlow("Auto Manifest");
		auditBean.setUserId("JBoss");
		auditBean.setMsg(errorMsg);
		LOG.error(errorMsg);
		auditDaoImpl.insertInstance(auditBean);

	}
	
	private void auditAndLogZeroPackage(String unit_id) {
		String msg = "Zero packages manifested.";
		AuditBean auditBean = new AuditBean();
		auditBean.setDcUnitId(unit_id);
		auditBean.setEventType("Batch");
		auditBean.setWorkFlow("Auto Manifest");
		auditBean.setUserId("JBoss");
		auditBean.setMsg(msg);
		LOG.error(msg);
		auditDaoImpl.insertInstance(auditBean);

	}
	
	private void manifest(String unit_id) {
		try {
			
			Map<String, String> manifestRequest = new HashMap<String, String> ();
			manifestRequest.put("shipperCode", EnterpriseShippingToolUtil.getShipperCode(unit_id));
			
			LOG.debug(manifestRequest);
			
			//System.out.println("manifesting unit: " + unit_id);
			String response = shipipngServiceClient.postAPI(JSONSerializer.serialize(manifestRequest), 
					EnterpriseShippingToolConstants.MANIFEST_API);
			//System.out.println(response);
			
			LOG.debug(response);


			ManifestResponse manifestResponse = JSONSerializer.deserialize(response, ManifestResponse.class);
			
			if(!manifestResponse.getResponseHeader().getReturnCode().equals("0") && !manifestResponse.getResponseHeader().getReturnCode().equals("4")) {
				auditAndLogError(unit_id, "Error occurred while processing manifest");
				return;
			} else if (manifestResponse.getResponseHeader().getReturnCode().equals("4")) {
				auditAndLogZeroPackage(unit_id);
			}
			
			
			
			List<String> trackingNums = new LinkedList<String> ();
			for(ProcessedPackageResult result: manifestResponse.getProcessedPackagesResponseBody().getResults()) {
				for(PackageInfo packageInfo : result.getPackageInfo()) {
					trackingNums.add(packageInfo.getTrackingNum());
				}
			}
			if(trackingNums.size()>0) {
				if(unit_id.length()==4) {
					//MANIFEST_LOGGER.error(unit_id + " has closed out: " + JSONSerializer.serialize(trackingNums));
					processKmartUpdate(unit_id, trackingNums);
				}
	
				AuditBean auditBean = new AuditBean();
				auditBean.setDcUnitId(unit_id);
				auditBean.setEventType("Batch");
				auditBean.setWorkFlow("Auto Manifest");
				auditBean.setUserId("JBoss");
				auditBean.setMsg("Auto Manifest triggered. Number of packages: " + trackingNums.size());
				
				auditDaoImpl.insertInstance(auditBean);
			} else {
				auditAndLogZeroPackage(unit_id);
			}
		} catch (Exception e) {
			LOG.error("Error processing auto manifest for dc: " + unit_id + " with error: " + e.getMessage());
			auditAndLogError(unit_id, "Error occurred while processing manifest");
		}
	}
	
	public void processKmartUpdate (String unit_id, List<String> trackingNums) {
		try {
			MANIFEST_LOGGER.error(unit_id + " has closed out: " + JSONSerializer.serialize(trackingNums));
            List<String> subOrderIds = packageInfoDao.getSubOrderIdForTrackingNumbers(trackingNums);
            MANIFEST_LOGGER.error("Update For Kmart:  " + unit_id
                    + "\n with SubOrdIds:  " + JSONSerializer.serialize(subOrderIds));
			
            
            List<String> storeSubOrderIds = new LinkedList<String>();

            if(subOrderIds!=null && subOrderIds.size()>0) {
                for(String suborderId : new HashSet<String>(subOrderIds)) {
                    if(!suborderId.startsWith("a") && !suborderId.startsWith("c")) {
                        storeSubOrderIds.add(suborderId);
                    }
                }
                if(!storeSubOrderIds.isEmpty()) {

                    MPUKmartShippedUpdateRequest mpuKmartShippedUpdateRequest = new MPUKmartShippedUpdateRequest();
                    mpuKmartShippedUpdateRequest.setStoreNumber(unit_id);
                    mpuKmartShippedUpdateRequest.setAssociateId("9999");
                    mpuKmartShippedUpdateRequest.setOrders(storeSubOrderIds);
                    orderRetrievalClient.updateKmartOrdersToShipped(mpuKmartShippedUpdateRequest);
                }
            }
		} catch (Exception e) {
			MANIFEST_LOGGER.error("Error updating status for " + unit_id 
					+ " for pkgs: " + JSONSerializer.serialize(trackingNums));
		}
	}
	
}
