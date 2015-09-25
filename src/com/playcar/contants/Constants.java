package com.playcar.contants;

import android.os.Environment;

/**
 * Created by chengrong on 2015/6/2.
 */
public class Constants {
    /**
     * Preference中存储的标识前缀
     */
    public static final String PREF_PREFIX = "playcar.session.";

    //正式环境
//    public static final String BASE_URL = "http://e.zhuliangtian.com/";

//    public static final String BASE_URL = "http://192.168.1.222";

    public static final String BASE_URL = "http://192.168.1.222:8380/zlt-merchant";
//       public static final String BASE_URL = "http://192.168.131.220:8280";
//

//    http://e.zhuliangtian.com/

//
//    public static final String BASE_URL = "http://mxsl.ddns.net:8380/";
//    public static final String BASE_URL = "http://zhuliangtian.aliapp.com";


    //    public static final String BASE_URL = "http://mxsl.ddns.net:8380/";
    //  public static final String BASE_URL = "http://zhuliangtian.aliapp.com";


    public static final int PAGESIZE = 10;

    public static final String MESSAGE_SUCCESS = "success";

    public static final String MESSAGE_ERROR = "error";

    public static final String MESSAGE_FAILED = "failed";

    public static final String ABOUT_US_URL = BASE_URL + "/about";

    public static final String PAY_SUCCESS = "PAY_SUCCESS";

    public static final String MERCHANT_CONFIRMED = "MERCHANT_CONFIRMED";

    public static final String TRADE_FINISH = "TRADE_FINISH";

    public static final String MERCHANT_REJECT = "MERCHANT_REJECT";

    public static final String TEMPORARY_BOOKING = "TEMPORARY_BOOKING";

    public static final String NEW_BOOK_INTENTION = "NEW_BOOK_INTENTION";
    public static final String CONFIRM_BOOK_INTENTION = "CONFIRM_BOOK_INTENTION";
    public static final String SERVICING_BOOK_INTENTION = "SERVICING_BOOK_INTENTION";
    public static final String ROBBED_BOOK_INTENTION = "ROBBED_BOOK_INTENTION";


    public static final String filePathCover = Environment.getExternalStorageDirectory() + "/postphotoCover/";
    public static final String filePathPhoto = Environment.getExternalStorageDirectory() + "/postphotoPhoto/";


}
