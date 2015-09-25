package com.wqdsoft.im.Entity;

import java.io.Serializable;


import com.wqdsoft.im.org.json.JSONException;
import com.wqdsoft.im.org.json.JSONObject;

public class VisitEntity implements Serializable{

	/**
	 * {"data":{"visit":"3","count":93,"interactive":0},"state":{"code":0,"msg":"","debugMsg":""}}
	 */
	private static final long serialVersionUID = 1L;
	
	public int visit;
	public int count;
	public int interactive=0;
	public IMJiaState state;
	public VisitEntity() {
		super();
	}
	public VisitEntity(String reqString) {
		super();
		if(reqString == null || reqString.equals("")){
			return;
		}
		try {
			JSONObject json = new JSONObject(reqString);
			if(!json.isNull("data")){
				JSONObject childObj = json.getJSONObject("data");
				this.visit = childObj.getInt("visit");
				this.count = childObj.getInt("count");
				this.interactive = childObj.getInt("interactive");
			}
			if(!json.isNull("state")){
				state = new IMJiaState(json.getJSONObject("state"));
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		
	}
	
}
