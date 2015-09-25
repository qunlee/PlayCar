package com.wk.libs.beans;

import android.os.Parcelable;

/**
 * 所有列表bean的公共属性。
 * 
 * XXXXListBean来继承
 * 
 */
public abstract class BaseListBean{
	public int totalcount;

	public int getTotalcount() {
		return totalcount;
	}

	public void setTotalcount(int totalcount) {
		this.totalcount = totalcount;
	}

}
