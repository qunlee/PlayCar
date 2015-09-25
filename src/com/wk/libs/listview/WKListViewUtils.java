package com.wk.libs.listview;

import java.util.ArrayList;

import com.playcar.R;
import com.wk.libs.WKApplication;
import com.wk.libs.beans.*;
import com.wk.libs.listview.PullToRefreshBase.*;
import com.wk.libs.net.WKAsyncTask;
import com.wk.libs.utils.WKTimeUtils;
import com.wk.libs.utils.WKUtils;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

/**
 * Listview列表工具
 * 
 * T:Item的json bean,如 StoreGsonBean
 * 
 * V:接口返回的json bean,如 StoreListBean
 */
public abstract class WKListViewUtils<T, V extends BaseListBean> implements
		OnLastItemVisibleListener, OnRefreshListener2<ListView> {

	public WKListViewInterface listClass = null;
	public PullToRefreshListView pullRefreshListView;
	public ListView listView;
	public WKAdapter adapter;
	public ArrayList<T> list = new ArrayList<T>();// 列表数据
	public IBaseBean<V> bean = null;// 接口返回的数据
	/**
	 * 插一句，有的服务器喜欢用curIdx，有的喜欢用page；
	 * 
	 * curIndex每次增长pageCount，page每次增长1
	 */
	public int curIdx = 0;// 当前索引
	public int pageCount = 50;// 每页条数
	public int page = 1;// 当前页
	public boolean showLoading = false;// 获取网络数据时，是否显示进度框
	private boolean isBusy = false;// 是否正在请求数据
	private Context ctx;// 上下文

	public static String LOAD_COMPLETE = "已加载完最后一页";// 加载完成提示信息
	public static String NO_NET = "请检查下网络配置";

	public static boolean isShowLastUpdateTime = true;// 是否显示上次更新时间

	private String localResult = null;// 本地数据
	public boolean isLoadCacheOK = false;// 用来标记是否已经读取完本地数据，每次都读取本地数据

	public View header = null;// list的header

	public boolean checkEnd = true;// 是否监测列表有数据

	public static CheckResultOK checkResult = null;// 检测方法

	/**
	 * 设置检测result方法
	 */
	public static void setCheck(CheckResultOK check) {
		checkResult = check;
	}

	/**
	 * 强制刷新本地
	 */
	public void forceUpdate() {
		isLoadCacheOK = false;
	}

	/**
	 * 初始化,
	 */
	public void init(Context ctx, WKListViewInterface listClass) {
		this.ctx = ctx;
		init(ctx, listClass, createListView());
	}

	/**
	 * 初始化，需要传入listview
	 * 
	 * @param ctx
	 * @param pullRefreshListView
	 */
	public void init(Context ctx, WKListViewInterface listClass,
			PullToRefreshListView pullRefreshListView, View header) {
		this.header = header;
		this.ctx = ctx;
		this.listClass = listClass;
		this.pullRefreshListView = pullRefreshListView;
		listView = pullRefreshListView.getRefreshableView();
		if (header != null) {
			this.listView.addHeaderView(header);
		}
		adapter = new WKAdapter(ctx);
		listView.setAdapter(adapter);
		pullRefreshListView.setMode(Mode.BOTH);
		pullRefreshListView.setOnRefreshListener(this);
		pullRefreshListView.setOnLastItemVisibleListener(this);
	}

	/**
	 * 初始化，需要传入listview
	 * 
	 * @param ctx
	 * @param pullRefreshListView
	 */
	public void init(Context ctx, WKListViewInterface listClass,
			PullToRefreshListView pullRefreshListView) {
		this.ctx = ctx;
		this.listClass = listClass;
		this.pullRefreshListView = pullRefreshListView;
		listView = pullRefreshListView.getRefreshableView();
		if (header != null) {
			this.listView.addHeaderView(header);
		}
		adapter = new WKAdapter(ctx);
		listView.setAdapter(adapter);
		pullRefreshListView.setMode(Mode.BOTH);
		pullRefreshListView.setOnRefreshListener(this);
		pullRefreshListView.setOnLastItemVisibleListener(this);
	}

	/**
	 * 获取app
	 */
	public WKApplication app() {
		return (WKApplication) ctx.getApplicationContext();
	}

	/**
	 * 设置读取数据时，是否显示进度圈
	 */
	public void setShowLoading(boolean showLoading) {
		this.showLoading = showLoading;
	}

	/**
	 * 检测是否初始化OK
	 */
	private boolean checkInitOK() {
		if (listClass == null) {
			WKApplication.showToast("请初始化listClass");
		}
		return true;
	}

	/**
	 * 检测是否有网络
	 */
	public boolean checkNetIsOK() {
		if (WKUtils.isNetworkAvailable(ctx) == false) {
			WKApplication.showToast("" + NO_NET);
			return false;
		}
		return true;
	}

	/**
	 * 加载完成
	 */
	public void onLastItemVisible() {
		if (checkEnd) {
			checkIsEnd();
		}
	}

	/**
	 * 检查是否是最后页
	 */
	public void checkIsEnd() {
		try {
			if (list != null && bean != null && bean.data != null
					&& list.size() == bean.data.totalcount) {
				pullRefreshListView.setMode(Mode.PULL_FROM_START);
				WKApplication.showToast("" + LOAD_COMPLETE);
			} else {
				pullRefreshListView.setMode(Mode.BOTH);
			}
		} catch (Exception e) {
			pullRefreshListView.setMode(Mode.BOTH);
			e.printStackTrace();
		}
	}

	/**
	 * 更新时间
	 * 
	 * @param refreshView
	 * @param downOrUp
	 */
	private void updateTimeInfo(boolean isPullDown) {
		if (isShowLastUpdateTime == false) {
			return;
		}
		String label = WKTimeUtils.timeForListviewUpdate() + "更新";
		pullRefreshListView.getLoadingLayoutProxy(true, false)
				.setLastUpdatedLabel(label);
	}

	/**
	 * 下拉更新数据
	 */
	public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
		updateData();
	}

	/**
	 * 更新数据
	 */
	public void updateData() {
		// 无网络情况下要加载缓存
		if (!checkNetIsOK()) {
			pullRefreshListView.onRefreshComplete();
			if (isLoadCacheOK == false) {
				new GetListTask(ctx, true, showLoading).execute();
			}
			return;
		}
		if (!checkNetIsOK() || !checkInitOK() || isBusy) {
			pullRefreshListView.onRefreshComplete();
			return;
		}
		updateTimeInfo(true);
		isBusy = true;
		curIdx = 0;
		page = 1;
		new GetListTask(ctx, true, showLoading).execute();
	}

	/**
	 * 上拉加载更多
	 */
	public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
		if (!checkNetIsOK() || !checkInitOK() || isBusy) {
			pullRefreshListView.onRefreshComplete();
			return;
		}
		isBusy = true;
		curIdx += pageCount;
		page++;
		new GetListTask(ctx, false, showLoading).execute();
	}

	/**
	 * 适配器
	 */
	public class WKAdapter extends WKBaseAdapter {
		public WKAdapter(Context ctx) {
			super(ctx);
		}

		public int getCount() {
			if (list == null)
				return 0;
			return list.size();
		}

		@SuppressWarnings("unchecked")
		public View getView(int arg0, View arg1, ViewGroup arg2) {
			if (arg1 == null) {
				arg1 = View.inflate(ctx, listClass.getItemViewId(), null);
				holder = listClass.createViewHolder(arg1);
				arg1.setTag(holder);
			} else {
				holder = (WKViewHolder) arg1.getTag();
			}
			// 更新数据
			holder.setData(list.get(arg0));
			return arg1;
		}
	}

	/**
	 * 获取数据
	 */
	class GetListTask extends WKAsyncTask<String, String, String> {

		boolean clearList = false;

		public GetListTask(Context context, boolean clearList,
				boolean showLoading) {
			super(context);
			this.clearList = clearList;
			this.showLoading = showLoading;
			// 如果有数据，就不用显示进度圈了
			if (list != null && list.size() > 0) {
				this.showLoading = false;
			}
		}

		@Override
		protected String doInBackground(String... params) {
			return listClass.getData(!isLoadCacheOK);
		}

		@Override
		protected void doOnPostExecute(String result) {
			isBusy = false;
			pullRefreshListView.onRefreshComplete();
			if (isLoadCacheOK == false) {// 本地
				isLoadCacheOK = true;
				localResult = result;
				if (clearList) {// 是否需要清空列表
					list.clear();
				}
				if (result != null && !result.equals("")) {
					listClass.getDataBack(result, true);
				}
				// 本地拿完了再从服务器拿
				updateData();

			} else {// 不是本地
				// 本地为空，本地和服务器数据不相同，
				if (localResult == null || !localResult.equals(result)) {
					// 楼下这句不适应所有情况，到时候会抽出一个方法来
					// if(!result.contains("error")&&result.contains("success"))
					// {
					if (checkResult.checkResultOK(result)) {
						localResult = result;
						if (clearList) {// 是否需要清空列表
							list.clear();
						}
						listClass.getDataBack(result, false);
						// System.out.println("lazy list need update");
					}
				} else {
					// System.out.println("lazy list no update");
				}
			}
		}
	}

	/**
	 * 创建一个listview，共Fragment使用。
	 */
	public PullToRefreshListView createListView() {
		View v = View.inflate(ctx, R.layout.wk_list_view, null);
		PullToRefreshListView pullList = (PullToRefreshListView) v
				.findViewById(R.id.wk_pull_refresh_list);
		((ViewGroup) v).removeView(pullList);
		return pullList;
	}

	public void onResume() {
		if (adapter != null) {
			adapter.notifyDataSetChanged();
		}
	}

	/**
	 * Add Sound Event Listener
	 */
	public void addSound() {
		// SoundPullEventListener<ListView> soundListener = new
		// SoundPullEventListener<ListView>(
		// ctx);
		// soundListener.addSoundEvent(State.PULL_TO_REFRESH,
		// R.raw.wk_ptr_pull_event);
		// soundListener.addSoundEvent(State.RESET, R.raw.wk_ptr_reset_sound);
		// soundListener.addSoundEvent(State.REFRESHING,
		// R.raw.wk_ptr_refreshing_sound);
		// pullRefreshListView.setOnPullEventListener(soundListener);
	}

	/**
	 * 检测result方法
	 */
	public interface CheckResultOK {
		public boolean checkResultOK(String result);
	}
}
