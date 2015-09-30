package com.playcar.activity.mine;

import android.os.Bundle;

import com.playcar.R;
import com.playcar.adapter.mine.MyFriendsAdapter;

/**
 * 我的关注和粉丝
 * @author Administrator
 *
 */
public class CarMyFollowOrFansActivity extends CarMineBaseActivity{
	private MyFriendsAdapter friendsAdapter;
	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setContentView(R.layout.car_my_follow_activity);
	}
}
