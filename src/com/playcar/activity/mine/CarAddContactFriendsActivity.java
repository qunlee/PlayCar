package com.playcar.activity.mine;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.widget.ListView;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.playcar.R;
import com.playcar.adapter.mine.AddContactFriendsAdapter;

/**
 * 添加通讯录好友
 */
public class CarAddContactFriendsActivity extends CarMineBaseActivity{
	@ViewInject(R.id.listView)
	private ListView listView;
	private AddContactFriendsAdapter addContactFriendsAdapter;
	private List<String> list = new ArrayList<String>();
	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setContentView(R.layout.car_add_contact_friends_activity);
		initializeHead("", "添加通讯录好友", "添加(1)");
		ViewUtils.inject(this);
		setAdapter();
	}
	private void setAdapter(){
		if(addContactFriendsAdapter == null){
			list.add("");
			list.add("");
			addContactFriendsAdapter = new AddContactFriendsAdapter(list, this);
			listView.setAdapter(addContactFriendsAdapter);
		}
		addContactFriendsAdapter.notifyDataSetChanged();
	}
}
