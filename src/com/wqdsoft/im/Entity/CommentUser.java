package com.wqdsoft.im.Entity;

import java.io.Serializable;

import com.wqdsoft.im.org.json.JSONException;
import com.wqdsoft.im.org.json.JSONObject;

public class CommentUser implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public int id;
	public String uid;
	public String nickname;
	public String fuid;
	public String fnickname;
	public String content;
	public String headsmall;
	public long createtime;
	
	
	
	
	public CommentUser(int id, String uid, String nickname, String fuid,
			String fnickname, String content, long createtime) {
		super();
		this.id = id;
		this.uid = uid;
		this.nickname = nickname;
		this.fuid = fuid;
		this.fnickname = fnickname;
		this.content = content;
		this.createtime = createtime;
	}

	public CommentUser() {
		super();
	}
	
	public CommentUser(JSONObject json) {
		initCompent(json);
	}
	
	private void initCompent(JSONObject json){
		try {
			this.id = json.getInt("id");
			if(!json.isNull("uid")){
				this.uid = json.getString("uid");
			}
			
			if(!json.isNull("nickname")){
				this.nickname = json.getString("nickname");
			}
			if(!json.isNull("fid")){
				this.fuid = json.getString("fid");
			}
			if(!json.isNull("fnickname")){
				this.fnickname = json.getString("fnickname");
			}
			if(!json.isNull("content")){
				this.content = json.getString("content");
			}
			if(!json.isNull("createtime")){
				this.createtime = json.getLong("createtime");
			}
			if(!json.isNull("headsmall")){
				this.headsmall = json.getString("headsmall");
			}
			
		} catch (JSONException e) {
			e.printStackTrace();
		}
				
	}
	
	
	
}
