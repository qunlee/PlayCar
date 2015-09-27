package com.wk.libs.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import com.playcar.WKApplication;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;

/**
 * 照片工具类：
 * 
 * 1、拍照
 * 
 * 2、选取单张
 * 
 * 3、选取多张
 * 
 * 
 * DEMO：
 * 
 * app().takePhoto.takePhoto(this);
 * 
 * protected void onActivityResult(int requestCode, int resultCode, Intent data)
 * { super.onActivityResult(requestCode, resultCode, data);
 * 
 * // 拍照返回处理 app().takePhoto.takePhotoBack(requestCode, resultCode, data, new
 * ITakePhoto() { public void handle(String fn) { if (fn != null &&
 * !fn.equals("")) { img.setImageBitmap(BitmapFactory.decodeFile(fn)); } }
 * 
 * public void handle(ArrayList<String> fnList) { } });
 * 
 */
public class WKTakePhotoUtils {

	// 拍照
	private static final int WK_PHOTO_UTILS_TAKE_A_PHOTO = 10000;
	// 选取
	private static final int WK_PHOTO_UTILS_SELECT = 10020;
	// 选取并裁剪
	private static final int WK_PHOTO_UTILS_SELECT_CROP = 10030;
	// 裁剪
	private static final int WK_PHOTO_UTILS_CROP_PICTURE = 10040;
	// 选取列表
	private static final int WK_PHOTO_UTILS_MUTI_SELECT = 10050;

	public WKApplication app;

	// 是否保存缩放后的图片以节省内存
	public static boolean needResizeImage = true;

	// 如果resizeImage，最大宽度值
	public static int maxWidth = 1024;

	// 裁剪照片的宽度
	public static int CROP_WIDTH = 500;

	public void initApp(WKApplication app) {
		this.app = app;
	}

	/**************************************************************************************
	 * 拍照
	 */
	public void takePhoto(Activity activity) {
		Intent openCameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		Uri imageUri = Uri.fromFile(new File(Environment
				.getExternalStorageDirectory(), TAKE_PHOTO_FN));
		// 指定照片保存路径（SD卡），image.jpg为一个临时文件，每次拍照后这个图片都会被替换
		openCameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
		activity.startActivityForResult(openCameraIntent,
				WK_PHOTO_UTILS_TAKE_A_PHOTO);
	}

	/**
	 * 拍完的原始图片，存放在内存卡中
	 */
	public static final String TAKE_PHOTO_FN = "TAKE_PHOTO_FILENAME.jpg";

