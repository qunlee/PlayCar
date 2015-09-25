package com.wqdsoft.im.Entity;

import java.io.Serializable;

import com.wqdsoft.im.org.json.JSONException;
import com.wqdsoft.im.org.json.JSONObject;

public class Options implements Serializable{

	/**
	 *  {"data":[{"id":"48","sid":"546","text":"vb","count":"0","scale":0},
	 * {"id":"49","sid":"546","text":"vgh","count":"1","scale":1},
	 * {"id":"50","sid":"546","text":"vgh","count":"0","scale":0}],
	 * "state":{"code":0,"msg":"\u6295\u7968\u6210\u529f","debugMsg":""}}
	 */
	private static final long serialVersionUID = 1L;
	public int id;
	public int sid;
	public String text;
	public int count;
	public int scale;
	
	public String title;
	
	public Options(int id, String title) {
		super();
		this.id = id;
		this.title = title;
	}
	public Options() {
		super();
	}
	
	public Options(JSONObject jsonObj) {
		super();
		try {
			if(jsonObj == null || jsonObj.equals("")){
				return ;
			}
			this.id = jsonObj.getInt("id");
			if(!jsonObj.isNull("option")){
				this.title = jsonObj.getString("option");
			}
			if(!jsonObj.isNull("sid")){
				this.sid = jsonObj.getInt("sid");
			}
			if(!jsonObj.isNull("text")){
				this.text = jsonObj.getString("text");
			}
			if(!jsonObj.isNull("count")){
				this.count = jsonObj.getInt("count");
			}
			if(!jsonObj.isNull("scale")){
				double st = jsonObj.getDouble("scale")*100;
				
				this.scale =(int) st;
			}
			
		} catch (JSONException e) {
			e.printStackTrace();
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	

}
