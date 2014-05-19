/*
 ** Created by Jessie Emmanuel Adante
 ** Created on 4/24/14
 ** This class retrieves the laboratory request 
 */

package com.example.model;

public class LabRequest 
{
	private int reference_number;
	private int encounter_number;
	private String service_code;
	private String service_name;
	private int quantity;
	
	
	public LabRequest()
	{
		reference_number = 0;
		encounter_number = 0;
		service_code ="";
		service_name = "";
		quantity = 0;
		
		
	}
	
	public LabRequest(int referencenumber, int encounternumber, String servicecode, String servicename, int q, String transactiontype, String requestoption, String discount)
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
	
	public void setServiceCode(String servicecode)
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
	

}
