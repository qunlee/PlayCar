package com.wk.libs.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Handler;
import android.widget.ImageView;

import com.itotem.imageloader.core.DisplayImageOptions;
import com.itotem.imageloader.core.ImageLoader;
import com.itotem.imageloader.core.assist.ImageLoadingListener;
import com.wk.libs.WKApplication;

public class WKHImageLoader {
	public ImageLoader loader;

	public WKApplication app;

	/**
	 * 绑定本地图片和网络下载地址
	 * 
	 * @param bitmap
	 * @param uri
	 */
	public void saveImg(Bitmap bitmap, String uri) {
		loader.saveBitmap(bitmap, uri);
	}

	private void displayLocal(String uri, ImageView imageView,
			DisplayImageOptions options, ImageLoadingListener listener) {
		System.out.println("lazy - url=" + uri);
		if (loader.isLocalExist(uri)) {
			Bitmap b = null;
			b = loader.getMemoryCache().get(uri);
			if (b != null) {
				System.out.println("lazy - in memery");
				imageView.setImageBitmap(b);
				app.recycleBitmap(b);
			} else {
				System.out.println("lazy - in disk");
				File f = loader.getDiscCache().get(uri);
				imageView.setImageURI(Uri.fromFile(f));
			}
		}
	}

	/**显示图片（带缓存）
	 */
	public void displayImage(String uri, ImageView imageView) {
		if (app.sp.getNoImgMode()) {// 无图
			displayLocal(uri, imageView, null, null);
		} else {// 正常
			loader.displayImage(uri, imageView, app.options);
		}
	}
	/**显示图片（不带缓存）
	 */
	public void displayImageNoCache(String uri,ImageView imageView){
		if (app.sp.getNoImgMode()) {// 无图
			displayLocal(uri, imageView, null, null);
		} else {// 正常
			loader.displayImage(uri, imageView, app.options2);
		}
	}

	public void displayImage(String uri, ImageView imageView,
			DisplayImageOptions options) {
		if (app.sp.getNoImgMode()) {// 无图
			displayLocal(uri, imageView, options, null);
		} else {// 正常
			// loader.displayImage(uri, imageView, options);
			if (uri.contains("http")) { // 网络图片
				loader.displayImage(uri, imageView, options);
			} else { // 本地图片
				Bitmap llBmp = createBitmapByScale(uri, 3);
				imageView.setImageBitmap(llBmp);
			}
		}
	}

	public void displayImage(String uri, ImageView imageView,
			ImageLoadingListener listener) {
		if (app.sp.getNoImgMode()) {// 无图
			displayLocal(uri, imageView, null, listener);
		} else {// 正常
			loader.displayImage(uri, imageView, app.options, listener);
		}
	}

	public void displayImage(String uri, ImageView imageView,
			DisplayImageOptions options, ImageLoadingListener listener) {
		if (app.sp.getNoImgMode()) {// 无图
			displayLocal(uri, imageView, options, listener);
		} else {// 正常
			loader.displayImage(uri, imageView, options, listener);
		}
	}

	public void putInCache(final String filePath) {
		new Handler().post(new Runnable() {
			public void run() {
				if (loader.isLocalExist(filePath) == false)
					loader.putInCache(filePath);
			}
		});
	}

	public String getFilePath(String uri) {
		return loader.getFilePath(uri);
	}

	public void clearMemoryCache() {
		loader.clearMemoryCache();
	}

	public void downLoadImage(String url) {
		loader.downLoadImage(url);
	}

	/**
	 * 按比例压缩图片
	 * 
	 * @param path
	 * @param scale
	 * @return
	 */
	public static Bitmap createBitmapByScale(String path, int scale) {
		int w = getImageWH(path)[0];
		if (w > 480)
			scale = w / 480;
		else
			scale = 1;
		Bitmap bm = null;
		try {
			// 读取图片
			BitmapFactory.Options options = new BitmapFactory.Options();
			options.inSampleSize = scale;
			InputStream is = new FileInputStream(path);
			bm = BitmapFactory.decodeStream(is, null, options);
		} catch (Exception e) {
			System.out.println("lazy createBitmapByScale Exception." + e);
		} catch (OutOfMemoryError e) {
			System.out.println("lazy OutOfMemoryError." + e);
		}
		return bm;
	}

	/** 获取图像的宽高 **/
	public static int[] getImageWH(String path) {
		int[] wh = { -1, -1 };
		if (path == null) {
			return wh;
		}
		File file = new File(path);
		if (file.exists() && !file.isDirectory()) {
			try {
				BitmapFactory.Options options = new BitmapFactory.Options();
				options.inJustDecodeBounds = true;
				InputStream is = new FileInputStream(path);
				BitmapFactory.decodeStream(is, null, options);
				wh[0] = options.outWidth;
				wh[1] = options.outHeight;
			} catch (Exception e) {
				System.out.println("lazy getImageWH Exception." + e);
			}
		}
		return wh;
	}
}
