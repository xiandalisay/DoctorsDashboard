/*
 ** Created by Jessie Emmanuel Adante
 ** Created on 5/13/14
 ** This class retrieves the soap message
 */
package com.example.model;

public class Soap {
	private int soap_id;
	private int encounter_id;
	private String msg_soap;
	private String date_modified;
	private boolean sync_soap;
	
	public Soap()
	{
		soap_id = 0;
		encounter_id = 0;
		msg_soap = "";
		date_modified = "";
		sync_soap = false;
	}
	
	public Soap (int sid, int eid, String msgsoap, String datemodified, boolean syncsoap)
	{
		soap_id = sid;
		encounter_id = eid;
		msg_soap = msgsoap;
		date_modified = datemodified;
		sync_soap = syncsoap;
	}
	
	public void setSoapId (int sid)
	{
		soap_id = sid;
	}
	
	public void setEncounterId (int eid)
	{
		encounter_id = eid;
	}
	
	public void setMessageSoap (String msgsoap)
	{
		msg_soap = msgsoap;
	}
	
	public void setDateModified (String datemodified)
	{
		date_modified = datemodified;
	}
	
	public void setSyncSoap (boolean syncsoap)
	{
		sync_soap = syncsoap;
	}
	
	public int getSoapId()
	{
		return soap_id;
	}
	
	public int setEncounterId()
	{
		return encounter_id;
	}
	
	public String setMessageSoap()
	{
		return msg_soap;
	}
	
	public String setDateModified()
	{
		return date_modified;
	}
	
	public boolean setSyncSoap()
	{
		return sync_soap;
	}
	
	public String toString()
	{
		return date_modified.substring(0,10) + ":\n" + msg_soap.substring(0,30)+"...";
	}
}
