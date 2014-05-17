package com.example.android.navigationdrawerexample;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.model.Department;
import com.example.model.ReferralReason;
import com.example.model.Rest;
import com.example.parser.ReferralReasonParser;

public class ReferralActivity extends Activity {

	private int patient_id;
	ArrayList<Department> departments;
	ArrayList<ReferralReason> reasons;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_referral);
		
		TextView textView 	= (TextView) findViewById(R.id.nameLabel);
		Intent intent 		= getIntent();
		Bundle extras 		= intent.getExtras();
		patient_id 			= extras.getInt("EXTRA_PATIENT_ID");
		String name_last 	= extras.getString("EXTRA_PATIENT_NAME_LAST");
		String name_first	= extras.getString("EXTRA_PATIENT_NAME_FIRST");
		textView.setText(name_last + ", " + name_first);
		
		Spinner referDepartments 	= (Spinner) findViewById(R.id.referDepartment);
		Spinner referReason 		= (Spinner) findViewById(R.id.referReason);
		//DepartmentAdapter departmentAdapter = new DepartmentAdapter(this);
		
		
		departments = new ArrayList<Department>();
		//departments = departmentAdapter.getDepartments();
		//ArrayAdapter<Department> array_adapter = new ArrayAdapter<Department>(this, android.R.layout.simple_spinner_item, departments);
		//referDepartments.setAdapter(array_adapter);      
		reasons = new ArrayList<ReferralReason>();
		
		
		if(isNetworkAvailable()){
			try{
				Rest rest = new Rest("GET", this);
				rest.setURL("http://121.97.45.242/segservice/encounter/reasonreferral/");
				rest.execute();
				while(rest.getContent() == null){}
				
				if(rest.getResult()){
					String content = rest.getContent();
					ReferralReasonParser referralReasonParser = new ReferralReasonParser(content);
					reasons = referralReasonParser.getReasons();
					
					ArrayAdapter<ReferralReason> reasonAdapter = new ArrayAdapter<ReferralReason>(this, android.R.layout.simple_spinner_item, reasons);
					referReason.setAdapter(reasonAdapter);
				}
			}catch(Exception e){System.out.println(e.toString());}
		}else
			Toast.makeText(this, "Connection unavailable", Toast.LENGTH_SHORT).show();;
		
	}
	
	//to check if the phone has access to the internet
	public boolean isNetworkAvailable() {
	    ConnectivityManager connectivityManager 
	          = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
	    NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
	    return activeNetworkInfo != null && activeNetworkInfo.isConnected();
	}
	
	/* Called when submit button is clicked */
	public void referPatient(View view){
		Intent intent = new Intent(this,PatientInfoActivity.class);
		Bundle extras = new Bundle();
		extras.putInt("EXTRA_PATIENT_ID", patient_id );
		intent.putExtras(extras);
			
		Rest rest = new Rest("POST",this);
		/* setup API URL */
		rest.setURL("http://121.97.45.242/segservice/encounter/referral/");
		
		rest.addRequestParams("encounter_nr", "100055"); //encounter number
		rest.addRequestParams("doctor_nr", "100055"); //doctor number
		rest.addRequestParams("referred_dept", "100055"); //reffered department
		rest.addRequestParams("reason_referral", "100055"); //reason number for referral
		

		System.out.println(rest.getURL());
		/* process request service request */
		rest.execute();
		
		/* check if connection was successful */
		while(rest.getContent() == null){}
		
		System.out.println("Data Received:\n" + rest.getContent()); 
			
		//System.out.println("processing request..");
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.referral, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	/**
	 * A placeholder fragment containing a simple view.
	 */
	/*
	public static class PlaceholderFragment extends Fragment {

		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_referral,
					container, false);
			return rootView;
		}
	}
	*/

}