	/**
	 * 拍照的result
	 * 
	 * @param requestCode
	 * @param resultCode
	 * @param data
	 * @return
	 */
	public void takePhotoBack(int requestCode, int resultCode, Intent data,
			ITakePhoto takePhoto) {
		try {
			if (resultCode != Activity.RESULT_OK) {
				return;
			}

			if (requestCode == WK_PHOTO_UTILS_TAKE_A_PHOTO) {

				// 将保存在本地的图片取出并缩小后显示在界面上
				String path = Environment.getExternalStorageDirectory() + "/"
						+ TAKE_PHOTO_FN;

				// 是否需要resize
				checkNeedResizeImage(path);

				// 给Activity返回数据
				takePhoto.handle(path);

				// Bitmap bitmap = createBitmapByScale(path, 1);
				// // 将处理过的图片显示在界面上，并保存到本地
				// // img_select_ok.setImageBitmap(newBitmap);
				// String name = "image_" +
				// SystemClock.currentThreadTimeMillis();
				// savePhotoToSDCard(bitmap, Environment
				// .getExternalStorageDirectory().getAbsolutePath(), "/"
				// + name);
				// // 将保存在本地的图片取出并缩小后显示在界面上
				// String fn = Environment.getExternalStorageDirectory()
				// .getAbsolutePath() + "/" + name + ".jpg";
				// // 保存到缓存系统
				// app.imageLoader.saveImg(bitmap, fn);
				// app.recycleBitmap(bitmap);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**************************************************************************************
	 * 选择单张的照片,直接返回
	 */
	public void selectPhoto(Activity activity) {
		Intent intent = new Intent(Intent.ACTION_PICK, null);
		intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
				"image/*");
		activity.startActivityForResult(intent, WK_PHOTO_UTILS_SELECT);
	}

	/**
	 * 选择照片返回
	 */
	public void selectPhotoBack(int requestCode, int resultCode, Intent data,
			Activity act, ITakePhoto takePhoto) {
		try {
			if (resultCode != Activity.RESULT_OK) {
				return;
			}
			if (requestCode == WK_PHOTO_UTILS_SELECT) {
				String path = uri2Path(data.getData(), act);
				// 是否需要resize
				checkNeedResizeImage(path);
				takePhoto.handle(path);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**************************************************************************************
	 * 选择单张的照片,并裁剪
	 */
	public void selectPhotoAndCrop(Activity activity) {
		Intent intent = new Intent(Intent.ACTION_PICK, null);
		intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
				"image/*");
		activity.startActivityForResult(intent, WK_PHOTO_UTILS_SELECT_CROP);
	}

	/**
	 * 选择照片返回
	 */
	public void selectPhotoAndCropBack(int requestCode, int resultCode,
			Intent data, Activity act, ITakePhoto takePhoto) {
		try {
			if (resultCode != Activity.RESULT_OK) {
				return;
			}
			if (requestCode == WK_PHOTO_UTILS_SELECT_CROP) {
				startPhotoZoom(data.getData(), act, takePhoto);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/*************************************************************************************
	 * 裁剪返回
	 */
	public void cropBack(int requestCode, int resultCode, Intent data,
			Activity act, ITakePhoto takePhoto) {
		try {
			if (resultCode != Activity.RESULT_OK) {
				return;
			}
			if (requestCode == WK_PHOTO_UTILS_CROP_PICTURE) {
				takePhoto.handle(data.getData().getPath());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**************************************************************************************
	 * 批量选择
	 */
	public void selectPhotoList(Activity act) {
		act.startActivityForResult(new Intent(act, AlbumActivity.class),
				WK_PHOTO_UTILS_MUTI_SELECT);
	}

	/**
	 * 批量选择返回
	 */
	public void selectPhotoListBack(int requestCode, int resultCode,
			Intent data, Activity act, ITakePhoto takePhoto) {
		if (resultCode != Activity.RESULT_OK) {
			return;
		}
		try {

			if (requestCode == WK_PHOTO_UTILS_MUTI_SELECT) {
				@SuppressWarnings("unchecked")
				final List<ImageItem> list = (List<ImageItem>) data
						.getSerializableExtra("data");
				ArrayList<String> fnList = new ArrayList<String>();
				for (ImageItem item : list) {
					fnList.add(item.imagePath);
				}
				takePhoto.handle(fnList);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 批量选择
	 *************************************************************************************/

	/**
	 * 缩放bitmap
	 */
	public static Bitmap createBitmapByMaxWight(String path, int maxWight) {
		int w = getImageWH(path)[0];
		int scale;
		if (w > maxWight)
			scale = w / maxWight;
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
			e.printStackTrace();
		} catch (OutOfMemoryError e) {
			e.printStackTrace();
		}
		return bm;
	}

	/**
	 * 获取图像的宽高
	 */
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
				e.printStackTrace();
			}
		}
		return wh;
	}

	/***************************************************************************/

	/**
	 * 删除文件
	 * 
	 * @param path
	 * @param fileName
	 */
	public static void deletePhotoAtPathAndName(String path, String fileName) {
		if (checkSDCardAvailable()) {
			File folder = new File(path);
			File[] files = folder.listFiles();
			for (int i = 0; i < files.length; i++) {
				System.out.println(files[i].getName());
				if (files[i].getName().equals(fileName)) {
					files[i].delete();
				}
			}
		}
	}

	/**
	 * Check the SD card
	 */
	public static boolean checkSDCardAvailable() {
		return android.os.Environment.getExternalStorageState().equals(
				android.os.Environment.MEDIA_MOUNTED);
	}

	/**
	 * Resize the bitmap
	 * 
	 * @param bitmap
	 * @param width
	 * @param height
	 * @return
	 */
	public static Bitmap zoomBitmap(Bitmap bitmap, int width, int height) {
		int w = bitmap.getWidth();
		int h = bitmap.getHeight();
		Matrix matrix = new Matrix();
		float scaleWidth = ((float) width / w);
		float scaleHeight = ((float) height / h);
		matrix.postScale(scaleWidth, scaleHeight);
		Bitmap newbmp = Bitmap.createBitmap(bitmap, 0, 0, w, h, matrix, true);
		return newbmp;
	}

	/**
	 * Save image to the SD card
	 * 
	 * @param photoBitmap
	 * @param photoName
	 * @param path
	 */
	public static void savePhotoToSDCard(Bitmap photoBitmap, String path,
			String photoName) {
		if (checkSDCardAvailable()) {
			File dir = new File(path);
			if (!dir.exists()) {
				dir.mkdirs();
			}
			File photoFile = new File(path, photoName);
			FileOutputStream fileOutputStream = null;
			try {
				fileOutputStream = new FileOutputStream(photoFile);
				if (photoBitmap != null) {
					if (photoBitmap.compress(Bitmap.CompressFormat.JPEG, 90,
							fileOutputStream)) {
						fileOutputStream.flush();
					}
				}
			} catch (FileNotFoundException e) {
				photoFile.delete();
				e.printStackTrace();
			} catch (IOException e) {
				photoFile.delete();
				e.printStackTrace();
			} finally {
				try {
					fileOutputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * URI转换为文件路径
	 * 
	 * @param uri
	 * @param act
	 * @return
	 */
	@SuppressLint("NewApi")
	public String uri2Path(Uri uri, Activity act) {
		String img_path = "";
		if (DocumentsContract.isDocumentUri(act, uri)) {
			String wholeID = DocumentsContract.getDocumentId(uri);
			String id = wholeID.split(":")[1];
			String[] column = { MediaStore.Images.Media.DATA };
			String sel = MediaStore.Images.Media._ID + "=?";
			Cursor cursor = act.getContentResolver().query(
					MediaStore.Images.Media.EXTERNAL_CONTENT_URI, column, sel,
					new String[] { id }, null);
			int columnIndex = cursor.getColumnIndex(column[0]);
			if (cursor.moveToFirst()) {
				img_path = cursor.getString(columnIndex);
			}
			cursor.close();
		} else {
			int actual_image_column_index;
			String[] proj = { MediaStore.Images.Media.DATA };
			Cursor cursor = act.getContentResolver().query(uri, proj, null,
					null, null);
			actual_image_column_index = cursor
					.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
			cursor.moveToFirst();
			img_path = cursor.getString(actual_image_column_index);
		}

		return img_path;
	}

	public static final String WK_CROP_FILE = "WK_CROP_FILE.jpg";// 裁剪保存的图片

	/**
	 * 裁剪照片
	 * 
	 * @param uri
	 * @param act
	 * @param takePhoto
	 */
	@SuppressLint("SdCardPath")
	public void startPhotoZoom(Uri uri, Activity act, ITakePhoto takePhoto) {
		Uri bigImgURl = Uri.parse("file:///sdcard/" + WK_CROP_FILE);
		Intent intent = new Intent("com.android.camera.action.CROP");
		intent.setDataAndType(uri, "image/*");
		// 下面这个crop=true是设置在开启的Intent中设置显示的VIEW可裁剪
		intent.putExtra("crop", "true");
		// aspectX aspectY 是宽高的比例
		intent.putExtra("aspectX", 1);
		intent.putExtra("aspectY", 1);
		// outputX outputY 是裁剪图片宽高
		intent.putExtra("outputX", CROP_WIDTH);
		intent.putExtra("outputY", CROP_WIDTH);
		intent.putExtra("return-data", false);
		intent.putExtra(MediaStore.EXTRA_OUTPUT, bigImgURl);
		intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
		act.startActivityForResult(intent, WK_PHOTO_UTILS_CROP_PICTURE);
	}

	/**
	 * 检测是否需要缩小图片，内存
	 */
	public void checkNeedResizeImage(String fn) {
		if (needResizeImage == false)
			return;
		Bitmap bitmap = createBitmapByMaxWight(fn, maxWidth);
		savePhotoToSDCard(bitmap, "", fn);
	}

}