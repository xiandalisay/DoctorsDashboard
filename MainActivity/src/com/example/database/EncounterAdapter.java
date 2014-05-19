/*
 * @author: Alvin Jay Cosare
 * @date created: 05/13/14
 * @description: 
 * 			Class that handles all database 
 * 			processes related to encounterss
 * 
 */

package com.example.database;

import java.util.ArrayList;

import com.example.model.Encounter;
import com.example.model.Preferences;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class EncounterAdapter extends Data {
	
	ContentValues values;
	
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
	
	/* @Author: Christian Joseph Dalisay
	 * 
	 */
	public void deleteDoctorEncounter(Integer encounter) {
		db = dbHandler.getWritableDatabase();
		try {
			db.delete(TABLE_DOC_ENC, " encounter_id = ?", new String[] {encounter + ""});
		} catch(SQLException se) {
			Log.d("EncounterAdapter deleteDocEnc", Log.getStackTraceString(se));
		} finally {
			db.close();
		}
		
	}
	
	/* @Author: Christian Joseph Dalisay
	 * 
	 */
	public void insertDoctorEncounter(Integer encounter,Integer personnel) {
		db = dbHandler.getWritableDatabase();
		values = new ContentValues();
		try {
			values.put(ENCOUNTER_ID, encounter);	
			values.put(PERSONNEL_ID, personnel);	
			
			db.insertWithOnConflict(TABLE_DOC_ENC, null, values, SQLiteDatabase.CONFLICT_REPLACE);
		} catch(SQLException se) {
			Log.d("EncounterAdapter insertDocEnc", Log.getStackTraceString(se));
		} finally {
			db.close();
		}
		
	}
	
	/* @Author: Christian Joseph Dalisay
	 * 
	 */
	public void insertEncounters(ArrayList<Encounter> enc) {
		db = dbHandler.getWritableDatabase();
		values = new ContentValues();
		try {
			db.beginTransaction();
			for(int i = 0; i < enc.size(); i++) {
				values.put(ENCOUNTER_ID, enc.get(i).getEncounterId());	
				values.put(PID, enc.get(i).getPID());	
				values.put(ENCOUNTERED, enc.get(i).getDateEncountered());	
				values.put(PATIENT, enc.get(i).getTypePatient());	
				values.put(COMPLAINT , enc.get(i).getMessageComplaint());
				db.insertWithOnConflict(TABLE_ENCOUNTER, null, values, SQLiteDatabase.CONFLICT_REPLACE);
			  }
			  db.setTransactionSuccessful();
				Log.d("DepartmentAdapter insertEncounters", "setTransactionSuccessful");
			}
			catch (SQLException se) {
				Log.d("DepartmentAdapter insertEncounters", Log.getStackTraceString(se));
			}
			finally	{
			  db.endTransaction();
			}
	}

	/* @Author: Christian Joseph Dalisay
	 * 
	 */
	public void deleteEncounter(int encounter) {
		db = dbHandler.getWritableDatabase();
		try {
			db.delete(TABLE_ENCOUNTER, "encounter_id = ?", new String[] {"'" + encounter + "'"});
		}
		catch (SQLException se) {
			Log.d("DepartmentAdapter deleteEncounter", Log.getStackTraceString(se));
		}
		finally {
		  db.close();
		}
	}
}
