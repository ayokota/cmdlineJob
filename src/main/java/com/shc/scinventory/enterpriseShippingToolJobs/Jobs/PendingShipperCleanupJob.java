package com.shc.scinventory.enterpriseShippingToolJobs.Jobs;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.shc.scinventory.enterpriseShippingToolJobs.Bindings.MPU.MPUKmartShippedUpdateRequest;
import com.shc.scinventory.enterpriseShippingToolJobs.Clients.OrderRetrievalClient;
import com.shc.scinventory.enterpriseShippingToolJobs.Utilities.FileReader;
import com.shc.scinventory.enterpriseShippingToolJobs.Utilities.JSONSerializer;

@Component
public class PendingShipperCleanupJob {

	
    @Autowired
    OrderRetrievalClient orderRetrievalClient;
	
	public void run () {
    	try {
			String source = FileReader.readResource("pendingShipperCleanup.csv");
			
			for(String line : source.split("\n")) {
				String[] segments = line.split(",");
				MPUKmartShippedUpdateRequest mpuKmartShippedUpdateRequest = new MPUKmartShippedUpdateRequest();
				mpuKmartShippedUpdateRequest.setAssociateId("9999");
				mpuKmartShippedUpdateRequest.setStoreNumber(segments[0]);
				mpuKmartShippedUpdateRequest.getOrders().add(segments[2]);
				//System.out.println(JSONSerializer.serialize(mpuKmartShippedUpdateRequest));
				orderRetrievalClient.updateKmartOrdersToShipped(mpuKmartShippedUpdateRequest);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		
	}
}

