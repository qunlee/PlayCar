package com.playcar.bean;

import android.content.Intent;

/**
 * 广告的bean对象
 * @author Administrator
 *
 */
public class Advertising {
	public String path;
	public int defaultImgResource;
	public Intent intent;
	
	
	public Intent getIntent() {
		return intent;
	}

	public void setIntent(Intent intent) {
		this.intent = intent;
	}

	public Advertising() {
		this("", 0);
	}

	public Advertising(String path, int defaultImgResource) {
		this.path = path;
		this.defaultImgResource = defaultImgResource;
	}
	public Advertising(String path, int defaultImgResource,Intent intent) {
		this.path = path;
		this.defaultImgResource = defaultImgResource;
		this.intent = intent;
	}

	
	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public int getDefaultImgResource() {
		return defaultImgResource;
	}

	public void setDefaultImgResource(int defaultImgResource) {
		this.defaultImgResource = defaultImgResource;
	}
}