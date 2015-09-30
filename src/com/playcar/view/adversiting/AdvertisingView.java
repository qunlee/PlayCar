package com.playcar.view.adversiting;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.TextView;

import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.playcar.R;
import com.playcar.bean.Advertising;
import com.playcar.util.Tools;

public class AdvertisingView extends FrameLayout {
	private Context context;
	private View currentView;
	@ViewInject(R.id.view_pager_advertising)
	private AutoScrollViewPager viewPager;
	@ViewInject(R.id.advertising_Cricle)
	private AdvertisingCricle advertisingCricle;
	
	private OnclickListener listener;
	public void setListener(OnclickListener listener){
		this.listener = listener;
	}

	/**
	 * 专用于新房广告位
	 */
	@ViewInject(R.id.tv_pic_num)
	private TextView tvPictureNum;

	private final int DEFAULT_SSIZE = 1;
	private List<Advertising> list = new ArrayList<Advertising>();

	public AdvertisingView(Context context) {
		super(context);
		init(context);
	}

	public AdvertisingView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context);
	}

	public AdvertisingView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init(context);
	}

	private void init(Context context) {
		this.context = context;
		currentView = LayoutInflater.from(getContext()).inflate(R.layout.car_adversting_view, null);
		ViewUtils.inject(this, currentView);
		this.addView(currentView);
		List<Advertising> list = new ArrayList<Advertising>();
		for (int i = 0; i < DEFAULT_SSIZE; i++) {
			Advertising ad = new Advertising("", R.drawable.car);
			list.add(ad);
		}
		setAdvertisings(list);
	}

	/**
	 * 注入广告数据
	 * 
	 * @param advertisings
	 */
	public void setAdvertisings(List<Advertising> list) {
		if (list == null) {
			return;
		}
		this.list.clear();
		this.list.addAll(list);
		viewPager.setAdapter(new ImagePagerAdapter(getContext(), this.list).setInfiniteLoop(true));
		viewPager.setOnPageChangeListener(new MyOnPageChangeListener());
		viewPager.setInterval(2000);
		viewPager.startAutoScroll();
		advertisingCricle.setCount(list.size());
	}

	public class MyOnPageChangeListener implements OnPageChangeListener {

		@Override
		public void onPageSelected(int position) {
			// String str = new StringBuilder().append((position) % list.size()
			// + 1).append("/").append(list.size()).toString();
			// // Log.i("tag", str);
			int index = (position) % list.size();
			advertisingCricle.setSelectItem(index);

		}

		@Override
		public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
		}

		@Override
		public void onPageScrollStateChanged(int arg0) {
		}
	}

	public class ImagePagerAdapter extends RecyclingPagerAdapter {

		private BitmapUtils bu;
		private Context context;
		private List<Advertising> imageIdList;

		private int size;
		private boolean isInfiniteLoop;

		public ImagePagerAdapter(Context context, List<Advertising> imageIdList) {
			this.context = context;
			this.imageIdList = imageIdList;
			this.size = imageIdList.size();
			isInfiniteLoop = false;
			bu = new BitmapUtils(getContext());
			bu.configDefaultLoadFailedImage(R.drawable.car);
			bu.configDefaultLoadingImage(R.drawable.car);
		}

		@Override
		public int getCount() {
			// Infinite loop
			return isInfiniteLoop ? Integer.MAX_VALUE : imageIdList.size();
		}

		/**
		 * get really position
		 * 
		 * @param position
		 * @return
		 */
		private int getPosition(int position) {
			return isInfiniteLoop ? position % size : position;
		}

		@Override
		public View getView(int position, View view, ViewGroup container) {
			ViewHolder holder;
			if (view == null) {
				holder = new ViewHolder();
				view = holder.imageView = new ImageView(context);
				view.setTag(holder);
			} else {
				holder = (ViewHolder) view.getTag();
			}
			final Advertising advertising = imageIdList.get(getPosition(position));
			holder.imageView.setScaleType(ScaleType.CENTER_CROP);
			// holder.imageView.setImageResource(advertising.defaultImgResource);
			holder.imageView.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					if (advertising.intent != null) {
						getContext().startActivity(advertising.intent);
					}else{
						AdvertisingView.this.setOnClickListener();
					}
				}
			});
			bu.display(holder.imageView, advertising.path);
			return view;
		}
		public class ViewHolder {
			ImageView imageView;
		}

		/**
		 * @return the isInfiniteLoop
		 */
		public boolean isInfiniteLoop() {
			return isInfiniteLoop;
		}

		/**
		 * @param isInfiniteLoop
		 *            the isInfiniteLoop to set
		 */
		public ImagePagerAdapter setInfiniteLoop(boolean isInfiniteLoop) {
			this.isInfiniteLoop = isInfiniteLoop;
			return this;
		}
	}

	public void setPicNumVisibility() {
		tvPictureNum.setVisibility(View.VISIBLE);
	}

	public void setPicNumText(String str) {
		if(!Tools.StringHasContent(str)){
			tvPictureNum.setText("共"+0+"张");
			return;
		}
		tvPictureNum.setText("共"+str+"张");
	}
	public interface OnclickListener{
		public void onclick();
	}
	public void setOnClickListener(){
		if(this.listener != null){
			this.listener.onclick();
		}
	}

}
