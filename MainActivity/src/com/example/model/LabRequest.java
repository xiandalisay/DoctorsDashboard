/*
 ** Created by Jessie Emmanuel Adante
 ** Created on 4/24/14
 *
 *	Updated by: Christian Joseph Dalisay
 *	Updated on 05/16/14
 *
 ** This class retrieves the laboratory request number and encounter
 */

package com.example.model;

public class LabRequest 
{
	private int request_number;
	private int encounter_number;
	private String date_requested;
	
	public LabRequest()
	{
		request_number = 0;
		encounter_number = 0;
		date_requested = "";
	}
	
	public LabRequest(int requestnumber, int encounternumber)
	{
		request_number = requestnumber;
		encounter_number = encounternumber;
	}
	
	public void setRequestNumber(int requestnumber)
	{
		request_number = requestnumber;
	}
	
	public void setEncounterNumber(int encounternumber)
	{
		encounter_number = encounternumber;
	}
	
	public void setDateRequested(String request) {
		this.date_requested = request;
	}
	
	public int getRequestNumber()
	{
		return request_number;
	}
	
	public int getEncounterNumber()
	{
		return encounter_number;
	}
	
	public String getDateRequested() {
		return this.date_requested;
	}

}
