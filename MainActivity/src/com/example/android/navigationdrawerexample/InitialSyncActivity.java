package com.example.android.navigationdrawerexample;

import com.example.model.Rest;

import android.content.Intent;
import android.os.Bundle;

public class InitialSyncActivity extends InitialActivity {

	private Rest rest;
	
	private String base_url;
	private String personnel_id;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		intent = getIntent();
		extras = intent.getExtras();
		
		base_url = extras.getString("EXTRA_BASE_URL");
		personnel_id = extras.getString("EXTRA_PERSONNEL_ID");
		
		retrieveEncountersAPI();
		
		showLoginActivity();
		
	}
	
	/* starts Login Activity */
	public void showLoginActivity(){
		Intent intent = new Intent(this, LoginActivity.class);
		intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK); 
		startActivity(intent);
	}

	@Override
	protected void onPause() {
		super.onPause();
		
		finish();
	}

	private void retrieveEncountersAPI() {
		
		rest = new Rest("GET");
		
		/* get base_url associated with doctor */
		rest.setURL(
				base_url + 
				"/encounter/tagged/doctor_nr/" +
				personnel_id ); 
		
		logMessage(rest.getURL());
		
		rest.execute();

		logMessage("processing request..");
			
		/* wait until data is retrieved, there is delay in retrieving data*/
		while(rest.getContent() == null){} 
			
		System.out.println("Data Received:\n" + rest.getContent());
		
	}
		
}
