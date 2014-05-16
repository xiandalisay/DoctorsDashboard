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

public class LabRequestAdapter extends Data {
	
	
	public  SQLiteDatabase db;
	private DatabaseHandler dbHandler;
	
	private static final int 	DATABASE_VERSION	= 1;
	private static final String DATABASE_NAME 		= "localhost";
	
	public  LabRequestAdapter(Context context) {
		try {
			dbHandler = new DatabaseHandler(context, DATABASE_NAME, null, DATABASE_VERSION);
			Log.d("LabRequestAdapter", "Initialized");
		} catch (Exception e) {
			Log.d("LabRequestAdapter Exception", Log.getStackTraceString(e));
		}
	}
	
	public  LabRequestAdapter open() throws SQLException {
		db = dbHandler.getWritableDatabase();
		return this;
	}
	
	public void close() {
		db.close();
	}
	
	public  SQLiteDatabase getDatabaseInstance() {
		return db;
	}
	
	
}