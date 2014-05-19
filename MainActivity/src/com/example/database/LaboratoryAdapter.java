/*
 * @author: Alvin Jay Cosare
 * @date created: 05/19/14
 * @description: 
 * 			Class that handles all database 
 * 			processes related to laboratory 
 * 			requests and services
 * 
 */

package com.example.database;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.model.LabRequest;
import com.example.model.LabService;

public class LaboratoryAdapter extends Data {
	
	ContentValues values;
	
	/* _constructor */
	public  LaboratoryAdapter(Context context) 
	{
		try {
			dbHandler = new DatabaseHandler(context, DATABASE_NAME, null, DATABASE_VERSION);
			Log.d("Encounter Adapter","Instantiated");
		} catch (Exception e) {
			Log.d("Encounter Adapter Exception", Log.getStackTraceString(e));
		}
	}
	
	
	/* inserts lab requests in mobile DB */
	public void insertLabRequestsEncounter(ArrayList<LabRequest> requests) {
		
		db = dbHandler.getWritableDatabase();
		values = new ContentValues();
		
		try {
			for(int i = 0; i < requests.size(); i++) {
				values.put(ENCOUNTER_ID, requests.get(i).getEncounterNumber());	
				values.put(REQUEST_ID, requests.get(i).getRequestNumber());				
				
				db.insertWithOnConflict(TABLE_LAB_REQUEST, null, values, SQLiteDatabase.CONFLICT_REPLACE);
				
				insertLabServiceRequest(requests.get(i));
			}
		} catch(SQLException se) {
			Log.d("LABORATORY_ADAPTER", Log.getStackTraceString(se));
		} finally {
			db.close();
		}	
		
	}

	/* insert into composite table for requests and associated services inluded*/
	private void insertLabServiceRequest(LabRequest request) {
		
		db = dbHandler.getWritableDatabase();
		values = new ContentValues();
		
		ArrayList<LabService> services = request.getServices();
		
		try {
			
			for(int i=0; i < services.size(); i++) {
				
				values.put(REQUEST_ID, request.getRequestNumber());				
				values.put(SERVICE_ID, services.get(i).getServiceCode());
				values.put(QUANTITY, services.get(i).getQuantity());
				
				db.insertWithOnConflict(TABLE_SERVICE_REQUEST, null, values, SQLiteDatabase.CONFLICT_REPLACE);
			}
			
		} catch(SQLException se) {
			
			Log.d("LABORATORY_ADAPTER", Log.getStackTraceString(se));
	
		} finally {
			
			db.close();
		
		}	
	}
}
