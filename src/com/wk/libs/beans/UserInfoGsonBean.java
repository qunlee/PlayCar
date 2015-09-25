package com.wk.libs.beans;


public class UserInfoGsonBean {

	// user_id: "user1",
	// name: "小强",
	// avatar: "pic1",
	// yanglaojin: 1,
	// chengxinbi: 1000,
	// notice_count: 9,
	// card_count: 2,
	// store_count: 1
	// token

	/**
	 * DB的主键，必须又
	 */
	public int id = 1;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String user_id;
	public String name;
	public String phone;
	public String avatar;
	public double yanglaojin;
	public double chengxinbi;
	public int notice_count;
	public int card_count;
	public int store_count;
	public String token;
	
	public LogInfoGsonBean loginfo;

	public LogInfoGsonBean getLoginfo() {
		return loginfo;
	}

	public void setLoginfo(LogInfoGsonBean loginfo) {
		this.loginfo = loginfo;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public int getNotice_count() {
		return notice_count;
	}

	public void setNotice_count(int notice_count) {
		this.notice_count = notice_count;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getUser_id() {
		return user_id;
	}

	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	public double getYanglaojin() {
		return yanglaojin;
	}

	public void setYanglaojin(double yanglaojin) {
		this.yanglaojin = yanglaojin;
	}

	public double getChengxinbi() {
		return chengxinbi;
	}

	public void setChengxinbi(double chengxinbi) {
		this.chengxinbi = chengxinbi;
	}

	public int getCard_count() {
		return card_count;
	}

	public void setCard_count(int card_count) {
		this.card_count = card_count;
	}

	public int getStore_count() {
		return store_count;
	}

	public void setStore_count(int store_count) {
		this.store_count = store_count;
	}

	@Override
	public String toString() {
		return "UserInfoGsonBean [id=" + id + ", user_id=" + user_id
				+ ", name=" + name + ", phone=" + phone + ", avatar=" + avatar
				+ ", yanglaojin=" + yanglaojin + ", chengxinbi=" + chengxinbi
				+ ", notice_count=" + notice_count + ", card_count="
				+ card_count + ", store_count=" + store_count + ", token="
				+ token + "]";
	}
	
}
