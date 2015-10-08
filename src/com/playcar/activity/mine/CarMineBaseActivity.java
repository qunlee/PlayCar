package com.playcar.activity.mine;

import android.view.View;
import android.view.View.OnClickListener;

import com.playcar.R;
import com.playcar.activity.mine.imp.MenuStrategy;
import com.playcar.view.HeadView;
import com.wk.libs.listview.PullToRefreshAdapterViewBase;
import com.wqdsoft.im.BaseActivity;

public abstract class CarMineBaseActivity extends BaseActivity implements OnClickListener{
	protected HeadView headView;
	private MenuStrategy menuStrategy;
	
	public void setMenuStrategy(MenuStrategy menuStrategy) {
		this.menuStrategy = menuStrategy;
	}
	protected void initializeHead(String backValue ,String title , String menuValue){
		headView = (HeadView) findViewById(R.id.headView);
		headView.onclickBack(this);
		headView.onclickRight(this);
		headView.setBackValue(backValue);
		headView.setTitle(title);
		headView.setRightValue(menuValue);
	}
	/**
	 * 为PullToRefreshGrideView设置属性
	 */
	public void setPullToRefreshAttribute(PullToRefreshAdapterViewBase<?> mPullRefreshGridView) {
		mPullRefreshGridView.setPullToRefreshOverScrollEnabled(false);
		mPullRefreshGridView.getLoadingLayoutProxy(false, true).setRefreshingLabel("正在加载");
		mPullRefreshGridView.getLoadingLayoutProxy(false, true).setPullLabel("加载更多");
		mPullRefreshGridView.getLoadingLayoutProxy(false, true).setReleaseLabel("放开加载");

		mPullRefreshGridView.getLoadingLayoutProxy(true, false).setRefreshingLabel("正在加载");
		mPullRefreshGridView.getLoadingLayoutProxy(true, false).setPullLabel("下拉刷新");
		mPullRefreshGridView.getLoadingLayoutProxy(true, false).setReleaseLabel("放开刷新");
	}
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.txt_back:
			this.finish();
			break;
		case R.id.txt_right:
			if(menuStrategy != null){
				menuStrategy.openActivity();
			}
			break;

		default:
			break;
		}
	}
	
}
