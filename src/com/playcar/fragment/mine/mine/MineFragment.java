package com.playcar.fragment.mine.mine;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.playcar.R;
import com.playcar.activity.mine.CarMineInfoActivity;
import com.wqdsoft.im.BaseActivity;

public class MineFragment extends MineBaseFragment{
	private View currentLayout;
	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
	    currentLayout = inflater.inflate(R.layout.car_mine_fragment, container, false);
	    ViewUtils.inject(this, currentLayout);
		return currentLayout;
	}
	@OnClick(R.id.img_info)
	private void onclick_info(View v){
		BaseActivity activity = (BaseActivity) getActivity();
		activity.openActivityAfterLogin(new Intent(getActivity(),CarMineInfoActivity.class));
	}
}
