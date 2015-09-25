package com.wqdsoft.im.Entity;

import java.io.Serializable;

import com.wqdsoft.im.org.json.JSONException;
import com.wqdsoft.im.org.json.JSONObject;

public class AddGroup implements Serializable{

	private static final long serialVersionUID = -14641564545645L;
	public Group mGroup;
	public IMJiaState mState;
	
	public AddGroup(){}
	
	public AddGroup(String reString){
		try {
			JSONObject json = new JSONObject(reString);
			if(!json.isNull("state")){
				mState = new IMJiaState(json.getJSONObject("state"));
			}
			
			if(!json.isNull("data")){
				mGroup = new Group(json.getJSONObject("data"));
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

}
