package com.playcar.activity.mine;

import android.os.Bundle;

import com.lidroid.xutils.ViewUtils;
import com.playcar.R;

/**
 * 汽车列表界面
 * @author Administrator
 *
 */
public class CarListActivity extends CarMineBaseActivity{
//	//汽车品牌
//	@ViewInject(R.id.car_brand)
//	private CarBrandView carBrandView;
//	//汽车品牌对应的汽车类型
//	@ViewInject(R.id.car_type)
//	private CarTypeView carTypeView;
	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setContentView(R.layout.car_list_activity);
		initializeHead("返回", "车辆认证", "没有我的车型");
		ViewUtils.inject(this);
	}
}
