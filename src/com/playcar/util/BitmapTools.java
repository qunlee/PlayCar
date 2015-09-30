package com.playcar.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
public class BitmapTools {
	private static int maxSize = 100;
	
	public static int getMaxSize() {
		return maxSize;
	}
	public static void setMaxSize(int maxSize) {
		BitmapTools.maxSize = maxSize;
	}
	public static Bitmap getBitmap(String srcPath, int width, int height) {
		if(srcPath == null || srcPath.trim().equals("")){
			return null;
		}
		BitmapFactory.Options newOpts = new BitmapFactory.Options();
		newOpts.inJustDecodeBounds = true;
		Bitmap bitmap = BitmapFactory.decodeFile(srcPath, newOpts);
		int w = newOpts.outWidth;
		int h = newOpts.outHeight;
		float hh = width;
		float ww = height;
		int be = 1;
		if (w > h && w > ww) {
			be = (int) (newOpts.outWidth / ww);
		} else if (w < h && h > hh) {
			be = (int) (newOpts.outHeight / hh);
		}
		if (be <= 0)
			be = 1;
		newOpts.inSampleSize = be;
		newOpts.inJustDecodeBounds = false;
		bitmap = BitmapFactory.decodeFile(srcPath, newOpts);
		return compressImage(bitmap, srcPath);
	}
	private static Bitmap compressImage(Bitmap image, String path) {
		try {
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			image.compress(Bitmap.CompressFormat.JPEG, 100, baos);
			int options = 100;
			while (baos.toByteArray().length / 1024 > maxSize) {
				if(options<=0){
					break;
				}
				baos.reset();
				image.compress(Bitmap.CompressFormat.JPEG, options, baos);
				options -= 10;
			}
			ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());
			Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, null);
			FileOutputStream fos = new FileOutputStream(path);
			bitmap.compress(Bitmap.CompressFormat.JPEG, options, fos);
			fos.flush();
			if (!image.isRecycled()) {
				image.recycle();
			}
			return bitmap;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return null;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

}
