/*
 * @Author: Christian Joseph Dalisay
 * @Date: 05/18/14
 * @Description - manages the CRUD operations
 * 				 of the table doctor_encounter
 */

package com.example.database;


import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;


import com.example.database.Data;
import com.example.model.Age;
import com.example.model.DoctorProfile;
import com.example.model.Encounter;
import com.example.model.Patient;
import com.example.model.Soap;

public class DoctorEncounterAdapter extends Data {
	
	
	private SQLiteDatabase db;
	private DatabaseHandler dbHandler;
	private Cursor cursor;
	private String query;
	
	private ArrayList<Encounter> encounter_list;
	
	private static final int 	DATABASE_VERSION	= 1;
	private static final String DATABASE_NAME 		= "localhost";
	
	public  DoctorEncounterAdapter(Context context) {
		try {
			dbHandler = new DatabaseHandler(context, DATABASE_NAME, null, DATABASE_VERSION);
			Log.d("DoctorEncounterAdapter", "Initialized");
		} catch (Exception e) {
			Log.d("DoctorEncounterAdapter Exception", Log.getStackTraceString(e));
		}
	}
	
	/* counts the doctors involved by an encounter */
	public Integer countDoctorsByEncounter(Integer encounter_id,Integer personnel) {
		db = dbHandler.getReadableDatabase();
		try {
			System.out.println("Personnel id: " + personnel);
			System.out.println("Encounter id: " + encounter_id);
			query = " SELECT count(encounter_id)  FROM " + TABLE_DOC_ENC + 
					" WHERE encounter_id = " + encounter_id + " AND personnel_id =" + personnel;
			System.out.println(query);
			cursor = db.rawQuery(query,  null);
			System.out.println(" rows: " + (cursor.getCount()-1));

			return (cursor.getCount()-1);
		} catch (Exception se) {
			Log.d("getEncounterIds", se.getMessage());
			return 0;
		}
		finally {
			db.close();
		}
	}
	
	public void deleteDoctorEncounter(ArrayList<Integer> encounter) {
		db = dbHandler.getWritableDatabase();
		
		try {
			for(int i = 0; i < encounter.size(); i++) {	
				db.delete(TABLE_DOC_ENC, "encounter_id = ?", new String[] {encounter.get(i)+""});
			}
		} catch (Exception se) {
			Log.d("deleteDoctorEncounter", Log.getStackTraceString(se));
		}
		finally {
			db.close();
		}
	}
}