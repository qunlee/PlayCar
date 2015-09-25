package com.wk.libs.beans;

public class LogInfoGsonBean {

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
	
	public String UserId;
	public String Token;
	public long LoginTime;
	public String RequestTime;
//	public boolean IsUsed;

	public String getUserId() {
		return UserId;
	}

	public void setUserId(String userId) {
		UserId = userId;
	}

	public String getToken() {
		return Token;
	}

	public void setToken(String token) {
		Token = token;
	}

	public long getLoginTime() {
		return LoginTime;
	}

	public void setLoginTime(long loginTime) {
		LoginTime = loginTime;
	}

	public String getRequestTime() {
		return RequestTime;
	}

	public void setRequestTime(String requestTime) {
		RequestTime = requestTime;
	}

//	public boolean isIsUsed() {
//		return IsUsed;
//	}
//
//	public void setIsUsed(boolean isUsed) {
//		IsUsed = isUsed;
//	}

}
