package com.playcar.fragment;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;
import com.hp.hpl.sparta.Text;
import com.playcar.R;
import com.playcar.Beam.TrandsBean;
import com.playcar.Beam.TrandsBean.Trands;
import com.playcar.activity.CarNearActivity;
import com.wk.libs.listview.PullToRefreshListView;
import com.wk.libs.listview.WKListViewInterface;
import com.wk.libs.listview.WKListViewUtils;
import com.wk.libs.listview.WKViewHolder;

/**
 * Created by Aure on 15/9/23. 附近——动态
 */
public class CarNearTrandsFragment extends Fragment implements
		WKListViewInterface, OnItemClickListener {

	private WKListViewUtils<Trands, TrandsBean> listUtils;

	private Activity mActivity;
	private TextView mMsgTv;
	private String mMsgName;

	private String orderStatus = "";

	private int flag;

	private int pagenum = 1;
	private boolean isPullUp = false;
	// 表示是否继续加载下一页
	private boolean notLoadNext = false;

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		this.mActivity = activity;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
	}

	private void initList() {
		PullToRefreshListView pList = (PullToRefreshListView) 
				view.findViewById(R.id.wk_pull_refresh_list);
		listUtils = new WKListViewUtils<Trands, TrandsBean>() {
		};
		listUtils.init(getActivity(), this, pList);
		listUtils.setShowLoading(true);
		listUtils.pullRefreshListView.setOnItemClickListener(this);
		listUtils.updateData();
	}
	View view;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.car_near_trands_fragment, container,
				false);
		initList();
		return view;
	}
	
	

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);

	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		final CarNearActivity activity = ((CarNearActivity) getActivity());
		View fragmentView = activity.fragments.get(0).getView();
	}

	@Override
	public void onStart() {
		super.onStart();
	}

	@Override
	public void onResume() {
		super.onResume();
	}

	@Override
	public void onPause() {
		super.onPause();
	}

	@Override
	public void onStop() {
		super.onStop();
	}

	@Override
	public void onDestroyView() {
		super.onDestroyView();
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
	}

	@Override
	public void onDetach() {
		super.onDetach();
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		// TODO Auto-generated method stub

	}

	@Override
	public String getData(boolean... isLocal) {
		// TODO Auto-generated method stub
		return "0";
	}

	@Override
	public void getDataBack(String result, boolean isLocal) {
		// TODO Auto-generated method stub
//		Type cvbType = new TypeToken<BaseBean<TrandsBean>>() {
//		}.getType();
//		listUtils.bean = new Gson().fromJson(result, cvbType);
//		listUtils.list.addAll(listUtils.bean.data.news);
//		if (listUtils.list.size() > 0)
//			listUtils.adapter.notifyDataSetChanged();
		TrandsBean trandsBean = new TrandsBean();
		
		Trands trands = trandsBean.new Trands();
		
		trands.age = "14";
		trands.name = "小软";
		trands.id = "01";
		trands.content = "小软叫小软～";
		listUtils.list.add(trands);
		listUtils.adapter.notifyDataSetChanged();
	}

	@Override
	public WKViewHolder createViewHolder(View arg1) {
		// TODO Auto-generated method stub
		return new MyViewHolder(arg1);
	}

	@Override
	public int getItemViewId() {
		// TODO Auto-generated method stub
		return R.layout.car_near_trands_list_item;
	}
	
	class MyViewHolder extends WKViewHolder<Trands> {
		TextView ageView;
		TextView nameView;
		TextView contentView;

		public MyViewHolder(View view) {
			ageView = (TextView) view.findViewById(R.id.age);
			nameView = (TextView) view.findViewById(R.id.name);
			contentView = (TextView) view.findViewById(R.id.content);
		}

		@Override
		public void setData(Trands t) {
			ageView.setText(t.age);
			nameView.setText(t.name);
			contentView.setText(t.content);
			
		}
	}

}
