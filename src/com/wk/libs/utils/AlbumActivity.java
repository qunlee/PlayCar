package com.wk.libs.utils;

import java.io.File;
import java.io.Serializable;
import java.util.List;

import com.playcar.R;
import com.playcar.WKApplication;
import com.wk.libs.WKBaseActivity;

import net.tsz.afinal.FinalDb;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;

public class AlbumActivity extends WKBaseActivity {

	private List<ImageBucket> dataList;
	private GridView gridView;
	private AlbumAdapter adapter;
	private AlbumHelper helper;
	private final int MULTI_SELETED = 5;
	private FinalDb db;

	@Override
	public void loadXml() {
		setContentView(R.layout.album_layout);
	}

	@Override
	public void init() {
		db = FinalDb.create(ctx);
		initHelper();
		initView();
		initData();

	}

	private void initHelper() {
		helper = AlbumHelper.getHelper();
		helper.init(getApplicationContext());
	}

	/**
	 * 初始化数据
	 */
	private void initData() {
		dataList = helper.getImagesBucketList(true);
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
	}

	Handler hander = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 0:
				adapter.addItem((ImageBucket) msg.obj);
				break;
			}
		}
	};

	/**
	 * 过滤 是否 图片都有缩略图地址如没有则新建
	 */
	private void compressData() {
		if (dataList != null && dataList.size() > 0) {
			for (ImageBucket item : dataList) {
				if (item.imageList != null && item.imageList.size() > 0) {
					String thumbPath = item.imageList.get(0).thumbnailPath;
					String sourcePath = item.imageList.get(0).imagePath;
					if (thumbPath == null || thumbPath.equals("")) {
						item.imageList.get(0).thumbnailPath = getCurrentThumPath(sourcePath);
					} else {
						if (!isFileExist(item.imageList.get(0).thumbnailPath)) {
							item.imageList.get(0).thumbnailPath = getCurrentThumPath(sourcePath);
						}
					}
				}

				Message msg = new Message();
				msg.what = 0;
				msg.obj = item;
				hander.sendMessage(msg);
			}
		}
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
			// System.out.println("-- 读取 -- ");
			thumbnailPath = bean.getThumPath() + "/" + bean.getPhotoName()
					+ ".png";
		} else {
			// 新建 存数据库
			// System.out.println("-- 新建 -- ");
			thumbnailPath = createImgBitmapThum(sourcePath);
		}

		return thumbnailPath;
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
		int be = (int) (options.outHeight / (float) 200); // 原图 压缩成 128*128的缩略图
		if (be <= 0) {
			be = 1;
		}
		options.inSampleSize = be;

		bitmap = BitmapFactory.decodeFile(sourcePath, options);
		// int w = bitmap.getWidth();
		// int h = bitmap.getHeight();
		// System.out.println(w + " " + h);
		String path = getSDPath();
		System.out.println("path --------- " + path);
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

	/**
	 * 初始化view视图
	 */
	private void initView() {
		gridView = (GridView) findViewById(R.id.gridview);
		adapter = new AlbumAdapter(ctx);
		gridView.setAdapter(adapter);

		gridView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				/**
				 * 根据position参数，可以获得跟GridView的子View相绑定的实体类，然后根据它的isSelected状态，
				 * 来判断是否显示选中效果。 至于选中效果的规则，下面适配器的代码中会有说明
				 */
				// if(dataList.get(position).isSelected()){
				// dataList.get(position).setSelected(false);
				// }else{
				// dataList.get(position).setSelected(true);
				// }
				/**
				 * 通知适配器，绑定的数据发生了改变，应当刷新视图
				 */
				// adapter.notifyDataSetChanged();
				Intent intent = new Intent(ctx, MultiSeletctPicsActivity.class);
				intent.putExtra("data",
						(Serializable) dataList.get(position).imageList);
				startActivityForResult(intent, MULTI_SELETED);
			}

		});
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == MULTI_SELETED) {
			if (resultCode == RESULT_OK) {
				setResult(RESULT_OK, data);
				finish();
			}
		}

	}

	@Override
	public void setData() {
		// TODO Auto-generated method stub

	}

}
