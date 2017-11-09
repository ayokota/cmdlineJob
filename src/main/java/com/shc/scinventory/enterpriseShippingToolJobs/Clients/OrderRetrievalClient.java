package com.shc.scinventory.enterpriseShippingToolJobs.Clients;

import javax.ws.rs.core.MediaType;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.shc.scinventory.enterpriseShippingToolJobs.Bindings.MPU.MPUKmartShippedUpdateRequest;
import com.shc.scinventory.enterpriseShippingToolJobs.Utilities.JSONSerializer;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
@Component
public class OrderRetrievalClient {
    private static final Logger LOG = Logger.getLogger(OrderRetrievalClient.class);
    private static final Logger MANIFEST_LOGGER = Logger.getLogger("manifestLogger");
	
    @Value("${KMART_SHIPPED_UPDATE_URL}")
    private String kmart_shipped_update_url;

    private Integer connectionTimeout = 60000;
    private Integer readTimeout = 60000;


    private Client client;

    public WebResource getWebResource(String requestEndpoint) {
        if (client == null) {
            client = Client.create();
            client.setConnectTimeout(connectionTimeout);
            client.setReadTimeout(readTimeout);
        }
        return client.resource(requestEndpoint);
    }
    
    public String buildKmartUpdateToShippedUrl(String dcUnitId) {
        if (dcUnitId == null || dcUnitId.isEmpty()) {
            return null;
        }
        StringBuilder queryUrl = new StringBuilder();
        queryUrl.append(kmart_shipped_update_url).append(dcUnitId);
        return queryUrl.toString();
    }

    public String updateKmartOrdersToShipped(MPUKmartShippedUpdateRequest mpuKmartShippedUpdateRequest) {
        String response = "";
        try {
            String updateUrl = buildKmartUpdateToShippedUrl(mpuKmartShippedUpdateRequest.getStoreNumber());

            String request = JSONSerializer.serialize(mpuKmartShippedUpdateRequest);

            LOG.debug("Update kmart order url:  " + updateUrl);
            LOG.debug("Update kmart order request:  " + request);

            WebResource resource = getWebResource(updateUrl);
            String itemResponse = resource.type(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).post(String.class, request);

            LOG.debug("Update kmart order response:  " + itemResponse);

            MANIFEST_LOGGER.error("url:  " + updateUrl
                    + "\nreq: " + request
                    + "\nresp: " + itemResponse);
            
            response = itemResponse;

        } catch (Exception e) {
            LOG.error("An error has occured while calling update API for kmart " +
                    "\nstore: " + mpuKmartShippedUpdateRequest.getStoreNumber() +
                    "\nrequest: " + JSONSerializer.serialize(mpuKmartShippedUpdateRequest) +
                    "\nwith msg: " + e.getMessage());
            MANIFEST_LOGGER.error("An error has occured while calling update API for kmart " +
                    "\nstore: " + mpuKmartShippedUpdateRequest.getStoreNumber() +
                    "\nrequest: " + JSONSerializer.serialize(mpuKmartShippedUpdateRequest) +
                    "\nwith msg: " + e.getMessage());
            response = e.getMessage();

        }

        return response;
    }
}
