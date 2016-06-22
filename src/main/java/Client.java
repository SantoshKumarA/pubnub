/**
 * 
 */
package com.rest;

import java.net.URI;
import java.net.URISyntaxException;

public class Client {

	private RestClientUtil restClient = null;
	private String url = "http://localhost:8080/booking";
	private String message = "hello";
	
	public Client() {
		restClient = new RestClientUtil(1000 * 60 * 6);
	}

	/** 
	 * To test in standalone mode for invoking REST call with POST method..
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		new Client().invokeRest();
	}

	/**
	 * can be invoked from outside also..
	 */
	public void invokeRest() {
		RestClientUtil.Response response;
		try {
			System.out.println("starting..");
			response = restClient.post(new URI(url), message);
			System.out.println("posted..");
			
			if(!response.getStatusCode().equals(RestClientUtil.StatusCode.OK)){
				System.out.println("Parking Management booking request processed..");
	        }
			System.out.println("completed..");
		} catch (URISyntaxException e1) {
			e1.printStackTrace();
		}
		finally {
			if(restClient != null){
	            try{
	                restClient.close();
	            }catch(Exception e){
	                System.out.println("Failed to close the rest client");
	            }
	        }
		}
	}
	
	public void setMessage(String message)
	{
		this.message = message;
	}

}
