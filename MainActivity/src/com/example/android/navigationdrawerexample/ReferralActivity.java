package com.example.android.navigationdrawerexample;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.database.DepartmentAdapter;
import com.example.model.Department;
import com.example.model.Rest;

public class ReferralActivity extends Activity {

	private int patient_id;
	ArrayList<Department> departments;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_referral);

		/*
		if (savedInstanceState == null) {
			getFragmentManager().beginTransaction()
					.add(R.id.container, new PlaceholderFragment()).commit();
		}
		*/
		
		TextView textView = (TextView) findViewById(R.id.nameLabel);
		Intent intent = getIntent();
		Bundle extras = intent.getExtras();
		patient_id = extras.getInt("EXTRA_PATIENT_ID");
		String name_last = extras.getString("EXTRA_PATIENT_NAME_LAST");
		String name_first= extras.getString("EXTRA_PATIENT_NAME_FIRST");
		textView.setText(name_last + ", " + name_first);
		
		Spinner spinner = (Spinner) findViewById(R.id.referDepartment);
		DepartmentAdapter departmentAdapter = new DepartmentAdapter(this);
		
		
		departments = new ArrayList<Department>();
		departments = departmentAdapter.getDepartments();
		
		System.out.println(departments.get(0).getDepartmentName());
		ArrayAdapter<Department> array_adapter = new ArrayAdapter<Department>(this, android.R.layout.simple_spinner_item, departments);
		spinner.setAdapter(array_adapter);

		/*
		ArrayAdapter<Department> arrayAdapter = new ArrayAdapter<Department>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, android.R.id.text2, departments){
        	//method to override the getView method of ArrayAdapter, this changes the color of the text view
        	@Override
        	public View getDropDownView(int position, View convertView, ViewGroup parent) {
        		
        		TextView label = new TextView(getApplicationContext());
        		label.setTextColor(Color.BLACK);
        		label.setText(departments.get(position).getDepartmentName());
        		
        		return label;
        	  }
        	};

        spinner.setAdapter(arrayAdapter);
        */
        	
	}
	
	/* Called when submit button is clicked */
	public void referPatient(View view){
		Intent intent = new Intent(this,PatientInfoActivity.class);
		Bundle extras = new Bundle();
		extras.putInt("EXTRA_PATIENT_ID", patient_id );
		intent.putExtras(extras);
			
		Rest rest = new Rest("POST");
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
