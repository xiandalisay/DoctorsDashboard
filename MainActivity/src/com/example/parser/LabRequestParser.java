/*
 * @author Jose Martin Ipong 5/14/2014
 */
package com.example.parser;

import java.util.ArrayList;
import java.util.HashMap;

import com.example.model.LabRequest;
import com.example.model.LabService;
import com.google.resting.json.JSONArray;
import com.google.resting.json.JSONException;
import com.google.resting.json.JSONObject;

public class LabRequestParser extends JSONParser{
	private ArrayList<LabRequest> requests;
	private ArrayList<LabService> services;
	private HashMap<String, String> data = new HashMap<String, String>();
	//private JSONObject json_childNode;
	private JSONArray json_array;
		
	private final String ENCOUNTER_NR 	= "encounter_nr";
	private final String REFNO 	= "refno";
	private final String SERVICE_CODE 	= "service_code";
	private final String SERVICE_NAME = "service_name";
	private final String QUANTITY = "quantity";
	
	

	
	public LabRequestParser(String content) throws NullPointerException{
		
		requests = new ArrayList<LabRequest>();
		try {
			json_array = new JSONArray(content);
		} catch (JSONException e) {
			System.out.println("JSON error");
		}
		
	}
	
	public ArrayList<LabRequest> getLabRequests(){
		
		try{
			requests = new ArrayList<LabRequest>();
		
			int lengthJsonArr = json_array.length();  
	   
	        for(int i=0; i < lengthJsonArr; i++) 
	        {
	                         /****** Get Object for each JSON node.***********/
	            JSONObject jsonChildNode = json_array.getJSONObject(i);
				
				
	                           
	                         /******* Fetch node values **********/
	            int    encounter_nr      	= Integer.parseInt(jsonChildNode.optString(ENCOUNTER_NR).toString());
	            int    refno       	= Integer.parseInt(jsonChildNode.optString(REFNO).toString());
				String service_code		= jsonChildNode.optString(SERVICE_CODE).toString();
				String service_name = jsonChildNode.optString(SERVICE_NAME).toString();
				String quantity = jsonChildNode.optString(QUANTITY).toString();
				
				
				LabRequest labrequest = new LabRequest(refno, encounter_nr, service_code, service_name, Integer.parseInt(quantity),"nothing","nothing","nothing");
				requests.add(labrequest);
							
	        }
		}catch(Exception e){System.out.println(e.toString() + " pong was here too :)");}
        
        return requests;
	}
	
	/* retrieve and parse all lab requests */
	public ArrayList<LabRequest> getRequestService(){
		
		try{			
			int lengthJsonArr = json_array.length();  
			
			/* first JSON object retrieved thru web service */
			JSONObject first_object = json_array.getJSONObject(0);
			
			/* gets first ref_no for basis if request has been changed */
			String current_request = first_object.optString(REFNO).toString();
	        
			for(int i=0; i < lengthJsonArr; i++) 
	        {
	                         /****** Get Object for each JSON node.***********/
	            JSONObject jsonChildNode = json_array.getJSONObject(i);
	                           
	                         /******* Fetch node values **********/
	            int    encounter_id      	= Integer.parseInt(jsonChildNode.optString(ENCOUNTER_NR).toString());
	            int    request_nr       	= Integer.parseInt(jsonChildNode.optString(REFNO).toString());

	            /* check if ref_no has changed, save the new request as object and add to ArrayList */
	            if(!current_request.equals(jsonChildNode.optString(REFNO).toString())){
	            	LabRequest request = new LabRequest();	
	            	
	            	request.setEncounterNumber(encounter_id);
	            	request.setRequestNumber(request_nr);
	            	request.setServices(services);
	            	
	            	requests.add(request);
	            }
	            
				String service_code			= jsonChildNode.optString(SERVICE_CODE).toString();
				String service_name 		= jsonChildNode.optString(SERVICE_NAME).toString();
				int quantity 				= Integer.parseInt(jsonChildNode.optString(QUANTITY));
				
				LabService service = new LabService();
				
				service.setServiceCode(service_code);
				service.setLabServiceName(service_name);
				service.setQuantity(quantity);
				
				services.add(service);
	        }
			
		}catch(Exception e){
			System.out.println(e.toString() + " pong was here too :)");
		}
        
		return requests;
	}
}
