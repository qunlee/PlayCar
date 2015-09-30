package com.playcar.activity.mine;

import net.tsz.afinal.annotation.view.ViewInject;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.widget.LinearLayout;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.playcar.R;
import com.playcar.fragment.mine.mine.MineFragment;
import com.playcar.fragment.mine.mine.MineInfoFragment;
/**
 * 我的主界面
 * @author Administrator
 *
 */
public class CarMineActivity extends CarMineBaseActivity {
	@ViewInject(id = R.id.layout_mine_top)
	private LinearLayout layout_mine_top;
	@ViewInject(id = R.id.layout_carAndDynamic)
	private LinearLayout layout_carAndDynamic;
	//我的主界面头部fragment
	private MineFragment mineFragment;
	//我的住界面动态和我的爱车fragment
	private MineInfoFragment mineInfoFragment;
	private FragmentManager manager;
	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setContentView(R.layout.car_mine_activity);
		ViewUtils.inject(this);
		init();
	}
	private void init(){
		manager = getSupportFragmentManager();
		mineFragment = new MineFragment();
		mineInfoFragment = new MineInfoFragment();
		manager.beginTransaction().add(R.id.layout_mine_top , mineFragment).add(R.id.layout_carAndDynamic, mineInfoFragment).commit();
	}
	@OnClick(R.id.txt_add_friends)
	public void onclick_add_friends(View view){
		Intent intent = new Intent(this,CarAddFriendsActivity.class);
		openActivityAfterLogin(intent);
	}
	@OnClick(R.id.txt_setting)
	public void onclick_setting(View view){
		Intent intent = new Intent(this,CarSettingActivity.class);
		openActivityAfterLogin(intent);
	}
	
}
