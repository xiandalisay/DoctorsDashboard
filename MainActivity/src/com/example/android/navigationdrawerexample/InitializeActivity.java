/*
 ** Created by Alvin Jay Cosare
 ** Created on 05/06/14
 ** Handles processes for the initialization of the application
 **
 ** Updated by Christian Joseph Dalisay
 ** Updated on 05/10/14
 */

package com.example.android.navigationdrawerexample;

import java.util.ArrayList;
import java.util.UUID;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;

import com.example.database.AccountsAdapter;
import com.example.database.ClientAdapter;
import com.example.database.DatabaseAdapter;
import com.example.database.DepartmentAdapter;
import com.example.model.Department;
import com.example.model.Preferences;
import com.example.model.Rest;
import com.example.parser.DepartmentParser;

public class InitializeActivity extends InitialActivity{
	
	private ArrayList<Department> departments;
	private String client_id;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		/*Initializes the database, and the shared preferences*/
		DatabaseAdapter db = new DatabaseAdapter(this);
		Preferences.createPreferences(this);
		
		/* When dummy datum client_id is used */
		if(!checkClientId()){
			client_id = generateClientId();
			Preferences.setInitializePreference(this,client_id);
			System.out.println("set " + Preferences.getInitializePreference(this));
			saveClientId(client_id);
		}
		else{
			Preferences.setInitializePreference(this,getClientId());
		}
		
		/* check if department table is empty */
		if(isDepartmentEmpty()){
			logMessage("Empty");
			
			try{
				retrieveDepartmentsAPI();
			}catch(Exception e){}
		}
		else{
			logMessage("Not Empty");
		}
		
		/*
		 *  Check if at least one account already exists 
		 *  If no existing accounts then proceed to Registration
		 *  Else proceed to LogIn
		 */
		if(!accountExists()){
			showRegisterActivity();
			finish(); //temporary
		} 
		else{
			showLoginActivity();
			finish();
		}
			
	}
	

	private void retrieveDepartmentsAPI() {
		DepartmentAdapter da = new DepartmentAdapter(this);
		
		if(isNetworkAvailable()){
			Rest rest = new Rest("GET");
			rest.setURL("http://121.97.45.242/segservice/department/show/");
			rest.execute();
			while(rest.getContent() == null){}
			 
			if(rest.getResult()){
				String content = rest.getContent();
				System.out.println(content);
				DepartmentParser department_parser = new DepartmentParser(content);
				departments = department_parser.getDepartments();
				da.insertDepartments(departments);
				da.getDepartments();
			}
		}
	}


	private boolean isDepartmentEmpty() {
		
		DepartmentAdapter db = new DepartmentAdapter(this);
		if(db.checkDepartments()){
			return true;
		}
		else{
			return false;			
		}
	}

	/* Checks if a client_id already exists */
	private boolean checkClientId(){
		
		ClientAdapter db = new ClientAdapter(this);
		
		if(db.clientIdExists()){
			return true;
		} 
		else{
			return false;
		}
	}
	
	/* Generates client_id */
	private String generateClientId(){ 
		
		System.out.println("uuid");
		return UUID.randomUUID().toString();
		
	}
	
	/* Saves generated client_id to mobile DB */
	private void saveClientId(String client_id){
		
		ClientAdapter db = new ClientAdapter(this);
		
		db.insertClientId(client_id);
		
	}
	
	private String getClientId() {
		ClientAdapter db = new ClientAdapter(this);
		return db.getClientId();
	}
	
	/* Checks if at least one account exists */
	private boolean accountExists(){
		AccountsAdapter db = new AccountsAdapter(this);
		
		if(db.getAccounts() > 0){
			return true;
		}
		else{
			return false;
		}

	}
	
	public void showRegisterActivity(){
		//System.out.println("register");
		Intent intent = new Intent(this, RegisterActivity.class);
		startActivity(intent);
	}
	
	public void showLoginActivity(){
		Intent intent = new Intent(this, LoginActivity.class);
		startActivity(intent);
	}

	@Override
	protected void onPause() {
		finish();
	}
	
	
	
}


