package com.playcar.bean;

import java.util.ArrayList;

public class LocalsBean {
	public ArrayList<LocalBean> locals = new ArrayList<LocalsBean.LocalBean>();

	public class LocalBean {
		public String id;
		public String address;
		public String detail;
		public boolean selected;
	}
}
