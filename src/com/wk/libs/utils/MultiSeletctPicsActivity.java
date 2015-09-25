package com.wk.libs.utils;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import com.playcar.R;
import com.wk.libs.WKApplication;
import com.wk.libs.WKBaseActivity;
import com.wk.libs.utils.ImageGridAdapter.TextCallback;

import net.tsz.afinal.FinalDb;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.GridView;

public class MultiSeletctPicsActivity extends WKBaseActivity {

	private List<ImageItem> dataList;
	private GridView gridView;
	private ImageGridAdapter adapter;
	private FinalDb db;

	@Override
	public void loadXml() {
		// TODO Auto-generated method stub
		setContentView(R.layout.muti_select_pic_layout);
	}

	@Override
	public void init() {
		dataList = (List<ImageItem>) getIntent().getSerializableExtra("data");
		db = FinalDb.create(ctx);
		initPageHeader();
		intitGridView();
	}

	boolean isClicked = false;

	private void initPageHeader() {
	}

	private void intitGridView() {
		gridView = (GridView) findViewById(R.id.gridview);
		gridView.setSelector(new ColorDrawable(Color.TRANSPARENT));
		adapter = new ImageGridAdapter(ctx);
		gridView.setAdapter(adapter);

		adapter.setTextCallback(new TextCallback() {
			public void onListen(int count) {
			}
		});

	}

	@Override
	public void setData() {
		// TODO Auto-generated method stub
		new Thread() {
			@Override
			public void run() {
				try {
					compressData();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}.start();

		// adapter.setData(dataList);
	}

	Handler hander = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 0:
				adapter.addItem((ImageItem) msg.obj);
				break;
			}
		}
	};

	/**
	 * 过滤 是否 图片都有缩略图地址如没有则新建
	 */
	private void compressData() {
		if (dataList != null && dataList.size() > 0) {
			for (ImageItem item : dataList) {

				String thumbPath = item.thumbnailPath;
				String sourcePath = item.imagePath;
				if (thumbPath == null || thumbPath.equals("")) {
					// holder.iv.setImageURI(Uri.fromFile(new
					// File(sourcePath)));
					item.thumbnailPath = getCurrentThumPath(sourcePath);
				} else {
					if (!isFileExist(thumbPath)) {
						item.thumbnailPath = getCurrentThumPath(sourcePath);
					}
				}

				Message msg = new Message();
				msg.what = 0;
				msg.obj = item;
				hander.sendMessage(msg);
			}
		}
	}

	/**
	 * 获取没有缩略图的地址
	 * 
	 * @param sourcePath
	 * @return
	 */
	private String getCurrentThumPath(String sourcePath) {
		String thumbnailPath = null;
		MyAlbumThumBean bean = db.findById(sourcePath, MyAlbumThumBean.class);
		if (bean != null) {
			// 已经在数据库中有的 就读取
			System.out.println("-- 读取 -- ");
			thumbnailPath = bean.getThumPath() + "/" + bean.getPhotoName()
					+ ".png";
		} else {
			// 新建 存数据库
			System.out.println("-- 新建 -- ");
			thumbnailPath = createImgBitmapThum(sourcePath);
		}

		return thumbnailPath;
	}

	private boolean isFileExist(String path) {
		try {
			File f = new File(path);
			if (f.exists()) {
				return true;
			} else {
				return false;
			}
		} catch (Exception e) {

		}

		return false;
	}

	/**
	 * 新建图片的缩略图 并存数据库
	 * 
	 * @param sourcePath
	 * @return
	 */
	private String createImgBitmapThum(String sourcePath) {
		if (!isFileExist(sourcePath)) {
			System.out.println(sourcePath + "-----------not exsit");
			return null;
		}
		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		Bitmap bitmap = BitmapFactory.decodeFile(sourcePath, options); //
		System.out.println("bitmap -- " + bitmap);
		options.inJustDecodeBounds = false;
		int be = (int) (options.outHeight / (float) 240); // 原图 压缩成 128*128的缩略图
		if (be <= 0) {
			be = 1;
		}
		options.inSampleSize = be;
		bitmap = BitmapFactory.decodeFile(sourcePath, options);
		String path = getSDPath();
		if (path == null) {
			app().showMsg("没有找到sd卡");
			return null;
		}
		String photoName = System.currentTimeMillis() + "";
		ImageTools.savePhotoToSDCard(bitmap, path, photoName);
		MyAlbumThumBean bean = new MyAlbumThumBean();
		bean.setSourthPath(sourcePath);
		bean.setPhotoName(photoName);
		bean.setThumPath(path);
		db.save(bean);

		return path + "/" + photoName + ".png";
	}

	/**
	 * 自己压缩的图片 存放地址
	 * 
	 * @return
	 */
	public String getSDPath() {
		File sdDir = null;
		boolean sdCardExist = Environment.getExternalStorageState().equals(
				android.os.Environment.MEDIA_MOUNTED); // 判断sd卡是否存在
		if (sdCardExist) {
			sdDir = new File(WKApplication.IMAGE_SD_CACHE_PATH);
			if (!sdDir.exists()) {// 判断文件夹目录是否存在

				sdDir.mkdir();// 如果不存在则创建

			}
		}
		return sdDir.toString();

	}

}
