package com.wk.libs.utils;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Message;

/**
 * ImageView图片显示工具
 */
public class WKImageUtil {
	
	
	private Handler handler = new Handler(){
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			if (msg.what == 1) {
				callback.result((Bitmap)msg.obj);
			}else{
			}
		}
	};
	
	/**
	 * 显示图片回调接口
	 */
	public interface ShowCallBack{
		public void result(Bitmap bitmap);
		public void fail(String error);
	}
	private ShowCallBack callback;
	/**
	 * 显示Url图片
	 */
	public void show(final String url, ShowCallBack callback){
		this.callback = callback;
		new Thread(){
			@Override
			public void run() {
				super.run();
				URL myUrl = null;
				Bitmap bitmap = null;
				try {
					myUrl = new URL(url);
				} catch (MalformedURLException e) {
					e.printStackTrace();
					Message msg = handler.obtainMessage();
					msg.what = 0;
					handler.sendMessage(msg);
				}
				try {
					HttpURLConnection conn = (HttpURLConnection) myUrl.openConnection();
					conn.setConnectTimeout(3*1000);
					conn.setDoInput(true);
					conn.connect();
					InputStream inStream = conn.getInputStream();
					bitmap = BitmapFactory.decodeStream(inStream);
					inStream.close();
				} catch (IOException e) {
					e.printStackTrace();
					Message msg = handler.obtainMessage();
					msg.what = 0;
					handler.sendMessage(msg);
				}
				if (bitmap != null) {
					Message msg = handler.obtainMessage();
					msg.obj = bitmap;
					msg.what = 1;
					handler.sendMessage(msg);
				}
			}
		}.start();
	}

}
