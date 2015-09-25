package com.wqdsoft.im;

import android.app.Dialog;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.RelativeLayout.LayoutParams;

import com.playcar.R;
import com.wqdsoft.im.DB.DBHelper;
import com.wqdsoft.im.DB.MessageTable;
import com.wqdsoft.im.Entity.MessageInfo;
import com.wqdsoft.im.Entity.IMJiaState;
import com.wqdsoft.im.global.FeatureFunction;
import com.wqdsoft.im.global.GlobalParam;
import com.wqdsoft.im.global.IMCommon;
import com.wqdsoft.im.net.IMException;

/**
 * 忘记密码
 * @author dongli
 *
 */
public class ForgetPwdActity extends BaseActivity {

	private Button mOkBtn;
	private String mInputPhone;
	private Dialog  mDialog;

	/*
	 * 处理消息
	 */
	private Handler mHandler = new Handler(){

		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			switch (msg.what) {
			case GlobalParam.MSG_CHECK_STATE:
				IMJiaState state = (IMJiaState)msg.obj;
				if(state == null ||state.code!=0 ){
					Toast.makeText(mContext, R.string.commit_data_error,Toast.LENGTH_LONG).show();
					return;
				}
				createDialog(mContext);

				break;

			default:
				break;
			}
		}

	};

	/*
	 * 导入控件
	 * (non-Javadoc)
	 * @see com.wqdsoft.im.BaseActivity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.forget_pwd);
		mContext = this;
		initCompent();
	}

	/*
	 * 实例化控件
	 */
	private void initCompent(){
		setTitleContent(R.drawable.back_btn,0,R.string.find_pwd);
		mLeftBtn.setOnClickListener(this);
		mOkBtn = (Button)findViewById(R.id.ok_btn);
		mOkBtn.setOnClickListener(this);
	}

	/*
	 * 查找密码
	 */
	private void findPwd(){
		if(!IMCommon.getNetWorkState()){
			mBaseHandler.sendEmptyMessage(BASE_MSG_NETWORK_ERROR);
			return;
		}
		new Thread(){
			public void run() {
				try {
					IMCommon.sendMsg(mBaseHandler, BASE_SHOW_PROGRESS_DIALOG, "数据提交中,请稍后...");
					IMJiaState state = IMCommon.getIMInfo().findPwd(mInputPhone);
					mBaseHandler.sendEmptyMessage(BASE_HIDE_PROGRESS_DIALOG);
					IMCommon.sendMsg(mHandler, GlobalParam.MSG_CHECK_STATE,state);
				} catch (IMException e) {
					e.printStackTrace();
					IMCommon.sendMsg(mBaseHandler, BASE_MSG_TIMEOUT_ERROR,
							mContext.getResources().getString(e.getStatusCode()));
				}catch (Exception e) {
					e.printStackTrace();
					mBaseHandler.sendEmptyMessage(BASE_HIDE_PROGRESS_DIALOG);
				}
			};
		}.start();
	}

	/*
	 * 按钮点击事件
	 * (non-Javadoc)
	 * @see com.wqdsoft.im.BaseActivity#onClick(android.view.View)
	 */
	@Override
	public void onClick(View v) {
		super.onClick(v);
		switch (v.getId()) {
		case R.id.left_btn:
			ForgetPwdActity.this.finish();
			break;
		case R.id.ok_btn:
		
			break;

		default:
			break;
		}
	}


	private void createDialog(Context context) {
		mDialog = new Dialog (context,R.style.dialog);
		LayoutInflater factor = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View serviceView = factor.inflate(R.layout.normal_hint_dialog, null);

		mDialog.setContentView(serviceView);
		mDialog.show();
		mDialog.setCancelable(false);	
		mDialog.getWindow().setLayout(LayoutParams.WRAP_CONTENT
				/*mContext.getResources().getDimensionPixelSize(R.dimen.bind_phone_height)*/
				, LayoutParams.WRAP_CONTENT);


		TextView chatContent=(TextView) serviceView
				.findViewById(R.id.card_title);
		chatContent.setText("新密码已发送到你的手机上，请注意查收！");

		Button okBtn=(Button)serviceView.findViewById(R.id.yes);

		okBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if(mDialog!=null){
					mDialog.dismiss();
					mDialog = null;
				}
				ForgetPwdActity.this.finish();
			}
		});

	}


}
