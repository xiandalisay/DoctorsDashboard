package com.example.android.navigationdrawerexample;

import java.util.ArrayList;

import android.content.Intent;
import android.os.Bundle;

import com.example.database.EncounterAdapter;
import com.example.model.Encounter;
import com.example.model.Patient;
import com.example.model.Rest;
import com.example.parser.EncounterParser;
import com.example.parser.PatientParser;

public class InitialSyncActivity extends InitialActivity {

	private Rest rest;

	private ArrayList<Encounter> encounters;
	private ArrayList<Patient> patients;
	private ArrayList<String> PIDs;
	
	private String base_url;
	private String personnel_id;
	
	private final static String TAGGED_URL = "/encounter/tagged/";
	private final static String PATIENT_URL = "/patient/show/";
	private final static String ENCOUNTER_URL = "/encounter/show/";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		retrieveBundle();
		patients = new ArrayList<Patient>();
		
		PIDs = retrieveTaggedByDoctorAPI();
		
		for(int i=0; i<PIDs.size() ; i++)
			alertMessage("PID: " + PIDs.get(i));
		
		retrievePatientsAPI();
		retrieveEncountersAPI();
		//retrieveReferralsAPI();
		
		showLoginActivity();
		finish();
	}
	
	private void retrieveBundle() {
		intent = getIntent();
		extras = intent.getExtras();
		
		base_url = extras.getString("EXTRA_BASE_URL");
		personnel_id = extras.getString("EXTRA_PERSONNEL_ID");
	}

	private ArrayList<String> retrieveTaggedByDoctorAPI() {
		
		Rest rest = new Rest("GET", this, "Retrieving tagged encounters..");
		
		/* set service URL to call */
		rest.setURL(base_url + TAGGED_URL);
		
		/* add doctor_nr as parameter */
		rest.addRequestParams("doctor_nr", personnel_id);
		
		/* process request service request */
		rest.execute();
		
		/* check if connection was successful */
		System.out.println("processing request..");
			
		/* wait until data is retrieved, there is delay in retrieving data*/
		while(rest.getContent() == null){} 
			
		System.out.println("Data Received:\n" + rest.getContent());
		
		PatientParser parser = new PatientParser(rest.getContent());
		
		return parser.getPatientsPID();
	}

	private void retrievePatientsAPI() {
		for(int i=0; i<PIDs.size(); i++){
			
			Rest rest = new Rest("GET", this, "Retrieving patient profiles..");
			
			/* set service URL to call */
			rest.setURL(base_url + PATIENT_URL);
			
			/* add pid as parameter */
			rest.addRequestParams("id", PIDs.get(i));
			
			/* process request service request */
			rest.execute();
			
			/* check if connection was successful */
			System.out.println("processing request..");
				
			/* wait until data is retrieved, there is delay in retrieving data*/
			while(rest.getContent() == null){} 
				
			System.out.println("Data Received:\n" + rest.getContent());
			
			PatientParser parser = new PatientParser(rest.getContent());
			
			patients.add(parser.getPatient());
		}
	}

	private void retrieveEncountersAPI() {
		
		EncounterAdapter db = new EncounterAdapter(this);
		encounters = new ArrayList<Encounter>();
		
		for(int i=0; i<PIDs.size(); i++){
			
			/* clear contents of ArrayList */
			encounters.clear();
			
			rest = new Rest("GET", this, "");
			
			/* get base_url associated with doctor */
			rest.setURL(base_url + ENCOUNTER_URL);
			
			/* add pid as parameter */
			rest.addRequestParams("pid", PIDs.get(i));
			
			/* process request service request */
			rest.execute();
			
			/* check if connection was successful */
			System.out.println("processing request..");
				
			/* wait until data is retrieved, there is delay in retrieving data*/
			while(rest.getContent() == null){} 
				
			System.out.println("Data Received:\n" + rest.getContent());
			
			EncounterParser parser = new EncounterParser(rest.getContent());
			
			/* retrieve and parse encounters of patient(i) */
			encounters = (parser.getEncounters());
			
			/* insert encounters of patient(i) in mobile DB */
			//db.insertEncounters(encounters);
		}		
	}

	/* starts Login Activity */
	public void showLoginActivity(){
		intent = new Intent(this, LoginActivity.class);
		extras =  new Bundle();
		
		/* pass to next activity that registration is successful */
		extras.putBoolean("EXTRA_SUCCESS_REGISTER", true);
		intent.putExtras(extras);
		
		intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK); 
		
		startActivity(intent);
	}

	@Override
	protected void onPause() {
		super.onPause();
		
		finish();
	}

	
		
}
