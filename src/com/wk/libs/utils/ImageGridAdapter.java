package com.wk.libs.utils;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.playcar.R;
import com.playcar.WKApplication;

import android.content.Context;
import android.net.Uri;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class ImageGridAdapter extends BaseAdapter {

	private Context mContext;
	private List<ImageItem> dataList;
	public Map<String, ImageItem> map = new HashMap<String, ImageItem>();
	private int selectTotal = 0;

	/**
	 * 获取全局APP
	 */
	public WKApplication app() {
		return (WKApplication) mContext.getApplicationContext();
	}

	public ImageGridAdapter(Context context) {
		this.mContext = context;
	}

	public void setData(List<ImageItem> data) {
		this.dataList = data;
		notifyDataSetChanged();
	}
	
	public void addItem(ImageItem item) {
		if(dataList==null) {
			dataList = new ArrayList<ImageItem>();
		}
		dataList.add(item);
		notifyDataSetChanged();
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		if (dataList != null) {
			return dataList.size();
		}
		return 0;
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

	private TextCallback textcallback = null;

	public void setTextCallback(TextCallback listener) {
		textcallback = listener;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup arg2) {
		final Holder holder;

		if (convertView == null) {
			holder = new Holder();
			convertView = View
					.inflate(mContext, R.layout.item_image_grid, null);
			holder.iv = (ImageView) convertView.findViewById(R.id.image);
			holder.selected = (ImageView) convertView
					.findViewById(R.id.isselected);
			holder.text = (TextView) convertView
					.findViewById(R.id.item_image_grid_text);
			convertView.setTag(holder);
		} else {
			holder = (Holder) convertView.getTag();
		}
		final ImageItem item = dataList.get(position);
		if (item.thumbnailPath == null || item.thumbnailPath.equals("")) {
			try {
				holder.iv.setImageURI(Uri.fromFile(new File(item.imagePath)));
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			try {
				holder.iv.setImageURI(Uri
						.fromFile(new File(item.thumbnailPath)));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		// app().imageLoader.putInCache(item.thumbnailPath);
		// app().imageLoader.displayImage(item.thumbnailPath, holder.iv,
		// app().options);
		holder.iv.setTag(item.imagePath);
		if (item.isSelected) {
			holder.selected.setVisibility(View.VISIBLE);
			holder.text.setBackgroundResource(R.drawable.bgd_relatly_line);
		} else {
			holder.selected.setVisibility(View.GONE);
			holder.text.setBackgroundColor(0x00000000);
		}
		holder.iv.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				String path = dataList.get(position).imagePath;

				if (selectTotal < 9) {
					item.isSelected = !item.isSelected;
					if (item.isSelected) {
						holder.selected.setVisibility(View.VISIBLE);
						;
						holder.text
								.setBackgroundResource(R.drawable.bgd_relatly_line);
						selectTotal++;
						if (textcallback != null)
							textcallback.onListen(selectTotal);
						map.put(path, item);

					} else if (!item.isSelected) {
						holder.selected.setVisibility(View.GONE);
						;
						holder.text.setBackgroundColor(0x00000000);
						selectTotal--;
						if (textcallback != null)
							textcallback.onListen(selectTotal);
						map.remove(path);
					}
				} else if (selectTotal >= 9) {
					if (item.isSelected == true) {
						item.isSelected = !item.isSelected;
						holder.selected.setImageResource(-1);
						selectTotal--;
						map.remove(path);
					} else {
						Toast.makeText(mContext, "最多一次选9张", Toast.LENGTH_SHORT)
								.show();
					}
				}
			}

		});

		return convertView;
	}

	class Holder {
		private ImageView iv;
		private ImageView selected;
		private TextView text;
	}

	public static interface TextCallback {
		public void onListen(int count);
	}

}
