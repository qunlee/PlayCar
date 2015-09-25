package com.playcar.contants;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.ArrayList;

/**
 * Created by chengrong on 2015/6/2.
 */
public class MySession {

    // Preference实例
    private SharedPreferences prefs;


    String hotelId;
    //用户名
    String userName;
    //密码
    String hotelName;
    //token
    String token;
    // 单例
    private static MySession instance;

    /**
     * 获取Session实例
     *
     * @return
     */
    public static synchronized MySession getInstance() {
        if (instance == null) {
            instance = new MySession();

        }
        return instance;
    }

    //登录本地保存数据
    public void login(String hotelId, String userName, String hotelName, String token) {
        this.hotelId = hotelId;
        this.userName = userName;
        this.hotelName = hotelName;
        this.token = token;
        saveToPreference();

    }

    /**
     * 注销登录状态
     */
    public void logout() {
        this.hotelId = null;
        this.userName = null;
        this.hotelName = null;
        this.token = null;

        saveToPreference();
    }

    //将登录的数据保存到本地share
    private synchronized void saveToPreference() {
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(Constants.PREF_PREFIX + "hotelId", hotelId);
        editor.putString(Constants.PREF_PREFIX + "userName", userName);
        editor.putString(Constants.PREF_PREFIX + "hotelName", hotelName);
        editor.putString(Constants.PREF_PREFIX + "token", token);
        editor.commit();
    }

    /**
     * 是否已登录根据本地Token是不是空
     *
     * @return
     */
    public boolean isLogin() {
        return (getToken() == null || "".equals(getToken())) ? false : true;
    }

    /**
     * 初始化prefs
     *
     * @param prefs
     */
    public synchronized void initialize(SharedPreferences prefs) {
        this.prefs = prefs;
        // 读取用户名/id/SessionID
        userName = prefs.getString(Constants.PREF_PREFIX + "userName", null);
        hotelName = prefs.getString(Constants.PREF_PREFIX + "hotelName", null);
        token = prefs.getString(Constants.PREF_PREFIX + "token", null);
        hotelId = prefs.getString(Constants.PREF_PREFIX + "hotelId", null);
    }

    //获取用户名
    public String getUserName() {
        return userName;
    }

    //获取密码
    public String getToken() {
//        return token;
       String myToken  = prefs.getString(Constants.PREF_PREFIX + "token", null);
        return myToken;
    }

    // 获取token
    public String getHotelName() {
        return hotelName;
    }

    public String getUserId() {
        return hotelId;
    }

    public void saveDate(String today) {
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("today", today);
        editor.commit();
    }

    public String getDate() {
        String today = prefs.getString("today", "");
        return today;
    }


    public void setChangeHead(boolean isChange) {
        SharedPreferences.Editor editor = prefs.edit();
        editor.putBoolean("isChangeHead", isChange);
        editor.commit();
    }

    public boolean isChangeHead() {
        boolean changeHead = prefs.getBoolean("isChangeHead", false);
        return changeHead;
    }

    public void saveUserDesc(String desc) {
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("userDesc", desc);
        editor.commit();
    }

    public String getUserDesc() {
        String desc = prefs.getString("userDesc", "");
        return desc;
    }

    public void setNearStat(String desc) {
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("nearOrder", desc);
        editor.commit();
    }

    public String getNearStat() {
        String desc = prefs.getString("nearOrder", "open");
        return desc;
    }

    public void  setRedLight(boolean islight) {
        SharedPreferences.Editor editor = prefs.edit();
        editor.putBoolean("isLight", islight);
        editor.commit();
    }

    public boolean getRedLight() {
        boolean light = prefs.getBoolean("isLight", true);
        return light;
    }




    public void saveRoomDates(String days) {
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("days", days);
        editor.commit();
    }

    public ArrayList<String> getRoomDays() {
        String days = prefs.getString("days", "");

        ArrayList<String> daysList = new ArrayList<String>();

        if (days != null && !"".equals(days)) {
            String[] dates = days.split(",");
            for (int i = 0; i < dates.length; i++) {
                String day = dates[i];
                daysList.add(day);
            }
        }
        return daysList;
    }

    public void saveBasePrice (String basePrice) {
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("basePrice", basePrice);
        editor.commit();
    }

    public String getBasePrice() {
        String today = prefs.getString("basePrice", "");
        return today;
    }

    /**
     * 获取String的缓存数据，没有的话默认值是""
     *
     * @param context
     * @param key
     * @return
     */
    public String getString(Context context, String key) {
        String days = prefs.getString("selectDays", "");
        return prefs.getString(key, "");
    }

    /**
     * 设置String类型的缓存
     *
     * @param context
     * @param key
     * @param value
     */
    public void setString(Context context, String key, String value) {
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(key, value);
        editor.commit();
    }

    public void setInt(Context context, String key, int value) {
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt(key, value);
        editor.commit();
    }

    public int getInt(Context context, String key) {
        return prefs.getInt(key, 0);
    }



    public void clear() {
        SharedPreferences.Editor editor = prefs.edit();
        editor.clear();
        editor.commit();
    }


}
