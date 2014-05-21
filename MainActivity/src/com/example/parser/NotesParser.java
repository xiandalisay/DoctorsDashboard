/*
 * @Author: Christian Joseph Dalisay
 * @Date: 05/21/14
 * 
 */

package com.example.parser;

import java.util.ArrayList;

import com.example.model.Notes;
import com.google.resting.json.JSONArray;
import com.google.resting.json.JSONException;
import com.google.resting.json.JSONObject;

public class NotesParser extends JSONParser {
	
	private ArrayList<Notes> notes;
	//private HashMap<String, String> data = new HashMap<String, String>();
	//private JSONObject json_childNode;
	private JSONArray json_array;
		
	public NotesParser(String content) throws NullPointerException{
		
		notes = new ArrayList<Notes>();
		try {
			json_array = new JSONArray(content);
		} catch (JSONException e) {
			System.out.println("JSON error");
		}
		//department api returns an array
	}
	
	public ArrayList<Notes> getNotes(){
		
		try{
			notes = new ArrayList<Notes>();
		
			int lengthJsonArr = json_array.length();  
	   
	        for(int i=0; i < lengthJsonArr; i++) 
	        {
	                         /****** Get Object for each JSON node.***********/
	            JSONObject jsonChildNode = json_array.getJSONObject(i);
				
				
	                           
	                         /******* Fetch node values **********/
	            int    notes_id     = Integer.parseInt(jsonChildNode.optString("notes_nr"));
	            int    encounter_id = Integer.parseInt(jsonChildNode.optString("encounter_nr"));
	            int    personnel_id = Integer.parseInt(jsonChildNode.optString("personnel_nr"));
	            String title       	= jsonChildNode.optString("title").toString();
				String type			= jsonChildNode.optString("type").toString();
				String body       	= jsonChildNode.optString("body").toString();
				String created		= jsonChildNode.optString("date_created").toString();
				
				Notes note = new Notes();
				note.setNotesId(notes_id);
				note.setEncounterId(encounter_id);
				note.setPersonnelId(personnel_id);
				note.setTitle(title);
				note.setType(type);
				note.setBody(body);
				note.setDateCreated(created);
				
				notes.add(note);
	        }
	        
		}catch(Exception e){System.out.println(e.toString());}
        
        return notes;
	}
}
