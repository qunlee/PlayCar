package com.playcar.fragment.mine.mine;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.playcar.R;

public class MineInfoFragment extends MineBaseFragment{
	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.car_mine_info_fragment, container, false);
		return view;
	}
}
