package com.wqdsoft.im.map;

import android.location.Location;


public interface MapInterface {

	public void onComplete(Location location, String address);
	
	public void onError();
	
}
