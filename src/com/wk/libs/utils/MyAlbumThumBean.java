package com.wk.libs.utils;

import net.tsz.afinal.annotation.sqlite.Id;

/**
 * 相册缩略图对应路径
 * @author Administrator
 *
 */
public class MyAlbumThumBean {
	@Id(column="sourthPath")
	
	private String sourthPath;
	private String photoName;
	private String thumPath;
	
	public String getSourthPath() {
		return sourthPath;
	}
	public void setSourthPath(String sourthPath) {
		this.sourthPath = sourthPath;
	}
	public String getPhotoName() {
		return photoName;
	}
	public void setPhotoName(String photoName) {
		this.photoName = photoName;
	}
	public String getThumPath() {
		return thumPath;
	}
	public void setThumPath(String thumPath) {
		this.thumPath = thumPath;
	}
	
}
