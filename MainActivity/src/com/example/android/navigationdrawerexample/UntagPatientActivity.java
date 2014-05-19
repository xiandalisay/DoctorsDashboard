package com.example.android.navigationdrawerexample;

import java.util.ArrayList;

import com.example.database.CanvasAdapter;
import com.example.database.DatabaseAdapter;
import com.example.database.DoctorAdapter;
import com.example.database.DoctorEncounterAdapter;
import com.example.database.EncounterAdapter;
import com.example.database.LabRequestAdapter;
import com.example.database.NotesAdapter;
import com.example.database.ReferralAdapter;
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
		deleteUntaggedEncounter();
		
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

	/* 
	 * if only one doctor tagged it, deletes the data of the patient 
	 * else deletes the row of the relation
	 */
	private void deleteUntaggedEncounter() {
		ArrayList<Integer> enc_ids = new ArrayList<Integer>();
		/* diba dapat pid ? */
		//EncounterAdapter enc = new EncounterAdapter(this);
		//enc_ids = enc.getEncounterIds(encounter_id);
		
		ArrayList<Integer> doc = new ArrayList<Integer>();
		DoctorEncounterAdapter doc_enc = new DoctorEncounterAdapter(this);
		doc = doc_enc.countDoctorsByEncounter(encounter_id,Preferences.getPersonnelNumber(this));
		doc_enc.deleteDoctorEncounter(enc_ids);
		for(int i = 0; i < doc.size(); i++) {
			if(doc.get(i) > 1) {
				ReferralAdapter ref = new ReferralAdapter(this);
				ref.deleteReferral(encounter_id);
				LabRequestAdapter req = new LabRequestAdapter(this);
				req.deleteLabRequest(encounter_id, getPersonnelNumber());
				NotesAdapter notes = new NotesAdapter(this);
				notes.deleteNotes(encounter_id, getPersonnelNumber());
			}
		}
	}		
}
