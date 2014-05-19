/*
 * @Author: Christian Joseph Dalisay
 * @Date: 05/18/14
 * @Description: manages the CRUD operations
 * 				 of the table notes
 */

package com.example.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;


import com.example.database.Data;

public class NotesAdapter extends Data {
	
	
	public  SQLiteDatabase db;
	private DatabaseHandler dbHandler;
	
	private static final int 	DATABASE_VERSION	= 1;
	private static final String DATABASE_NAME 		= "localhost";
	
	public  NotesAdapter(Context context) {
		try {
			dbHandler = new DatabaseHandler(context, DATABASE_NAME, null, DATABASE_VERSION);
			Log.d("NotesAdapter", "Instantiated");
		} catch (Exception e) {
			Log.d("NotesAdapter Exception", Log.getStackTraceString(e));
		}
	}

	public void deleteNotes(int encounter_id, int personnel_id) {
		db = dbHandler.getWritableDatabase();
		
		try {
			db.delete(TABLE_NOTES, "encounter_id = ?, personnel_id", 
					new String[] {encounter_id +"",personnel_id+""});
		} catch (Exception se) {
			Log.d("NotesAdapter deleteNotes", Log.getStackTraceString(se));
		}
		finally {
			db.close();
			Log.d("NotesAdapter", "Done successfully.");
		}
	}

}