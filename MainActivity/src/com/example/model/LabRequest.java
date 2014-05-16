/*
 ** Created by Jessie Emmanuel Adante
 ** Created on 4/24/14
<<<<<<< HEAD
 *
 *	Updated by: Christian Joseph Dalisay
 *	Updated on 05/16/14
 *
 ** This class retrieves the laboratory request number and encounter
=======
 ** This class retrieves the laboratory request 
>>>>>>> master
 */

package com.example.model;

public class LabRequest 
{
	private int reference_number;
	private int encounter_number;
<<<<<<< HEAD
	private String date_requested;
=======
	private String service_code;
	private String service_name;
	private int quantity;
>>>>>>> master
	
	public LabRequest()
	{
		reference_number = 0;
		encounter_number = 0;
<<<<<<< HEAD
		date_requested = "";
=======
		service_code ="";
		service_name = "";
		quantity = 0;
>>>>>>> master
	}
	
	public LabRequest(int referencenumber, int encounternumber, String servicecode, String servicename, int q)
	{
		reference_number = referencenumber;
		encounter_number = encounternumber;
		service_code =servicecode;
		service_name = servicename;
		quantity = q;
	}
	
	public void setRequestNumber(int referencenumber)
	{
		reference_number = referencenumber;
	}
	
	public void setEncounterNumber(int encounternumber)
	{
		encounter_number = encounternumber;
	}
	
<<<<<<< HEAD
	public void setDateRequested(String request) {
		this.date_requested = request;
	}
	
	public int getRequestNumber()
=======
	public void setServiceCode(String servicecode)
>>>>>>> master
	{
		service_code =servicecode;
	}
	
	public void setServiceName(String servicename)
	{
		service_name = servicename;
	}
	
	public void setQuantity(int q)
	{
		quantity = q;
	}
	
	public int getReferenceNumber()
	{
		return reference_number;
	}
	
	public int getEncounterNumber()
	{
		return encounter_number;
	}
	
<<<<<<< HEAD
	public String getDateRequested() {
		return this.date_requested;
	}
=======
	public String getServiceCode()
	{
		return service_code;
	}
	
	public String getServiceName()
	{
		return service_name;
	}
	
	public int getQuantity()
	{
		return quantity;
	}
	
>>>>>>> master

}
