package com.shc.scinventory.enterpriseShippingToolJobs.Clients;

import javax.ws.rs.core.MediaType;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.shc.scinventory.enterpriseShippingToolJobs.Exception.ShippingToolException;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;

@Component
public class ShipipngServiceClient {
	
    private static final Logger LOG = Logger.getLogger(ShipipngServiceClient.class);

    @Value("${SHIPPING_SERVICE_API}")
    private String shippingServiceUrl;
    
    private Integer connectionTimeout = 200000;
    private Integer readTimeout = 100000;
    
    private Client client;

    public WebResource getWebResource(String requestEndpoint) {
        if (client == null) {
            client = Client.create();
            client.setConnectTimeout(connectionTimeout);
            client.setReadTimeout(readTimeout);
        }
        return client.resource(requestEndpoint);
    }
    
    public String postAPI (String request, String API) {
        String response = null;
        try {
            String requestUrl = new StringBuilder().append(shippingServiceUrl).append(API).toString();

            LOG.debug("end point: " + requestUrl
                    +"\n body: \n " + request);

            WebResource resource = getWebResource(requestUrl);
            response = resource
                    .type(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON)
                    .post(String.class, request);
        } catch (Exception e) {
        	throw new ShippingToolException(e.getMessage());
        }
        return response;
    }
}
