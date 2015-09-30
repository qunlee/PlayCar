package com.playcar.fragment.mine.mine.edit;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lidroid.xutils.ViewUtils;
import com.playcar.R;
import com.playcar.fragment.mine.mine.MineBaseFragment;

public class EditMineHeadFragment extends MineBaseFragment{
	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.car_edit_mine_head_fragment, container, false);
		ViewUtils.inject(this, view);
		return view;
	}
}
