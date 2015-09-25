package com.wqdsoft.im.Entity;

import java.io.Serializable;

import com.wqdsoft.im.org.json.JSONException;
import com.wqdsoft.im.org.json.JSONObject;

public class MessageResult implements Serializable{

	private static final long serialVersionUID = -1436465487871L;
	
	public MessageInfo mMessageInfo;
	public IMJiaState mState;
	
	public MessageResult(String reString){
		try {
			JSONObject json = new JSONObject(reString);
			if(!json.isNull("state")){
				mState = new IMJiaState(json.getJSONObject("state"));
			}
			
			if(!json.isNull("data")){
				mMessageInfo = new MessageInfo(json.getJSONObject("data"));
			}
			
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

}
