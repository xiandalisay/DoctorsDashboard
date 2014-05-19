package com.example.model;

public class Notes {
	private int notes_id;
	private int encounter_id;
	private String title;
	private String body;
	private String type;
	private String date_created;
	private boolean sync;
	
	public Notes() {
		notes_id = -1;
		encounter_id = -1;
		title = null;
		body = null;
		type = null;
		date_created = null;
		sync = false;
	}
	
	public Notes(int nid, int eid, String title, String body, String type, String date_created, boolean sync) {
		this.notes_id = nid;
		this.encounter_id = eid;
		this.title = title;
		this.body = body;
		this.type = type;
		this.date_created = date_created;
		this.sync = sync;
	}
	
	public int getNotes_id() {
		return notes_id;
	}

	public int getEncounter_id() {
		return encounter_id;
	}

	public String getTitle() {
		return title;
	}

	public String getBody() {
		return body;
	}

	public String getType() {
		return type;
	}

	public String getDate_created() {
		return date_created;
	}

	public boolean isSync() {
		return sync;
	}

	public void setNotes_id(int notes_id) {
		this.notes_id = notes_id;
	}

	public void setEncounter_id(int encounter_id) {
		this.encounter_id = encounter_id;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public void setType(String type) {
		this.type = type;
	}

	public void setDate_created(String date_created) {
		this.date_created = date_created;
	}

	public void setSync(boolean sync) {
		this.sync = sync;
	}
	
	public String toString() {
		return this.title + "\n" + this.type + "\n" + this.date_created.substring(0,10);
	}
}
