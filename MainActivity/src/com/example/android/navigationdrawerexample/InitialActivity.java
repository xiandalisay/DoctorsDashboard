package com.example.android.navigationdrawerexample;

import com.example.model.Preferences;

import android.app.Activity;

public class InitialActivity extends Activity{
	
    
    /* checks if phone is connected to a network */
    protected void checkNetwork() {
		// TODO Auto-generated method stub
		Preferences pref =  new Preferences();
		
		if(pref.isNetworkAvailable(this)){
			//Add code to change value of preference for checking connectivity (Online or Offline)
			System.out.println("ONline");
		}
		else{
			System.out.println("OFFline");
		}
			
	}
    
}
