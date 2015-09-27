package com.playcar.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ExpandableListView.OnGroupClickListener;
import android.widget.Toast;

import com.playcar.R;
import com.playcar.activity.CarNearActivity;
import com.playcar.bean.GroupChildBean;
import com.playcar.bean.GroupsBean;
import com.playcar.bean.TrandsBean;
import com.playcar.bean.GroupsBean.GroupBean;
import com.playcar.bean.TrandsBean.Trands;
import com.playcar.fragment.CarNearTrandsFragment.MyViewHolder;
import com.wk.libs.listview.PullToRefreshListView;
import com.wk.libs.listview.WKListViewInterface;
import com.wk.libs.listview.WKListViewUtils;
import com.wk.libs.listview.WKViewHolder;

/**
 * Created by Aure on 15/9/23.
 */
public class CarNearGroupFragment extends Fragment {

	// 这个数组是用来存储一级item的点击次数的，根据点击次数设置一级标签的选中、为选中状态
	private int[] group_checked = new int[] { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
			0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
	// 用来标识是否设置二級item背景色为绿色,初始值为-1既为选中状态
	private int child_groupId = -1;
	private int child_childId = -1;

	public GroupsBean groupsBean = new GroupsBean();

	private Activity mActivity;

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		this.mActivity = activity;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

	}

	// 初始化ExpandListView
	private void initList() {
		ExpandableListView expandableListView = (ExpandableListView) view
				.findViewById(R.id.list);
		// 设置默认图标为不显示状态
		expandableListView.setGroupIndicator(null);
		// 为列表绑定数据源
		expandableListView.setAdapter(adapter);
		// 设置一级item点击的监听器
		expandableListView.setOnGroupClickListener(new OnGroupClickListener() {

			@Override
			public boolean onGroupClick(ExpandableListView parent, View v,
					int groupPosition, long id) {
				group_checked[groupPosition] = group_checked[groupPosition] + 1;
				// 刷新界面
				((BaseExpandableListAdapter) adapter).notifyDataSetChanged();
				return false;
			}
		});

		// 设置二级item点击的监听器
		expandableListView.setOnChildClickListener(new OnChildClickListener() {

			@Override
			public boolean onChildClick(ExpandableListView parent, View v,
					int groupPosition, int childPosition, long id) {
				// 将被点击的一丶二级标签的位置记录下来
				 child_groupId = groupPosition;
				 child_childId = childPosition;
				// 刷新界面
				((BaseExpandableListAdapter) adapter).notifyDataSetChanged();
				return false;
			}
		});
	}

