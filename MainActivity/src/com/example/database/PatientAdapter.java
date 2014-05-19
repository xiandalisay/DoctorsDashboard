package com.example.database;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.model.Age;
import com.example.model.Encounter;
import com.example.model.Patient;

public class PatientAdapter extends Data {
	
	private ContentValues values;
	
	private ArrayList<Encounter> encounters;
	
	/* _constructor */
	public PatientAdapter(Context context) 
	{
		try {
			dbHandler = new DatabaseHandler(context, DATABASE_NAME, null, DATABASE_VERSION);
			Log.d("Patient Adapter","Instantiated");
		} catch (Exception e) {
			Log.d("Patient Adapter Exception", Log.getStackTraceString(e));
		}
	}
	
	public void insertPatient(Patient patient){
		
		db = dbHandler.getWritableDatabase();
		values = new ContentValues();
		
		try {
			values.put(PID, patient.getPID());	
			values.put(NAME_FIRST, patient.getNameFirst());	
			values.put(NAME_MIDDLE, patient.getNameMiddle());	
			values.put(NAME_LAST, patient.getNameLast());	
			values.put(SEX, patient.getSex());
			values.put(BIRTH, patient.getBirthdate());
			values.put(STREET, patient.getStreet());
			values.put(CITY, patient.getCity());
			values.put(PROVINCE, patient.getProvince());
			values.put(ZIPCODE, patient.getZipCode());
			
			db.insertWithOnConflict(TABLE_PATIENT, null, values, SQLiteDatabase.CONFLICT_REPLACE);
		} catch(SQLException se) {
			Log.d("PatientAdapter insert patient", Log.getStackTraceString(se));
		} finally {
			db.close();
		}
	}
	
	/* gets information about specific patient based on patient_id */
	public Patient getPatientProfile(int patient_id){
		
		db 				= dbHandler.getWritableDatabase();
		String query 	= "SELECT pid, name_last, name_first, name_middle, sex, date_birth, street, city, province, zipcode  FROM patient WHERE pid = " + patient_id;
		Cursor cursor 	= db.rawQuery(query, null);
		//patients 		= new ArrayList<Patient>();
		Age patientage = new Age();
		Patient patient = new Patient();
		
		try{
			if(cursor.moveToFirst())
			{
				int pid 			= cursor.getInt(cursor.getColumnIndex("pid"));
				System.out.println("PID:"+pid);
				String lastname 	= cursor.getString(cursor.getColumnIndex("name_last"));
				String firstname 	= cursor.getString(cursor.getColumnIndex("name_first"));
				String middlename 	= cursor.getString(cursor.getColumnIndex("name_middle"));
				String sex 			= cursor.getString(cursor.getColumnIndex("sex"));
				String date 		= cursor.getString(cursor.getColumnIndex("date_birth"));
				int age 			= patientage.getAge(date);
				String street 		= cursor.getString(cursor.getColumnIndex("street"));
				String city 		= cursor.getString(cursor.getColumnIndex("city"));
				String province 	= cursor.getString(cursor.getColumnIndex("province"));
				String zipcode 		= cursor.getString(cursor.getColumnIndex("zipcode"));
				
				patient = new Patient(pid, lastname, firstname, middlename, sex, age, street, city, province, zipcode);
				
				System.out.println("found");
			}
			else{
				patient = new Patient(); 
				System.out.println("not found");
			}
		}
		catch(Exception e)
		{
			System.out.println(e);
		}
		
		return patient;
	}
	
	/* get all encounters based on patient_id */
	public ArrayList<Encounter> getPatientEncounter(int patient_id){
		db = dbHandler.getWritableDatabase();
		encounters = new ArrayList<Encounter>();
		try {
			String query = "SELECT encounter.encounter_id, encounter.date_encountered, encounter.type_patient, encounter.message_complaint, encounter.pid FROM encounter INNER JOIN patient ON encounter.pid = patient.pid WHERE patient.pid = " + patient_id + " ORDER BY date_encountered";
			
			Cursor cursor = db.rawQuery(query,  null);
			if(cursor.moveToFirst()){
				do{
					int eid = cursor.getInt(cursor.getColumnIndex("encounter_id"));
					String dateencountered = cursor.getString(cursor.getColumnIndex("date_encountered"));
					String typepatient = cursor.getString(cursor.getColumnIndex("type_patient"));
					String message = cursor.getString(cursor.getColumnIndex("message_complaint"));
					int pid = cursor.getInt(cursor.getColumnIndex("pid"));
					Encounter encounter = new Encounter(eid, typepatient, message, dateencountered, pid);
					encounters.add(encounter);
				}while(cursor.moveToNext());
			}
		} catch (Exception e) {
			Log.d("getPatientEncounter", e.getMessage());
		}
		finally {
			db.close();
		}
		Log.d("getPatientEncounter", "Done successfully.");
		return encounters;
	}
	
	
}
