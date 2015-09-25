package com.wqdsoft.im.Entity;

import java.io.Serializable;

import com.wqdsoft.im.org.json.JSONException;
import com.wqdsoft.im.org.json.JSONObject;

public class LoginResult implements Serializable{

	private static final long serialVersionUID = 113454353454L;
	
	public Login mLogin;
	public IMJiaState mState;

	
	public LoginResult(){}
	
	public LoginResult(String reString){
		try {
			JSONObject json = new JSONObject(reString);
			
			if(!json.isNull("data")){
				mLogin = new Login(json.getJSONObject("data"));
			}
			
			if(!json.isNull("state")){
				mState = new IMJiaState(json.getJSONObject("state"));
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
}