	View view;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.car_near_group_fragment, container,
				false);
		GroupBean groupBean;
		for (int i = 0; i < 3; i++) {
			groupBean = groupsBean.new GroupBean();

			GroupChildBean childBean;
			for (int j = 0; j < 2; j++) {
				childBean = new GroupChildBean();
				groupBean.childList.add(childBean);
			}
			groupsBean.groupList.add(groupBean);
		}
		initList();
		return view;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		final CarNearActivity activity = ((CarNearActivity) getActivity());
		View fragmentView = activity.fragments.get(2).getView();
	}

	final ExpandableListAdapter adapter = new BaseExpandableListAdapter() {
		// 一级标签上的logo图片数据源
		// 一级标签上的标题数据源_groupsBean
		// 子视图显示文字
		// private String[][] child_text_array = new String[][] {
		// { "是否经常感到左臂疼痛？", "是否经常熬夜？", "您的踝关节有刺痛的现象吗？", "是否经常用凉水洗头？" },
		// { "是否经常感到左臂疼痛？", "是否经常熬夜？", "您的踝关节有刺痛的现象吗？", "是否经常用凉水洗头？" },
		// { "是否经常感到左臂疼痛？", "是否经常熬夜？", "您的踝关节有刺痛的现象吗？", "是否经常用凉水洗头？" },
		// { "是否经常感到左臂疼痛？", "是否经常熬夜？", "您的踝关节有刺痛的现象吗？", "是否经常用凉水洗头？" } };
		// 一级标签上的状态图片数据源
		int[] group_state_array = new int[] { R.drawable.car_group_point1,
				R.drawable.car_group_point2 };

		// 重写ExpandableListAdapter中的各个方法
		/**
		 * 获取一级标签总数
		 */
		@Override
		public int getGroupCount() {
			return groupsBean.groupList.size();
		}

		/**
		 * 获取一级标签内容
		 */
		@Override
		public Object getGroup(int groupPosition) {
			return groupsBean.groupList.get(groupPosition);
		}

		/**
		 * 获取一级标签的ID
		 */
		@Override
		public long getGroupId(int groupPosition) {
			return groupPosition;
		}

		/**
		 * 获取一级标签下二级标签的总数
		 */
		@Override
		public int getChildrenCount(int groupPosition) {
			return groupsBean.groupList.get(groupPosition).childList.size();
		}

		/**
		 * 获取一级标签下二级标签的内容
		 */
		@Override
		public Object getChild(int groupPosition, int childPosition) {
			return groupsBean.groupList.get(groupPosition).childList
					.get(childPosition);
		}

		/**
		 * 获取二级标签的ID
		 */
		@Override
		public long getChildId(int groupPosition, int childPosition) {
			return childPosition;
		}

		/**
		 * 指定位置相应的组视图
		 */
		@Override
		public boolean hasStableIds() {
			return true;
		}

		/**
		 * 对一级标签进行设置
		 */
		@Override
		public View getGroupView(int groupPosition, boolean isExpanded,
				View convertView, ViewGroup parent) {
			// 为视图对象指定布局
			convertView = (LinearLayout) LinearLayout.inflate(mActivity,
					R.layout.car_near_group_parent, null);

			RelativeLayout myLayout = (RelativeLayout) convertView
					.findViewById(R.id.group_layout);
			View view = (View) convertView.findViewById(R.id.group_divider);
			view.setVisibility(View.VISIBLE);
			if (groupPosition == 0) {
				view.setVisibility(View.GONE);
			}

			/**
			 * 声明视图上要显示的控件
			 */
			// 新建一个TextView对象，用来显示一级标签上的标题信息
			TextView group_title = (TextView) convertView
					.findViewById(R.id.group_title);
			// 新建一个TextView对象，用来显示一级标签上的大体描述的信息
			ImageView group_state = (ImageView) convertView
					.findViewById(R.id.group_state);
			TextView group_distance = (TextView) convertView
					.findViewById(R.id.group_distance);
			/**
			 * 设置相应控件的内容
			 */
			// 设置标题上的文本信息
			// GroupBean groupBean = groupsBean.groupList.get(groupPosition);
			// group_title.setText(groupBean.address + "(" + groupBean.count +
			// ")");
			// group_distance.setText(groupBean.distance + "千米");
			// 设置整体描述上的文本信息

			if (group_checked[groupPosition] % 2 == 1) {
				// 设置默认的图片是选中状态
				group_state.setImageResource(group_state_array[1]);
				// myLayout.setBackgroundResource(R.drawable.text_item_top_bg);
			} else {
				for (int test : group_checked) {
					if (test == 0 || test % 2 == 0) {
						// myLayout.setBackgroundResource(R.drawable.text_item_bg);
						// 设置默认的图片是未选中状态
						group_state.setImageResource(group_state_array[0]);
					}
				}
			}
			// 返回一个布局对象
			return convertView;
		}

		/**
		 * 对一级标签下的二级标签进行设置
		 */
		@Override
		public View getChildView(int groupPosition, int childPosition,
				boolean isLastChild, View convertView, ViewGroup parent) {

			// 为视图对象指定布局
			convertView = (LinearLayout) LinearLayout.inflate(mActivity,
					R.layout.car_near_group_child, null);
			/**
			 * 声明视图上要显示的控件
			 */
			// 新建一个TextView对象，用来显示具体内容
			
			/**
			 * 设置相应控件的内容
			 */
			// 设置要显示的文本信息
//			GroupChildBean childBean = groupsBean.groupList.get(groupPosition).childList.get(childPosition);
			
			// 判断item的位置是否相同，如相同，则表示为选中状态，更改其背景颜色，如不相同，则设置背景色为白色
			if (child_groupId == groupPosition
					&& child_childId == childPosition) {
				// 设置背景色为绿色
				// convertView.setBackgroundColor(Color.GREEN);
			} else {
				// 设置背景色为白色
				// convertView.setBackgroundColor(Color.WHITE);
			}
			// 返回一个布局对象
			return convertView;
		}

		/**
		 * 当选择子节点的时候，调用该方法
		 */
		@Override
		public boolean isChildSelectable(int groupPosition, int childPosition) {
			Toast.makeText(mActivity, groupPosition + " : " + childPosition, Toast.LENGTH_SHORT).show();
			return true;
		}

	};

	@Override
	public void onStart() {
		super.onStart();
	}

	@Override
	public void onResume() {
		super.onResume();
	}

	@Override
	public void onPause() {
		super.onPause();
	}

	@Override
	public void onStop() {
		super.onStop();
	}

	@Override
	public void onDestroyView() {
		super.onDestroyView();
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
	}

	@Override
	public void onDetach() {
		super.onDetach();
	}

}
