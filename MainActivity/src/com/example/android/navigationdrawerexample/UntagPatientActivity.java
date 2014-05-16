package com.example.android.navigationdrawerexample;

import com.example.database.CanvasAdapter;
import com.example.database.DatabaseAdapter;
import com.example.database.DoctorAdapter;
import com.example.model.HelperSharedPreferences;
import com.example.model.Preferences;
import com.example.model.Rest;

import android.os.Bundle;

public class UntagPatientActivity extends InitialActivity {

	private Rest rest;
	
	private int encounter_id;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		retrieveBundle();
		
		submitUntag();
		deleteRelatedData();
		
		finish();
	}

	private void submitUntag() {
		
		rest = new Rest("POST");
		
		/* setup API URL */
		rest.setURL(
					"http://121.97.45.242/segservice"+ //getBaseURL() +
					"/encounter/untagpatient/"
					);
		
		rest.addRequestParams("encounter_nr", encounter_id + "");
		rest.addRequestParams("doctor_nr", Preferences.getPersonnelNumber(this) + "");
		
		/* process request service request */
		rest.execute();
		
		/* check if connection was successful */
		while(rest.getContent() == null){}
		
		System.out.println("Data Received:\n" + rest.getContent()); 
			
	}

	/* retrieve passed data from parent intent */
	private void retrieveBundle() {
		intent = getIntent();
		extras = intent.getExtras();
		
		encounter_id = extras.getInt("EXTRA_ENCOUNTER_ID");
	}

	private void deleteRelatedData() {
		DoctorAdapter doc_ad = new DoctorAdapter(this);
		//doc_ad.deleteDoctorEncounter(encounter_id);
		/*ReferralAdapter ra = new ReferralAdapter(this);
		ra.deleteReferrals(encounter_id);
		CanvasAdapter ca = new CanvasAdapter(this);
		ca.deleteCanvas(encounter_id);
		LabRequestAdapter lra = new LabRequestAdapter(this);
		lra.deleteLabRequests(encounter_id);*/
	}
	

	

		
}
