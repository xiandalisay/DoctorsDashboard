package com.example.android.navigationdrawerexample;

import com.example.model.Preferences;
import com.example.model.Rest;

import android.os.Bundle;

public class TagPatientActivity extends InitialActivity {

	private Rest rest;
	
	private int encounter_id;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		retrieveBundle();
		
		System.out.println(encounter_id);
		
		submitTag();
	}

	private void submitTag() {
		
		rest = new Rest("POST");
		
		/* setup API URL */
		rest.setURL(
					"http://121.97.45.242/segservice"+ //getBaseURL() +
					"/encounter/tagpatient/"
					);
		
		rest.addRequestParams("doctor_nr", "100055"); //getPersonnelNumber() 
		rest.addRequestParams("encounter_nr", encounter_id + ""); //getPersonnelNumber() 

		System.out.println(rest.getURL());
		/* process request service request */
		rest.execute();
		
		/* check if connection was successful */
		while(rest.getContent() == null){}
		
		System.out.println("Data Received:\n" + rest.getContent()); 
			
		//System.out.println("processing request..");
			
	}

	/* retrieves base_url */
	private String getBaseURL() {
		return Preferences.getBaseURL(this);
	}
	
	/* retrieves base_url */
	private int getPersonnelNumber() {
		return Preferences.getPersonnelNumber(this);
	}

	/* retrieve passed data from parent intent */
	private void retrieveBundle() {
		intent = getIntent();
		extras = intent.getExtras();
		
		encounter_id = extras.getInt("EXTRA_ENCOUNTER_ID");
	}

	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		
		finish();
	}

	

	

		
}
