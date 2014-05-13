package com.example.android.navigationdrawerexample;

import com.example.model.Preferences;

import android.app.Activity;

public class InitialActivity extends Activity{
	
    
    /* checks if phone is connected to a network */
    @SuppressWarnings("static-access")
	protected void checkNetwork() {
		// TODO Auto-generated method stub
		Preferences pref =  new Preferences();
		System.out.println("network?");
		if(pref.isNetworkAvailable(this)){
			//Add code to change value of preference for checking connectivity (Online or Offline)
			pref.putSharedPreferencesBoolean(this, "network", true);
			
			System.out.println(pref.getSharedPreferencesBoolean(this, "network", false));
		}
		else{
			System.out.println("OFFline");
		}
			
	}
    
}
