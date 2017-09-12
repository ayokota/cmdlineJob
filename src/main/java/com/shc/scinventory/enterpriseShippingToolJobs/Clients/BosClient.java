package com.shc.scinventory.enterpriseShippingToolJobs.Clients;

import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientHandlerException;
import com.sun.jersey.api.client.WebResource;

@Component
public class BosClient {
	private static final Logger LOG = Logger.getLogger(BosClient.class);


	@Value("${BOS_URL}")
	private String bosUrl;
	@Value("${getBoxInfoUrl}")
	public  String getBoxInfoUrl ;
	@Value("${setBoxInfoUrl}")
	public  String setBoxInfoUrl ;
	@Value("${getOptimizedBoxURL}")
	public  String getOptimizedBoxURL ;
	@Value("${getOptimizedShipmodeUrl}")
	public  String getOptimizedShipmodeUrl ;

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
    
    public String getBoxInfo (String dcUnitId) {
    	String response = null;
    	try {
    		String requestUrl = new StringBuilder().append(bosUrl).append(getBoxInfoUrl).append(dcUnitId).toString(); 
			WebResource resource = getWebResource(requestUrl);

			LOG.debug("[GET] end point: " + requestUrl);

			response = resource.accept(MediaType.APPLICATION_JSON_TYPE).header(HttpHeaders.USER_AGENT, "").get(String.class);

    	} catch (Exception e) {
			LOG.error("An error has occured in getBoxInfo method with error msg: " + e.getMessage(), e);
    	}
    	return response;
    }

	public String postAPICall(String jsonRequest, String apiUrl) {
		String response = null;
		try {
			String requestUrl = new StringBuilder().append(bosUrl).append(apiUrl).toString();

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
