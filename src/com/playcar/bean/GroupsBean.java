package com.playcar.bean;

import java.util.ArrayList;

import com.wk.libs.beans.BaseListBean;

/**
 * 群组——父类
 * @author qunlee_mr
 *
 */
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
