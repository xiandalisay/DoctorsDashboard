/*
 * 
 * Updated By: Christian Joseph Dalisay\
 * Updated On: 05/15/14
 */
package com.example.android.navigationdrawerexample;

import java.util.ArrayList;

import com.example.database.DepartmentAdapter;
import com.example.database.EncounterAdapter;
import com.example.model.Encounter;
import com.example.model.Preferences;
import com.example.model.Rest;
import com.example.parser.DepartmentParser;
import com.example.parser.EncounterParser;
import com.example.parser.PatientParser;

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
		getEncounterFromTag();
		
		finish();
	}

	private void submitTag() {
		
		rest = new Rest("POST");
		
		/* setup API URL */
		rest.setURL(
					"http://121.97.45.242/segservice"+ //getBaseURL() +
					"/encounter/tagpatient/"
					);
		
		rest.addRequestParams("encounter_nr", encounter_id + "");
		rest.addRequestParams("doctor_nr", getPersonnelNumber() + "");
		
		/* process request service request */
		rest.execute();
		
		/* check if connection was successful */
		while(rest.getContent() == null){}
		
		System.out.println("Data Received:\n" + rest.getContent()); 
			
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

	/* Gets specific encounter from the API by encounter_id */
	public void getEncounterFromTag() {
		if(isNetworkAvailable()){
	
			Rest rest = new Rest("GET",this);
			/* setup API URL */
			rest.setURL(
						"http://121.97.45.242/segservice"+ 
						"/encounter/show/"
						);
			
			rest.addRequestParams("id", encounter_id + "");
			
			/* process request service request */
			rest.execute();
			
			/* check if connection was successful */
			while(rest.getContent() == null){}
			
			System.out.println("Data Received:\n" + rest.getContent()); 
			
			if(rest.getResult()) {
				String content = rest.getContent();
				System.out.println(content);
				EncounterParser encounter_parser = new EncounterParser(content);
				ArrayList<Encounter> encounters = encounter_parser.getEncounters();
				EncounterAdapter enc = new EncounterAdapter(this);
				enc.insertDoctorEncounter(encounters.get(0).getEncounterId(),Preferences.getPersonnelPreference(this));
				enc.insertEncounters(encounters);
			}
		} 
		
	}

	

		
}
