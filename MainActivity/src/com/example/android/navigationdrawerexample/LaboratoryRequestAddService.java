package com.example.android.navigationdrawerexample;

import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.example.model.Department;
import com.example.model.Rest;
import com.example.parser.DepartmentParser;
import com.example.parser.LabServiceParser;

public class LaboratoryRequestAddService extends Activity {
	ArrayList<Department> departments;
	String url_department = "http://121.97.45.242/segservice/department/show/";
	String url_service = "http://121.97.45.242/segservice/laboratory/show/";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_laboratory_request_add_service);
		// Show the Up button in the action bar.
		setupActionBar();
		//department spinner
		Spinner spinner_department = (Spinner) findViewById(R.id.requestingDepartmentSpinner);
		Rest rest = new Rest("GET",this, "");
		rest.setURL(url_department);
		rest.execute();
		while(rest.getContent() == null){}
		
		if(rest.getResult()){
			String content = rest.getContent();
			DepartmentParser department_parser = new DepartmentParser(content);
			departments = department_parser.getDepartments();
		}
		ArrayAdapter<Department> array_adapter = new ArrayAdapter<Department>(this, android.R.layout.simple_spinner_item, departments);
		spinner_department.setAdapter(array_adapter);
		
		
	
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
		getMenuInflater().inflate(R.menu.laboratory_request_add, menu);
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

}
