package com.playcar.activity.trands;

import com.playcar.R;
import com.playcar.bean.LocalsBean;
import com.playcar.bean.LocalsBean.LocalBean;
import com.playcar.bean.ReviewsBean;
import com.playcar.bean.ReviewsBean.ReviewBean;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

/**
 * 所在位置选择列表
 * 
 * @author qunlee_mr
 *
 */
public class ActivityLocal extends Activity implements OnClickListener,
		OnItemClickListener {

	private ListView local_list;
	private AdapterLocal adapter;
	private LocalsBean localsBean = new LocalsBean();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		setContentView(R.layout.car_local_list);

		local_list = (ListView) findViewById(R.id.review_list);

		initData();
		adapter = new AdapterLocal(this, localsBean);
		local_list.setAdapter(adapter);
		local_list.setOnItemClickListener(this);
		adapter.notifyDataSetChanged();
	}

	private void initData() {
		LocalBean localBean;
		for (int i = 0; i < 4; i++) {
			localBean = localsBean.new LocalBean();

			localBean.selected = false;

			localsBean.locals.add(localBean);
		}
	}

	@Override
	public void onClick(View v) {
		int id = v.getId();
		switch (id) {
		case R.id.back_btn:
			// 返回
			finish();
			break;

		default:
			break;
		}
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		for (int i = 0; i < localsBean.locals.size(); i++) {
			localsBean.locals.get(position).selected = false;
		}
		localsBean.locals.get(position).selected = true;
		adapter.notifyDataSetChanged();

		new Handler().postDelayed(new Runnable() {
			@Override
			public void run() {
				Intent it = new Intent();  
                it.putExtra("address", "杭州");  
                setResult(Activity.RESULT_OK, it);  
				finish();
			}
		}, 1000);
	}
}
