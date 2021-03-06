package com.wqdsoft.im;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.playcar.R;
import com.wqdsoft.im.global.FeatureFunction;
import com.wqdsoft.im.map.BMapApiApp;

/**
 * 关于我们页面
 * @author dongli
 *
 */
public class AboutActivity extends BaseActivity implements OnClickListener{

	/**
	 * 定义全局变量
	 */
	private TextView mVersionText, mWebSiteHint, mCopyRight;
	
	/**
	 * 导入控件
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.about_page);
		mContext = this;
		initComponent();
	}
	
	/**
	 * 实例化控件
	 */
	private void initComponent(){
		setTitleContent(R.drawable.back_btn, 0, R.string.about_us);
		mLeftBtn.setOnClickListener(this);
		
		mVersionText = (TextView) findViewById(R.id.version);
		mVersionText.setText(AboutActivity.this.getString(R.string.ochat_app_name) + FeatureFunction.getAppVersionName(AboutActivity.this));
		
		mWebSiteHint = (TextView) findViewById(R.id.website_hint);
		mWebSiteHint.setText(BMapApiApp.getInstance().getResources().getString(R.string.visit_website));
		
		mCopyRight = (TextView) findViewById(R.id.copyright);
		mCopyRight.setText(BMapApiApp.getInstance().getResources().getString(R.string.copyright));
	
	}

	/**
	 * 按钮点击事件
	 */
	@Override
	public void onClick(View v) {
		super.onClick(v);
		switch (v.getId()) {
		case R.id.left_btn:
			this.finish();
			break;

		default:
			break;
		}
	}
}
