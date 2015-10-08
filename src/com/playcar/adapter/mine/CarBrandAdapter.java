package com.playcar.adapter.mine;

import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SectionIndexer;
import android.widget.TextView;

import com.playcar.R;
import com.playcar.util.ViewHolder;
import com.playcar.view.carlist.GroupMemberBean;

public class CarBrandAdapter extends AdapterManager<GroupMemberBean> implements SectionIndexer {

	public CarBrandAdapter(Context mContext, List<GroupMemberBean> list) {
		super(list, mContext);
	}
	public View getView(final int position, View view, ViewGroup arg2) {
		if (view == null) {
			view = this.inflater.inflate(R.layout.activity_group_member_item, null);
		}
		TextView tvTitle = ViewHolder.get(view, R.id.title);
		TextView tvBank = ViewHolder.get(view, R.id.catalog);
		final GroupMemberBean mContent = list.get(position);
		// 根据position获取分类的首字母的Char ascii值
		int section = getSectionForPosition(position);
		// 如果当前位置等于该分类首字母的Char的位置 ，则认为是第一次出现
		if (position == getPositionForSection(section)) {
			tvBank.setVisibility(View.VISIBLE);
			tvBank.setText(mContent.getSortLetters());
		} else {
			tvBank.setVisibility(View.GONE);
		}
		tvTitle.setText(this.list.get(position).getName());
		return view;

	}

	/**
	 * 根据ListView的当前位置获取分类的首字母的Char ascii值
	 */
	public int getSectionForPosition(int position) {
		return list.get(position).getSortLetters().charAt(0);
	}

	/**
	 * 根据分类的首字母的Char ascii值获取其第一次出现该首字母的位置
	 */
	public int getPositionForSection(int section) {
		for (int i = 0; i < getCount(); i++) {
			String sortStr = list.get(i).getSortLetters();
			char firstChar = sortStr.toUpperCase().charAt(0);
			if (firstChar == section) {
				return i;
			}
		}

		return -1;
	}

	@Override
	public Object[] getSections() {
		return null;
	}
}
