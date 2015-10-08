package com.playcar.activity.mine;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;

import com.playcar.R;

/**
 * 我的职业
 * @author Administrator
 *
 */
public class CarMyProfessionActivity extends CarMineBaseActivity {

	private ListView listView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.car_activity_my_profession);
		initView();
	}

	private void initView() {
		listView = (ListView) findViewById(R.id.lv_my_professions);
		MyAdapter adapter = new MyAdapter(this);
		listView.setAdapter(adapter);
	}

	class MyAdapter extends BaseAdapter {

		private Context context;
		private LayoutInflater inflater;

		public MyAdapter(Context context) {
			this.context = context;
			inflater = LayoutInflater.from(context);
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return 5;
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			if (convertView == null) {
				convertView = inflater.inflate(R.layout.car_listitem_my_profession, null);
			}
			return convertView;
		}
	}
}
