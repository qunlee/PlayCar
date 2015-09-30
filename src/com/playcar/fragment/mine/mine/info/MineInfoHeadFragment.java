package com.playcar.fragment.mine.mine.info;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.playcar.R;
import com.playcar.fragment.mine.mine.MineBaseFragment;

/**
 * 我的资料详情界面中头部界面
 * @author Administrator
 *
 */
public class MineInfoHeadFragment extends MineBaseFragment{
	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.car_mine_info_head_fragment,container,false);
		ViewUtils.inject(this, view);
		return view;
	}
	@OnClick(R.id.txt_right)
	private void onlick_edit(View v){
		
	}
}
