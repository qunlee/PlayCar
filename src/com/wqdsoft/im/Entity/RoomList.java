package com.wqdsoft.im.Entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.wqdsoft.im.org.json.JSONArray;
import com.wqdsoft.im.org.json.JSONException;
import com.wqdsoft.im.org.json.JSONObject;

public class RoomList implements Serializable{

	private static final long serialVersionUID = 115645454645L;
	
	public List<Room> mRoomList;
	public IMJiaState mState;
	
	public RoomList(){}
	
	public RoomList(String reString){
		try {
			JSONObject json = new JSONObject(reString);
			if(!json.isNull("data")){
				String jsonData = json.getString("data");
				if(jsonData!=null && !jsonData.equals("")
						&& jsonData.startsWith("[")){
					JSONArray array = json.getJSONArray("data");
					if(array != null && array.length() != 0){
						mRoomList = new ArrayList<Room>();
						for (int i = 0; i < array.length(); i++) {
							mRoomList.add(new Room(array.getJSONObject(i)));
						}
					}
				}
				
			}
			
			if(!json.isNull("state")){
				mState = new IMJiaState(json.getJSONObject("state"));
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
}
