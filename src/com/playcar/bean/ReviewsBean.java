package com.playcar.bean;

import java.util.ArrayList;

/**
 * 动态评论类
 * @author qunlee_mr
 *
 */
public class ReviewsBean {
public ArrayList<ReviewBean> reviews = new ArrayList<ReviewsBean.ReviewBean>();
	public class ReviewBean{
		public String id;
		public String img_url;
		public String user_id;
		public String user_name;
		public String user_content;
		public String time;
	}
}
