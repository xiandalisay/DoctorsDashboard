/*
 * @author Jose Martin Ipong 5/14/2014
 */
package com.example.parser;

import java.util.ArrayList;

import com.example.model.Encounter;
import com.example.model.LabRequest;
import com.google.resting.json.JSONArray;
import com.google.resting.json.JSONException;
import com.google.resting.json.JSONObject;

public class LabRequestParser extends JSONParser{
	private ArrayList<LabRequest> labrequests;
	//private HashMap<String, String> data = new HashMap<String, String>();
	//private JSONObject json_childNode;
	private JSONArray json_array;
		
	private final String ENCOUNTER_NR 	= "encounter_nr";
	private final String REFNO 	= "refno";
	private final String SERVICE_CODE 	= "service_code";
	private final String SERVICE_NAME = "service_name";
	private final String QUANTITY = "quantity";
	
	

	
	public LabRequestParser(String content) throws NullPointerException{
		
		labrequests = new ArrayList<LabRequest>();
		try {
			json_array = new JSONArray(content);
		} catch (JSONException e) {
			System.out.println("JSON error");
		}
		
	}
	
	public ArrayList<LabRequest> getLabRequests(){
		
		try{
			labrequests = new ArrayList<LabRequest>();
		
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
				
				
				LabRequest labrequest = new LabRequest(refno, encounter_nr, service_code, service_name, Integer.parseInt(quantity));
				labrequests.add(labrequest);
				//System.out.println(pid);			
	        }
		}catch(Exception e){System.out.println(e.toString() + " pong was here too :)");}
        
        return labrequests;
	}
}
