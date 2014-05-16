/*
 * Edited by Jose Martin Ipong 5/15/2014, added online functionalities
 */

package com.example.android.navigationdrawerexample;

import java.util.ArrayList;

import android.app.ExpandableListActivity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnGroupExpandListener;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.database.DatabaseAdapter;
import com.example.database.EncounterAdapter;
import com.example.model.Encounter;
import com.example.model.Patient;
import com.example.model.Rest;
import com.example.model.Soap;
import com.example.parser.EncounterParser;
import com.example.parser.PatientParser;

public class PatientInfoActivity extends ExpandableListActivity {
	
	private ArrayList<String> parentItems = new ArrayList<String>();
	private ArrayList<Object> childItems = new ArrayList<Object>();
	private ArrayList<Object> child;
	
	private Encounter encounter;
	private Patient patient;
	
	private ArrayList<Encounter> encounters;
	private ArrayList<Patient> patients;
	
	private Button tag;
	private String tagText;
	
	private int patient_id;
	private int encounter_id;
	final String url_patient = "http://121.97.45.242/segservice/patient/show";
	final String url_encounter = "http://121.97.45.242/segservice/encounter/show";
	final static int FIRST_PATIENT = 0;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_patient_info);
		// Show the Up button in the action bar.
		setupActionBar();
		
		Intent intent = getIntent();
		Bundle extras = intent.getExtras();
		patient_id = extras.getInt("EXTRA_PATIENT_ID");
		
		tag = (Button) findViewById(R.id.TagPatientButton);
		
		DatabaseAdapter db = new DatabaseAdapter(this);
		if(isNetworkAvailable()){
			
			//Rest for patient
			Rest rest_patient = new Rest("GET", this);
			rest_patient.setURL(url_patient);
			rest_patient.addRequestParams("id", Integer.toString(patient_id));
			rest_patient.execute();
			while(rest_patient.getContent() == null){}
			
			if(rest_patient.getResult()){
				String content = rest_patient.getContent();
				PatientParser patient_parser = new PatientParser(content);
				patients = patient_parser.getPatients();
				patient = patients.get(FIRST_PATIENT);
				System.out.println(patient);
			}
			
			//Rest for encounter
			Rest rest_encounter = new Rest("GET", this);
			rest_encounter.setURL(url_encounter);
			//rest_encounter.addRequestParams("id", Integer.toString(patient_id));
			rest_encounter.execute();
			while(rest_encounter.getContent() == null){}
			if(rest_encounter.getResult()){
				String content = rest_encounter.getContent();
				EncounterParser encounter_parser = new EncounterParser(content);
				encounters = encounter_parser.getEncounters();
				
			}
		
			
		}		
		else{
		    patient = db.getPatientProfile(patient_id);
			encounters = db.getPatientEncounter(patient_id);
		}
		
		EditText nameEditText = (EditText) findViewById(R.id.FullName);
		EditText genderEditText = (EditText) findViewById(R.id.Gender);
		EditText addressEditText = (EditText) findViewById(R.id.Address);
		EditText ageEditText = (EditText) findViewById(R.id.Age);
		CheckBox histOfSmokingCheckBox = (CheckBox) findViewById(R.id.HistOfSmoking);
		CheckBox histOfDrinkingCheckBox = (CheckBox) findViewById(R.id.HistOfDrinking);
		histOfSmokingCheckBox.setChecked(true);
		histOfDrinkingCheckBox.setChecked(true);
		String nametext = patient.getNameLast() + ", " + patient.getNameFirst();
		String gendertext;
		
		if(patient.getSex().equals("M")){
			gendertext = "Male";
		}
		else{
			gendertext = "Female";
		}
		
		addressEditText.setText(patient.getAddress());
		nameEditText.setText(nametext);
		genderEditText.setText(gendertext);
		String age = String.valueOf(patient.getAge());
		ageEditText.setText(age);
		
		
		/**
		 * @author Jake Randolph B Muncada
		 * @date 5/16/2014
		 * 
		 * Edited: Put the expandable list here, instead of listview
		 */
		final ExpandableListView expandableList = getExpandableListView(); // you can use (ExpandableListView) findViewById(R.id.list)
		//final ExpandableListView expandableList = (ExpandableListView) findViewById(R.id.list);

		expandableList.setDividerHeight(2);
		expandableList.setGroupIndicator(null);
		expandableList.setClickable(true);
		
		setGroupParents();
		setChildData(patient_id, encounter_id);
		
		final ExpListAdapter adapter = new ExpListAdapter(parentItems, childItems);

		adapter.setInflater((LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE), this);
		expandableList.setAdapter(adapter);
		
		expandableList.setOnGroupExpandListener(new OnGroupExpandListener(){
			@Override
			public void onGroupExpand(int groupPosition){
				MyOnClickListener.setLastExpandedGroupPosition(groupPosition);
				int len = adapter.getGroupCount();
				
				for(int i=0; i<len; i++){
					if(i != groupPosition){
						expandableList.collapseGroup(i);
					}
				}
				
				//Toast.makeText(getApplicationContext(),"groupPosition:"+groupPosition+" ", Toast.LENGTH_SHORT).show();
			}
		});
		
		
		
		/* duplicate code with LINE 41  */
		patient = db.getPatientProfile(patient_id);
		ListView listview = (ListView) findViewById(R.id.servicesList);
		ArrayAdapter<Encounter> arrayAdapter = new ArrayAdapter<Encounter>(getApplicationContext(), android.R.layout.simple_list_item_1, encounters){
			@Override
			public View getView(int position, View ConvertView, ViewGroup parent){
				View view = super.getView(position, ConvertView, parent);
				TextView text = (TextView) view.findViewById(android.R.id.text1);
				text.setTextColor(Color.BLACK);
				return view;
			}
		};
		listview.setAdapter(arrayAdapter);
		listview.setOnItemClickListener(new OnItemClickListener() {
					
					@Override
					public void onItemClick(AdapterView<?> parent, View view, int position,
							long id) {
						TextView text 	= (TextView) view.findViewById(android.R.id.text1);
						encounter = encounters.get(position);
						int eid = encounter.getEncounterId();
						Bundle extras = new Bundle();
						extras.putInt("EXTRA_ENCOUNTER_ID", eid);
						
						Intent intent = new Intent(getApplicationContext(), PatientEncounterActivity.class);
						intent.putExtras(extras);
						startActivity(intent);
					}
				});
	}
	
	/**
	 * @author Jake Randolph B Muncada
	 * @date 5/16/2014
	 */
	public void setGroupParents() {
		parentItems.add("Medical History");
		parentItems.add("Previous Requests");
		parentItems.add("Referrals");
		parentItems.add("Notes");
	}
	
	/**
	 * @author Jake Randolph B Muncada
	 * @date 5/16/2014
	 * @param patient_id
	 * @param encounter_id
	 * @param date_encountered
	 */
	public void setChildData(int patient_id, int encounter_id) {

		ArrayList<Object> child = new ArrayList<Object>();
		DatabaseAdapter db = new DatabaseAdapter(getApplicationContext());
		
		ArrayList<Encounter> encounterList = db.getPatientEncounter(patient_id);
		for (int i = 0; i < encounterList.size(); i++) {
			child.add(encounterList.get(i));
		}
		childItems.add(child);
		child = new ArrayList<Object>();
		
		for (int i = 0; i < encounterList.size(); i++) {
			child.add(encounterList.get(i));
		}
		childItems.add(child);
		child = new ArrayList<Object>();
		
		for (int i = 0; i < encounterList.size(); i++) {
			child.add(encounterList.get(i));
		}
		childItems.add(child);
		child = new ArrayList<Object>();
		
		ArrayList<Soap> soapList = db.getDoctorNotes(encounter_id);
		child.add("ADD NEW NOTES");
		for (int i = 0; i < soapList.size(); i++) {
			child.add(soapList.get(i));
		}
		childItems.add(child);
		
	
	}
	
	/* called when "refer" button is clicked */
    public void showReferPatient(View view){
	    Intent intent = new Intent(this,ReferralActivity.class);
		Bundle extras = new Bundle();
		extras.putInt("EXTRA_PATIENT_ID", patient.getPid());
		extras.putString("EXTRA_PATIENT_NAME_LAST", patient.getNameLast());
		extras.putString("EXTRA_PATIENT_NAME_FIRST", patient.getNameFirst());
		intent.putExtras(extras);
		startActivity(intent);
	}
	
	/* called when the "Tag" button is clicked */
	public void handleTagClick(View view){
		tagText = tag.getText().toString();
		
		alertMessage("clicked");

		EncounterAdapter enc = new EncounterAdapter(this);
		if(tagText.equals("Tag Patient")){
			handleTagPatient();
			//Add here code to save encounter details to mobile DB
		
		tag.setText("Undo Tag");
		}
		else{
			handleUntagPatient();
			//Add here code to remove encounter details from mobile DB
			enc.deleteDoctorEncounter(encounter_id);
			tag.setText("Tag Patient");
		}		
	}
		
	/* handles tagging patient process */
	private void handleTagPatient() {
		Bundle extras = new Bundle();
		extras.putInt("EXTRA_ENCOUNTER_ID", encounter_id);
		Intent intent = new Intent(this, TagPatientActivity.class);
		intent.putExtras(extras);
		
		startActivity(intent);
		
		alertMessage("Successfully Tagged");
	}
		
	/* handles untagging patient process */
	private void handleUntagPatient() {
		Bundle extras = new Bundle();
		extras.putInt("EXTRA_ENCOUNTER_ID", encounter_id);
		
		Intent intent = new Intent(this, UntagPatientActivity.class);
		intent.putExtras(extras);
		
		startActivity(intent);
		
		alertMessage("Successfully Untagged");
	}
	
	private boolean isEncounterExists(int encounter_id){
		EncounterAdapter db = new EncounterAdapter(this);
		
		return db.isEncounterExists(encounter_id);
	}
	
	/**
	 * Set up the {@link android.app.ActionBar}.
	 */
	private void setupActionBar() {

		getActionBar().setDisplayHomeAsUpEnabled(true);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.patient_info, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			// This ID represents the Home or Up button. In the case of this
			// activity, the Up button is shown. Use NavUtils to allow users
			// to navigate up one level in the application structure. For
			// more details, see the Navigation pattern on Android Design:
			//
			// http://developer.android.com/design/patterns/navigation.html#up-vs-back
			//
			NavUtils.navigateUpFromSameTask(this);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	public boolean isNetworkAvailable() {
	    ConnectivityManager connectivityManager 
	          = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
	    NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
	    return activeNetworkInfo != null && activeNetworkInfo.isConnected();
	}
	
	/* displays message dialog */
    protected void alertMessage(String message){
    	Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }
}
