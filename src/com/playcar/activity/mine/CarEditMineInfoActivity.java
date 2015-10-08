package com.playcar.activity.mine;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;

import com.playcar.R;
import com.playcar.activity.mine.imp.FinishActivityStrategy;
import com.playcar.activity.mine.imp.MenuStrategy;
import com.playcar.fragment.mine.mine.MineBaseFragment;
import com.playcar.fragment.mine.mine.edit.EditMineDataFragment;
import com.playcar.fragment.mine.mine.edit.EditMineHeadFragment;

/**
 * 编辑我的资料
 * @author Administrator
 *
 */
public class CarEditMineInfoActivity extends CarMineBaseActivity{
	private MineBaseFragment editHeadFragment;
	private MineBaseFragment editDataFragment;
	private FragmentManager manager;
	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setContentView(R.layout.car_edit_mine_info_activity);
		initializeHead("取消","编辑资料", "保存");
		MenuStrategy activityStrategy = new FinishActivityStrategy(this);
		setMenuStrategy(activityStrategy);
		init();
	}
	private void init(){
		manager = getSupportFragmentManager();
		editHeadFragment = new EditMineHeadFragment();
		editDataFragment  = new EditMineDataFragment();
		manager.beginTransaction().add(R.id.layout_head, editHeadFragment).add(R.id.layout_data, editDataFragment).commit();
	}
}
