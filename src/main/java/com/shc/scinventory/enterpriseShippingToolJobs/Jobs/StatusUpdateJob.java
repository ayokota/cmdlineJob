package com.shc.scinventory.enterpriseShippingToolJobs.Jobs;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.shc.scinventory.enterpriseShippingToolJobs.Bindings.EST_updateKmartToPending.KmartPendingUpdateRequest;
import com.shc.scinventory.enterpriseShippingToolJobs.Clients.OrderRetrievalClient;
import com.shc.scinventory.enterpriseShippingToolJobs.Utilities.FileReader;
import com.shc.scinventory.enterpriseShippingToolJobs.Utilities.JSONSerializer;

@Component
public class StatusUpdateJob {
    @Autowired
    OrderRetrievalClient orderRetrievalClient;
    
    public void run() {
    	try {
			String source = FileReader.readResource("kmartUpdateToPending.txt");
			source = processRawSource (source);
			
			for(String line : source.split("\n")) {
				String updateString = line.split("\\|")[1];
				KmartPendingUpdateRequest kmartPendingUpdateRequest = JSONSerializer.deserialize(updateString, KmartPendingUpdateRequest.class);
				//System.out.println(kmartPendingUpdateRequest.getStoreNumber());
			    orderRetrievalClient.updateKmartOrders(updateString, kmartPendingUpdateRequest.getStoreNumber());

			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

	private String processRawSource(String source) {
		source = source.replace("\"data\\\":{\\\"", "\"data\\\":|{\\\"");
		source = source.replace("}]}]},\\\"headers", "}]}]}|,\\\"headers");
		source = source.replace("\\\"", "\"");

		return source;
	}
}
