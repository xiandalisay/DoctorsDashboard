package com.example.android.navigationdrawerexample;

import java.util.ArrayList;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Toast;

import com.example.model.Encounter;


public class MyOnClickListener implements OnClickListener {
	
	private final int INDEX_MEDICAL_HISTORY = 0;
	private final int INDEX_PREVIOUS_REQUESTS = 1;
	private final int INDEX_REFERRALS = 2;
	private final int INDEX_NOTES = 3;
	
	private static int lastExpandedGroupPosition;
	
	int childPos = -1;
	ArrayList<Object> child;
	
	public MyOnClickListener(int childPosition, ArrayList<Object> child) { 
		this.child = child;
		this.childPos = childPosition;
	}
	
	@Override
	public void onClick(View view) {
		
		switch (lastExpandedGroupPosition) {
			case INDEX_MEDICAL_HISTORY:
				
				int pid = ((Encounter)child.get(childPos)).getPid();
				Bundle bundle = new Bundle();
				bundle.putInt("EXTRA_PATIENT_ID", pid);
				Intent intent = new Intent(view.getContext(), PatientInfoActivity.class);
				intent.putExtras(bundle);
				view.getContext().startActivity(intent);
				
				Toast.makeText(view.getContext(),"groupPos:"+lastExpandedGroupPosition+"\n"+"childPos:"+childPos, Toast.LENGTH_SHORT).show();
				break;
			case INDEX_PREVIOUS_REQUESTS:
				Toast.makeText(view.getContext(),"groupPos:"+lastExpandedGroupPosition+"\n"+"childPos:"+childPos, Toast.LENGTH_SHORT).show();
				break;
			case INDEX_REFERRALS:
				Toast.makeText(view.getContext(),"groupPos:"+lastExpandedGroupPosition+"\n"+"childPos:"+childPos, Toast.LENGTH_SHORT).show();
				break;
			case INDEX_NOTES:
				Toast.makeText(view.getContext(),"groupPos:"+lastExpandedGroupPosition+"\n"+"childPos:"+childPos, Toast.LENGTH_SHORT).show();
				break;
			default:
				Log.e("onClick groupPosition", "Error on groupPosition: Should not reach default.");
				Log.e("onClick groupPosition", "groupPos:"+lastExpandedGroupPosition+"\n"+"childPos:"+childPos);
				break;
		}
	}
	
	public static void setLastExpandedGroupPosition(int pos) {
		lastExpandedGroupPosition = pos;
	}
}