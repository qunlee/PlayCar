package com.wk.libs;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.playcar.WKApplication;
import com.wk.libs.net.WKAsyncTaskPro;

import android.content.Context;
import android.content.Intent;

/**
 * 异常处理类：遇到异常不显示错误信息，直接重启，后续可增加信息收集
 */
public class WKCrashHandler implements Thread.UncaughtExceptionHandler {
	public static final String TAG = WKCrashHandler.class.getSimpleName();
	private static WKCrashHandler INSTANCE = new WKCrashHandler();
	private Context context;

	private WKCrashHandler() {
	}

	private WKApplication app() {
		return (WKApplication) context.getApplicationContext();
	}

	public static WKCrashHandler getInstance() {
		return INSTANCE;
	}

	public void init(Context context) {
		this.context = context;
		Thread.setDefaultUncaughtExceptionHandler(this);
	}

	@Override
	public void uncaughtException(Thread thread, final Throwable ex) {
		ex.printStackTrace();
		// System.out.println("lazy error thread  in");
		// new SendErrorMsg((Activity) context).execute(getTimeStr()
		// + PublicUtils.getErrorInfo(ex));
		// new Thread(new Runnable() {
		// public void run() {
		// System.out.println("lazy error thread msg=" + msg);
		// ItotemNetLib.getInstance(context).SendErrorMsg(msg);
		// }
		// }).start();
		try {
			String msg = getTimeStr() + getErrorInfo(ex);
			// SharedPreferencesUtil.getinstance(context).setString("last_error",
			// "" + msg);\

			// app().showMsg(msg, new OnClickListener() {
			// public void onClick(DialogInterface arg0, int arg1) {
			// }
			// });
		} catch (Exception e) {
			e.printStackTrace();
		}
		restart();
		// app().restart();
		return;
	}

	public String getTimeStr() {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss ");
		return df.format(new Date());
	}

	/**
	 * 发送错误信息
	 */
	class SendErrorMsg extends WKAsyncTaskPro {
		public SendErrorMsg(Context activity) {
			super(activity);
		}

		protected String doInBackground(boolean... params) {
			return null;
		}

		protected void doOnPostExecute(String result, boolean isLocal) {
		}
	}

	public void restart() {
		// app().exit();
		Intent intent = new Intent();
		intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
				| Intent.FLAG_ACTIVITY_NEW_TASK);
		context.startActivity(intent);
		// 保存
		android.os.Process.killProcess(android.os.Process.myPid());
	}

	/**
	 * 获取错误的信息
	 */
	public static String getErrorInfo(Throwable arg1) {
		Writer writer = new StringWriter();
		PrintWriter pw = new PrintWriter(writer);
		arg1.printStackTrace(pw);
		pw.close();
		String error = writer.toString();
		return error;
	}

}
