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
	
	/* @Author: Christian Joseph Dalisay */
	public static String getInitializePreference(Context context) {
		return getSharedPreferencesString(context, "key_client", "client");
	}
	
	public static void setInitializePreference(Context context, String pref) {
		putSharedPreferencesString(context, "key_client", pref);
	}
	
	public static String getAuthenticationPreference(Context context) {
		return getSharedPreferencesString(context, "key_authentication", "authtoken");
	}
	
	public static void setAuthenticationPreference(Context context, String pref) {
		putSharedPreferencesString(context, "key_authentication", pref);
	}	
	
	public static Boolean getRememberPreference(Context context) {
		return getSharedPreferencesBoolean(context, "key_remember", false);
	}
	
	public static void setRememberPreference(Context context, Boolean pref) {
		putSharedPreferencesBoolean(context, "key_remember", pref);
	}
	
	public static void setBaseUrlPreference(Context context, String pref) {
		putSharedPreferencesString(context, "key_base_url", pref);
	}
	
	public static Integer getPersonnelPreference(Context context) {
		return getSharedPreferencesInt(context, "key_personnel_nr", 0);
	}
	
	public static void setPersonnelPreference(Context context, Integer pref) {
		putSharedPreferencesInt(context, "key_personnel_nr", pref);
	}
}
