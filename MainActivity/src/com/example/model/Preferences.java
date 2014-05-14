package com.example.model;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class Preferences extends HelperSharedPreferences{
	
	private  ConnectivityManager connectivityManager;
	
	public boolean isNetworkAvailable(Context context) {
	    ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
	    NetworkInfo networkInfo = cm.getActiveNetworkInfo();
	    
	    // if no network is available networkInfo will be null
	    // otherwise check if we are connected
	    
	    if (networkInfo != null && networkInfo.isConnected()) {
	        return true;
	    }
	    
	    return false;
	} 
	
	/* retrieve base_url for a specific doctor from preferences */
	public static String getBaseURL(Context context){
		try{
			return getSharedPreferencesString(context, "key_base_url", "");			
		} catch(Exception e){
			System.out.println("getBaseURL Error");
			return null;
		}
	}
	
	/* retrieve personnel_nr for a specific doctor from preferences */
	public static int getPersonnelNumber(Context context){
		try{
			return getSharedPreferencesInt(context, "key_personnel_nr", 0);			
		} catch(Exception e){
			System.out.println("getPersonnelNumber Error");
			return 0;
		}
	}

}
