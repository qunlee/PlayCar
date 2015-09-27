package com.wk.libs.utils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.playcar.R;
import com.playcar.WKApplication;

import android.content.Context;
import android.net.Uri;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class AlbumAdapter extends BaseAdapter {

	private Context mContext;
	private List<ImageBucket> list;
	private WKApplication application;

	/**
	 * 获取全局APP
	 */
	public WKApplication app() {
		return (WKApplication) mContext.getApplicationContext();
	}

	public AlbumAdapter(Context context) {
		this.mContext = context;
	}

	public void setData(List<ImageBucket> data) {
		this.list = data;
		System.out.println("count -------------- " + list.size());
		notifyDataSetChanged();
		application = app();
	}
	
	public void addItem(ImageBucket item) {
		if(this.list == null) {
			this.list = new ArrayList<ImageBucket>();
		}
		this.list.add(item);
		notifyDataSetChanged();
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		int count = 0;
		if (list != null) {
			count = list.size();
		}

		return count;
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int arg0, View arg1, ViewGroup arg2) {
		ViewHolder holder;
		if (arg1 == null) {
			holder = new ViewHolder();
			arg1 = View.inflate(mContext, R.layout.item_image_bucket, null);
			holder.iv = (ImageView) arg1.findViewById(R.id.image);
			holder.selected = (ImageView) arg1.findViewById(R.id.isselected);
			holder.name = (TextView) arg1.findViewById(R.id.name);
			holder.count = (TextView) arg1.findViewById(R.id.count);
			arg1.setTag(holder);
		} else {
			holder = (ViewHolder) arg1.getTag();
//			app().recycleImageView(holder.iv);
		}
		ImageBucket item = list.get(arg0);
		System.out.println("arg0 ------------ " + arg0);
		holder.count.setText("" + item.count);
		holder.name.setText(item.bucketName);
		holder.selected.setVisibility(View.GONE);
		if (item.imageList != null && item.imageList.size() > 0) {
			String thumbPath = item.imageList.get(0).thumbnailPath;
			String sourcePath = item.imageList.get(0).imagePath;
			System.out.println("url --------------------- " + thumbPath);
			// application.imageLoader.putInCache(thumbPath);
			if (thumbPath == null || thumbPath.equals("")) {
//				holder.iv.setImageURI(Uri.fromFile(new File(sourcePath)));
				holder.iv.setImageDrawable(mContext.getResources().getDrawable(R.drawable.camera_bg));

			} else {
				holder.iv.setImageURI(Uri.fromFile(new File(thumbPath)));
			}
			// application.imageLoader.displayImage(thumbPath, holder.iv,
			// app().options);
			holder.iv.setTag(sourcePath);

		} else {
			holder.iv.setImageBitmap(null);
		}
		return arg1;
	}

	class ViewHolder {

		private ImageView iv;
		private ImageView selected;
		private TextView name;
		private TextView count;

	}

}
