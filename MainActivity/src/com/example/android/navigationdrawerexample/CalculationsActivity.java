package com.example.android.navigationdrawerexample;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class CalculationsActivity extends BaseActivity {

	private String value1 ;
	private String value2 ;
	
	/* UI layout references */
	private EditText et_value1;
	private EditText et_value2;
	private TextView rating;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.activity_calculations);
		super.onCreate(savedInstanceState);

		
	}
	
	/* retrieves inputted text by user and assigns it to a variable */
	private void setInputText(){
//		et_value1 = (EditText) findViewById(R.id.value1);
//		et_value2 = (EditText) findViewById(R.id.value2);
	}
	
	/* retrieves inputted text by user and converts/saves it as String */
	private void convertInputText(){
		value1 = et_value1.getText().toString();
		value2 = et_value2.getText().toString();
	}
	
	/* called when the "Sign in" button is clicked */
	public void handleInputs(View view){
		
		setInputText();
		
		/* Convert data type from EditText -> Editable -> String */ 
		convertInputText();
		
		//* Validate inputs from user (i.e. empty field(s), wrong inputs) */
		if(validateInputs()){
			computeBMI();
		}
			else {
				Toast.makeText(getApplicationContext(), "Failed to Authenticate", Toast.LENGTH_SHORT).show();
			}
	}
	
	/* validatesInputs - Validates the input of the user for logging in */		 
	public boolean validateInputs(){
		boolean cancel = false; //for flagging; will be equal to true if there are errors
		View focusView = null; //refers to the EditText View that will be focused if there are errors
		
		/* must only contain numeric characters */
		String regex = "^[-+]?[0-9]*\\.?[0-9]+$"; 
		if (!value1.matches(regex)) {
			et_value1.setError(getString(R.string.error_invalid_format));
			focusView = et_value1;
			cancel = true;
		} 
		
		/* must only contain numeric characters */
		if (!value2.matches(regex)) {
			et_value2.setError(getString(R.string.error_invalid_format));
			focusView = et_value2;
			cancel = true;
		} 
		
		/* there must be a value in the field */
		if (value1.isEmpty()){
			et_value1.setError(getString(R.string.error_field_required));
			focusView = et_value1;
			cancel = true;
		}
		
		/* there must be a value in the field */
		if (value2.isEmpty()){
			et_value2.setError(getString(R.string.error_field_required));
			focusView = et_value2;		
			cancel = true;
		} 
		
		if(cancel){
			focusView.requestFocus();
			return false;
		}
		
		else {
			return true;
		}
	}

	public Float computeBMI() {
		return (Float) (Float.parseFloat(value1)/(Float.parseFloat(value2)*Float.parseFloat(value2)));
	}
	
	public void displayRating() {
		Float BMI = computeBMI();
		if(BMI >= 30) {
			rating.setText("Obesity");
		}
		if(BMI >= 25 ) {
			rating.setText("Overweight");
		}
		if(BMI >= 18.5) {
			rating.setText("Normal Weight");
		}
		else {
			rating.setText("Underweight");
		}
	}
		 

}
