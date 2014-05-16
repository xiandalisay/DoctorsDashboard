package com.example.android.navigationdrawerexample;

import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Toast;


public class MyOnClickListener implements OnClickListener {
	
	private final int INDEX_MEDICAL_HISTORY = 0;
	private final int INDEX_PREVIOUS_REQUESTS = 1;
	private final int INDEX_REFERRALS = 2;
	private final int INDEX_NOTES = 3;
	
	private static int lastExpandedGroupPosition;
	
	int childPos = -1;
	
	public MyOnClickListener(int childPosition) {  
		this.childPos = childPosition;
	}
	
	@Override
	public void onClick(View view) {
		
		switch (lastExpandedGroupPosition) {
			case INDEX_MEDICAL_HISTORY:
				/*
				int eid = ((Encounter)child.get(childPos)).getEncounterId();
				Bundle bundle = new Bundle();
				bundle.putInt("EXTRA_ENCOUNTER_ID", eid);
				Intent intent = new Intent(view.getContext(), PatientEncounterActivity.class);
				intent.putExtras(bundle);
				view.getContext().startActivity(intent);
				*/
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