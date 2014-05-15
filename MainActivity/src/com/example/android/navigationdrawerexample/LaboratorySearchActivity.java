

package com.example.android.navigationdrawerexample;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;

import com.example.database.DatabaseAdapter;
import com.example.database.EncounterAdapter;
import com.example.model.Patient;
import com.example.model.Rest;
import com.example.parser.PatientParser;

public class LaboratorySearchActivity extends BaseActivity {
	
	private Rest rest;
	private Patient patient;
	private PatientParser parser;
	
	private ArrayList<Patient> patients;
	private ArrayAdapter<Patient> arrayAdapter;
	private ListView listview;
	
	private final String url = "http://121.97.45.242/segservice/patient/show/";
	
	private EditText search;
	
	private String content;
	private int encounter_id;
	private int patient_id;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_laboratory_search);
		
		search = (EditText) findViewById(R.id.search);
		
		/* initialize patient list */
		patients = new ArrayList<Patient>();
		
		if(isNetworkAvailable()){

			rest = new Rest("GET",this);
			rest.setURL(url);
			rest.execute();
			
			while(rest.getContent() == null){}
			
			if(rest.getResult()){
				content = rest.getContent();
				parser = new PatientParser(content);
				patients = parser.getPatients();
			}
		} 
		else{
			DatabaseAdapter db = new DatabaseAdapter(this);
			patients = db.searchPatient("");
		}
		
		setupArrayAdapter();
		        	
        setupListView();
   
        setupSearchEventHandle();
        
	}

			

	private void setupArrayAdapter() {
		
		listview = (ListView) findViewById(R.id.list);
		arrayAdapter = new ArrayAdapter<Patient>(this, android.R.layout.simple_list_item_2, android.R.id.text1, patients){
        	
			/* declarations */
			private TextView text1, text2; 
			private View view;
			
			private String displayname = "";
			private String displayinfo = "";
			
			/* method to override the getView method of ArrayAdapter, this changes the color of the text view */
        	
			@Override
        	public View getView(int position, View convertView, ViewGroup parent) {
				
        		view = super.getView(position, convertView, parent);
        	    
        		text1 = (TextView) view.findViewById(android.R.id.text1);
        	    text2 = (TextView) view.findViewById(android.R.id.text2);
        	    
        	    text1.setTextColor(Color.BLACK);
        	    text2.setTextColor(Color.BLACK);
        	    
        	    /* retrieve patient object from specific position */
        	    patient = patients.get(position);
        	   
        	    /* retrieve name of patient */
        	    displayname = patient.getNameLast() + ", " + patient.getNameFirst();
        	    
        	    /* add gender of patient to info to be displayed */
        	    if(patient.getSex().toLowerCase().equals("m")){
        	    		displayinfo = displayinfo + "Male";
        	    }
        	    else{
        	    		displayinfo = displayinfo + "Female";
        	    }
        	    
        	    //logMessage(displayinfo);
        	    
        	    displayinfo = displayinfo + " : " + patient.getBirthdate().substring(0,10);
        	    
        	    text1.setText(displayname);
        	    text2.setText(displayinfo);
        	    
        	    return view;
        	  }
        	};
        	
	}

	private void setupListView() {
		
		listview.setAdapter(arrayAdapter); 
		listview.setOnItemClickListener(new OnItemClickListener() {
			
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				
				/* getting values from selected ListItem */
				TextView text = (TextView) view.findViewById(android.R.id.text1);
				String patientname = text.getText().toString();
				
				/* starting single patient info activity */
				patient = patients.get(position);
				patient_id = patient.getPid();
				
				/*
				encounter_id = getLatestEncounter(patient_id);
				extras.putInt("EXTRA_ENCOUNTER_ID", encounter_id);
				alertMessage(encounter_id+"");
				 */
				
				/* saves the patient_id and encounter_id to be passed to the next activity */
				extras = new Bundle();
				extras.putInt("EXTRA_PATIENT_ID", patient_id);
				
				/* start next activity Patient Info (2nd Page) */
				intent = new Intent(getApplicationContext(), PatientInfoActivity.class);
				intent.putExtras(extras);
				
				startActivity(intent);
			}
		});
	}
	

	private void setupSearchEventHandle() {
		//event handler for pressing the search button on soft keyboard
		search.setOnEditorActionListener(new OnEditorActionListener() {
				
				/* declarations */
				private DatabaseAdapter db;
			    private EditText search;
				private String searchtext;
			    
				@Override
			    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
			        
					boolean handled = false;
			        
			        if (actionId == EditorInfo.IME_ACTION_SEARCH) {
			        	search = (EditText) findViewById(R.id.searchView1);
			        	
			        	searchtext = search.getText().toString();
			        	
			            db = new DatabaseAdapter(getApplicationContext());
			            patients = new ArrayList<Patient>();
			            
			            StringTokenizer t = new StringTokenizer(searchtext, ",");
			            String last = "";
			            String first = "%";
			            
			            if(t.countTokens() == 1){
			            	last = t.nextToken();
			            }
			            else{
			            	last = t.nextToken();
			            	first = t.nextToken();
			            }
			            
			            try{
			            	/* checks if device is connected to the network */
			            	if(isNetworkAvailable()){ 
			          
			        			rest = new Rest("GET");

			        			rest.setURL(url);
			        			
			        			rest.addRequestParams("name_last", last); //adds lastname as parameter to url
			        			rest.addRequestParams("name_first", first); //adds firstname as parameter to url
			        			
			        			rest.execute();
			        			
			        			while(rest.getContent() == null){}
			        			
			        			if(rest.getResult()){
			        				
			        				content = rest.getContent();
			        				logMessage(content);
			        				
			        				parser = new PatientParser(content);
			        				
			        				/* get patients from online source */
			        				patients = parser.getPatients(); 
			        			}
			        		}
			            	else{ 	/* gets patients from mobile database */		            		
			            		patients = db.searchPatient(searchtext);
			            	}
				           
			            	setupArrayAdapter();
			            	setupListView();
			            	
				            InputMethodManager inputManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE); 
				            inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
				            
			            }
			            catch(Exception e){
			            	System.out.println(e);
			            	alertMessage(e.toString());
			            }
			            handled = true;
			        }
			        return handled;
			    }
			});	
	}
	
	/* retrieves latest encounter of the patient */
	private int getLatestEncounter(int patient_id) {
		EncounterAdapter db = new EncounterAdapter(this);
		
		return db.getLatestEncounter(patient_id);
	}

	
	public void showPatientInfo(View view){
    	Intent intent = new Intent(this, PatientInfoActivity.class);
    	startActivity(intent);
    }
}

