/*
 * @Author: Christian Joseph Dalisay
 * @Date: 05/17/14
 * @Description: this adapter handles the CRUD operations 
 * 				 for the table patient
 */


package com.example.database;

import java.util.ArrayList;

import com.example.model.Doctor;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.database.Data;

public class PatientAdapter extends Data{
	
	
	private SQLiteDatabase db;
	private DatabaseHandler dbHandler;
	private Cursor cursor;
	
	private static final int 	DATABASE_VERSION	= 1;
	private static final String DATABASE_NAME 		= "localhost";
	//
	public  PatientAdapter(Context context) 
	{
		try {
			dbHandler = new DatabaseHandler(context, DATABASE_NAME, null, DATABASE_VERSION);
			Log.d("PatientAdapter", "Instantiated");
		} catch (Exception e) {
			Log.d("PatientAdapter Exception", Log.getStackTraceString(e));
		}
	}
	
	/* gets pids tagged by a specific doctor */
	public ArrayList<Integer> getPids(int personnel) {
		db = dbHandler.getReadableDatabase();
		String sql = "SELECT pid FROM " + TABLE_PATIENT + 
				" JOIN " + TABLE_ENCOUNTER + " USING (" + PID + ")" + 
				" JOIN " + TABLE_DOC_ENC + " USING (" + ENCOUNTER_ID + ")" + 
				" JOIN " + TABLE_DOCTOR + " USING (" + PERSONNEL_ID + ")" + 
				" WHERE doctor.personnel_id = '" + personnel + "'";
		
		cursor = db.rawQuery(sql, null);
		if(cursor.moveToFirst()) {
			ArrayList<Integer> pids = new ArrayList<Integer>();
			do {
				pids.add(cursor.getInt(cursor.getColumnIndex("pid")));
			} while(cursor.moveToNext());
			return pids;
		}
		Log.d("PatientAdapter getPids", "0 rows retrieved.");
		return null;
	}
	
}