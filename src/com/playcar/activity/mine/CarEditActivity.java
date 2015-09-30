package com.playcar.activity.mine;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;

import com.playcar.R;
import com.playcar.adapter.mine.CarHorizontalAdapter;
import com.playcar.view.HorizontalListView;

/**
 * 爱车详情编辑
 * @author Administrator
 *
 */
public class CarEditActivity extends Activity {
	private HorizontalListView hListView;
	private CarHorizontalAdapter<String> hAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.car_activity_car_edit);
		initView();
	}

	private void initView() {
		hListView = (HorizontalListView) findViewById(R.id.hlv_car_imgs);
		hAdapter = new CarHorizontalAdapter<String>(this);
		List<String> list = new ArrayList<String>();
		list.add("");
		list.add("");
		list.add("");
		hAdapter.setList(list);
		hListView.setAdapter(hAdapter);
	}
}
