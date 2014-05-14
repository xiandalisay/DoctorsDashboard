package com.example.database;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;

public class DepartmentAdapter extends Data {

	public  DepartmentAdapter(Context context) 
	{
		try {
			dbHandler = new DatabaseHandler(context, DATABASE_NAME, null, DATABASE_VERSION);
			Log.d("DatabaseHandler", "Database Created");
		} catch (Exception e) {
			Log.d("DatabaseHandler Exception", Log.getStackTraceString(e));
		}
	}
	
	public boolean checkDepartments(){
		//Add code for query getting number of accounts in mobile DB
		
		db = dbHandler.getReadableDatabase();
		
		String query = 
			"SELECT count(" + DEPT_ID + ")" +
			" FROM " + TABLE_DEPARTMENT;
		
		
		Cursor cursor = db.rawQuery(query, null);
		
		if(cursor.moveToFirst()){
			return true;
		}
		else{
			return false;
		}
	}
	
}
