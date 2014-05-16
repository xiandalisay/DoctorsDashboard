/*
 * @author: Alvin Jay Cosare
 * @date created: 05/13/14
 * @description: 
 * 			Class that handles all database 
 * 			processes related to encounterss
 * 
 */

package com.example.database;

import android.content.Context;
import android.util.Log;

public class EncounterAdapter extends Data {
	
	/* _constructor */
	public  EncounterAdapter(Context context) 
	{
		try {
			dbHandler = new DatabaseHandler(context, DATABASE_NAME, null, DATABASE_VERSION);
			Log.d("Encounter Adapter","Instantiated");
		} catch (Exception e) {
			Log.d("Encounter Adapter Exception", Log.getStackTraceString(e));
		}
	}
	
	/* retrieves the encounter_id of the latest encounter of a patient */
	@SuppressWarnings("null")
	public int getLatestEncounter(int patient_id){
	
		db = dbHandler.getReadableDatabase();
		
		String query = 
				"SELECT encounter_id " + 
			    " FROM " + TABLE_ENCOUNTER + 
				" WHERE pid = " + patient_id +
				" ORDER BY date_encountered DESC" +
				" LIMIT 1"; 
		
		cursor = db.rawQuery(query, null);
		
		try{
			cursor.moveToFirst();
			return cursor.getInt(cursor.getColumnIndex("encounter_id"));
		} catch(Exception e) {
			Log.d("Encounter Adapter","0 rows retrieved");
			return -1;
		}
	}
	
	public boolean isEncounterExists(int encounter_id){

		db = dbHandler.getReadableDatabase();
		
		String query = 
				"SELECT encounter_id " + 
			    " FROM " + TABLE_ENCOUNTER + 
				" WHERE  encounter_id = " + encounter_id;
		
		cursor = db.rawQuery(query, null);
		
		try{
			System.out.println("Encounter ID: " + encounter_id);
			return cursor.moveToFirst();
		} catch(Exception e) {
			Log.d("Encounter Adapter","0 rows retrieved");
			return false;
		}
	}

}
