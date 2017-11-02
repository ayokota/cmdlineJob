package com.shc.scinventory.enterpriseShippingToolJobs.Clients;

import javax.ws.rs.core.MediaType;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientHandlerException;
import com.sun.jersey.api.client.WebResource;

@Component
public class ShippingToolClient {
	private static final Logger LOG = Logger.getLogger(ShippingToolClient.class);

	@Value("${EST_URL}")
	private String estUrl;
	
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
    
	public String postAPICall(String jsonRequest, String apiUrl) {
		String response = null;
		try {
			String requestUrl = new StringBuilder().append(estUrl).append(apiUrl).toString();

			LOG.debug("end point: " + requestUrl
					+"\n body: \n " + jsonRequest);

			WebResource resource = getWebResource(requestUrl);
			response = resource.type(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).post(String.class, jsonRequest);

		} catch (ClientHandlerException e) {
			LOG.error("An error has occured in postAPICall method with error msg: " + e.getMessage(), e);
		} catch (Exception e) {

			LOG.error("An error has occured in postAPICall method with error msg: " + e.getMessage(), e);
		}
		return response;
	}
}
