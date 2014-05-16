package com.example.android.navigationdrawerexample;

import com.example.model.HelperSharedPreferences;
import com.example.model.Preferences;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.widget.Toast;

public class InitialActivity extends Activity{
	
	protected Intent intent;
    protected Bundle extras;
    
    @SuppressWarnings("static-access")
    
    /* checks if phone is connected to a network */
	protected  void checkNetwork() {
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
    
    protected boolean isNetworkAvailable() {
	    ConnectivityManager connectivityManager 
	          = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
	    NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
	    return activeNetworkInfo != null && activeNetworkInfo.isConnected();
	}
    
    /* displays message dialog */
    protected void alertMessage(String message){
    	Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }
    
    /* displays message dialog */
    protected void logMessage(String message){
    	System.out.println(message);
    }
    
    
}
