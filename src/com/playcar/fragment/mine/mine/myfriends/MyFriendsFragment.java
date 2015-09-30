package com.playcar.fragment.mine.mine.myfriends;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lidroid.xutils.ViewUtils;
import com.playcar.R;
import com.playcar.adapter.mine.MyFriendsAdapter;
import com.playcar.fragment.mine.mine.MineBaseFragment;

/**
 * 我的朋友
 * @author Administrator
 *
 */
public class MyFriendsFragment extends MineBaseFragment{
	private MyFriendsAdapter friendsAdapter;
	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.car_my_friends_fragment, container, false);
		ViewUtils.inject(this, view);
		return view;
	}
}
