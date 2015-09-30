package com.playcar.activity.mine;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;

import com.playcar.R;
import com.playcar.fragment.mine.mine.MineBaseFragment;
import com.playcar.fragment.mine.mine.info.MineInfoDataFragment;
import com.playcar.fragment.mine.mine.info.MineInfoHeadFragment;

/**
 * 我的资料
 * @author Administrator
 *
 */
public class CarMineInfoActivity extends CarMineBaseActivity{
	private MineBaseFragment mineHeadFragment;
	private MineBaseFragment mineDataFragment;
	private FragmentManager manager;
	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setContentView(R.layout.car_mine_info_activity);
		init();
		
	}
	private void init(){
		manager = getSupportFragmentManager();
		mineHeadFragment = new MineInfoHeadFragment();
		mineDataFragment = new MineInfoDataFragment();
		manager.beginTransaction().add(R.id.layout_head, mineHeadFragment).add(R.id.layout_data, mineDataFragment).commit();
	}
}
