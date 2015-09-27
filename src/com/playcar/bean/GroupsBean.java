package com.playcar.bean;

import java.util.ArrayList;

import com.wk.libs.beans.BaseListBean;

public class GroupsBean extends BaseListBean {
	
	public ArrayList<GroupBean> groupList = new ArrayList<GroupsBean.GroupBean>();
	
	public class GroupBean {
		public String id;
		public String address;
		public int count;
		public float distance;
		
		public ArrayList<GroupChildBean> childList = new ArrayList<GroupChildBean>();
	}
	
}
