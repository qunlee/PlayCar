package com.playcar.fragment.mine.mine;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.lidroid.xutils.view.annotation.event.OnItemClick;
import com.playcar.R;
import com.playcar.activity.mine.CarDetailActivity;
import com.playcar.activity.mine.CarMineBaseActivity;
import com.playcar.activity.mine.CarMyCarListActivity;
import com.playcar.activity.mine.CarPersonDynamicActivity;
import com.playcar.adapter.mine.CarMainCarListAdapter;
import com.playcar.view.MyListView;

public class MineInfoFragment extends MineBaseFragment{
	@ViewInject(R.id.mylistView)
	private MyListView listView;
	private CarMainCarListAdapter carMainCarListAdapter;
	private List<String> list = new ArrayList<String>();
	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.car_mine_info_fragment, container, false);
		ViewUtils.inject(this, view);
		return view;
	}
	@Override
	public void onActivityCreated(@Nullable Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		setAdapter();
		
	}
	private void setAdapter(){
		if(carMainCarListAdapter == null){
			list.add("");
			list.add("");
			carMainCarListAdapter = new CarMainCarListAdapter(list, getActivity());
			listView.setAdapter(carMainCarListAdapter);
		}
		carMainCarListAdapter.notifyDataSetChanged();
	}
	@OnItemClick(R.id.mylistView)
	private void onItemClick_myCar(AdapterView<?> arg0, View arg1, int arg2, long arg3){
		Intent intent = new Intent(getActivity() , CarDetailActivity.class);
		CarMineBaseActivity activity = (CarMineBaseActivity) getActivity();
		activity.openActivityAfterLogin(intent);
	}
	@OnClick(R.id.txt_addCar)
	private void onclick_addCar(View v){
		Intent intent = new Intent(getActivity() , CarMyCarListActivity.class);
		CarMineBaseActivity activity = (CarMineBaseActivity) getActivity();
		activity.openActivityAfterLogin(intent);
	}
	@OnClick(R.id.layout_dynamic)
	private void onclick_dynamic(View v){
		Intent intent = new Intent(getActivity() , CarPersonDynamicActivity.class);
		CarMineBaseActivity activity = (CarMineBaseActivity) getActivity();
		activity.openActivityAfterLogin(intent);
	}
	
}
