package com.wqdsoft.im;

import java.io.File;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.playcar.R;
import com.wqdsoft.im.Entity.Login;
import com.wqdsoft.im.Entity.LoginResult;
import com.wqdsoft.im.dialog.MMAlert;
import com.wqdsoft.im.dialog.MMAlert.OnAlertSelectId;
import com.wqdsoft.im.global.FeatureFunction;
import com.wqdsoft.im.global.GlobalParam;
import com.wqdsoft.im.global.GlobleType;
import com.wqdsoft.im.global.ImageLoader;
import com.wqdsoft.im.global.IMCommon;
import com.wqdsoft.im.net.IMException;

public class EditProfileActivity extends BaseActivity implements OnClickListener{

	/*
	 * 定义全局变量
	 */
	private static final String TEMP_FILE_NAME = "header.jpg";
	private RelativeLayout mHeaderLayout,mAddrLayout,mSexLayout,
	mSignLayout,mNickNameLayout;

	private TextView mSexTextView,mAddrTextView,mSiTextView,
	mNickNameTextView;
	private TextView mHintText;
	private ImageView mImageView;

	private String mInputNickName,mInputAddr,mInputSign;
	private int mInputSex = 2;
	
	/**
	 * // 省id
	 */
	private String mProvice;
	/**
	 * //市id
	 */
	private String mCity;

	private int mType;

	private Login mLogin;
	private Bitmap mBitmap;
	private String mImageFilePath;
	private String mHeadUrl;
	private ImageLoader mImageLoader;

	/*
	 * 处理消息
	 */
	private Handler mHandler = new Handler(){

		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			switch (msg.what) {
			case GlobalParam.MSG_CHECK_STATE:
				LoginResult loginResult = (LoginResult) msg.obj;
				if (loginResult == null) {
					Toast.makeText(mContext, "提交数据失败...", Toast.LENGTH_LONG)
					.show();
					return;
				}
				if (loginResult.mState.code != 0) {
					Toast.makeText(mContext, loginResult.mState.errorMsg,
							Toast.LENGTH_LONG).show();
					return;
				}
				Login login = loginResult.mLogin;
				login.password = IMCommon.getLoginResult(mContext).password;
				String oldheadUrl = IMCommon.getLoginResult(mContext).headsmall;
				String newHeadUrl = login.headsmall;
				IMCommon.saveLoginResult(mContext, login);
				setResult(RESULT_OK);
				Intent intent = new Intent(GlobalParam.ACTION_REFRESH_CHAT_HEAD_URL);
				intent.putExtra("oldurl", oldheadUrl);
				intent.putExtra("newurl", newHeadUrl);
				sendBroadcast(intent);
				EditProfileActivity.this.finish();
				/*reSearchState state = (reSearchState)msg.obj;
				if(state == null || state.equals("")){
					Toast.makeText(mContext, R.string.commit_data_error,Toast.LENGTH_LONG).show();
					return;
				}
				if(state.code == 0){

				}else{
					Toast.makeText(mContext, state.errorMsg, Toast.LENGTH_LONG).show();
				}
				break;*/
			case GlobalParam.MSG_SHOW_LOAD_DATA:
				if(mInputAddr != null && !mInputAddr.equals("")){
					mAddrTextView.setText(mInputAddr);
				}
				
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
		setContentView(R.layout.complete_user_info);
		mContext = this;
		mLogin = IMCommon.getLoginResult(mContext); 
		mImageLoader = new ImageLoader();
		initCompent();


	}

	/*
	 * 实例化控件
	 */
	private void initCompent(){
		setTitleContent(R.drawable.back_btn, R.drawable.ok_btn, R.string.edit_profile);
		mLeftBtn.setOnClickListener(this);
		mRightBtn.setOnClickListener(this);

		mHeaderLayout = (RelativeLayout)findViewById(R.id.new_header_layout);
		mNickNameLayout = (RelativeLayout)findViewById(R.id.nickname_layout);
		mAddrLayout = (RelativeLayout)findViewById(R.id.addr_layout);
		mSexLayout = (RelativeLayout)findViewById(R.id.sex_layout);
		mSignLayout = (RelativeLayout)findViewById(R.id.sign_layout);
		mHeaderLayout.setOnClickListener(this);
		mNickNameLayout.setOnClickListener(this);
		mAddrLayout.setOnClickListener(this);
		mSexLayout.setOnClickListener(this);
		mSignLayout.setOnClickListener(this);
		

		mNickNameTextView = (TextView)findViewById(R.id.nickname_content);
		mSexTextView = (TextView)findViewById(R.id.sex_content);
		mAddrTextView = (TextView)findViewById(R.id.addr_content);
		mSiTextView = (TextView)findViewById(R.id.sign_content);
		

		mImageView = (ImageView)findViewById(R.id.new_header_icon);
		setText();

	}

	/*
	 * 给控件设置值
	 */
	private void setText(){
		if(mLogin == null || mLogin.equals("")){
			return;
		}
		mHeadUrl = mLogin.headsmall;
		if(mLogin.headsmall!=null && !mLogin.headsmall.equals("")){
			mImageView.setTag(mLogin.headsmall);
			mImageLoader.getBitmap(mContext, mImageView, null,mLogin.headsmall,0,false,true);
		}
		mInputSex = mLogin.gender;
		if(mLogin.gender == 0){
			mSexTextView.setText(mContext.getResources().getString(R.string.man));
		}else if(mLogin.gender == 1){
			mSexTextView.setText(mContext.getResources().getString(R.string.femal));
		}else if(mLogin.gender == 2){
			mSexTextView.setText(mContext.getResources().getString(R.string.no_limit));
		}
		
		mInputNickName = mLogin.nickname;
		mNickNameTextView.setText(mInputNickName+" ");
		
		mInputSign = mLogin.sign;
		mSiTextView.setText(mInputSign+" ");
		
		mProvice = mLogin.provinceid;
		mCity = mLogin.cityid;
		mAddrTextView.setText(mProvice+"  "+mCity+" ");
	}
	
	/*
	 * 完善用户资料
	 */
	private void completeUserInfo(){
		if(!IMCommon.getNetWorkState()){
			mBaseHandler.sendEmptyMessage(BASE_MSG_NETWORK_ERROR);
		}
		new Thread(){
			public void run() {

				try {
					IMCommon.sendMsg(mBaseHandler, BASE_SHOW_PROGRESS_DIALOG,
							mContext.getResources().getString(R.string.commit_dataing));
					LoginResult login = IMCommon.getIMInfo().modifyUserInfo(mImageFilePath,mInputNickName,
							mInputSex,mInputSign,mProvice, mCity);
					IMCommon.sendMsg(mHandler, GlobalParam.MSG_CHECK_STATE, login);
					mBaseHandler.sendEmptyMessage(BASE_HIDE_PROGRESS_DIALOG);
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
			this.finish();
			break;
		case R.id.right_btn:
			/*if(mHeadUrl == null || mHeadUrl.equals("") ){
				if((mImageFilePath == null || mImageFilePath.equals(""))){
					Toast.makeText(mContext, mContext.getResources().getString(R.string.head_url_null),Toast.LENGTH_LONG).show();
					return;
				}
			}*/
			if(mInputNickName == null || mInputNickName.equals("")){
				Toast.makeText(mContext, mContext.getResources().getString(R.string.nickname_not_null),Toast.LENGTH_LONG).show();
				return;
			}
			completeUserInfo();
			break;
		case R.id.new_header_layout:
			selectImg();
			break;
		case R.id.nickname_layout:
			Intent nickNameIntent = new Intent();
			nickNameIntent.setClass(mContext, WriteUserInfoActivity.class);
			nickNameIntent.putExtra("content", mInputNickName);
			nickNameIntent.putExtra("type",GlobleType.COMPLETE_NICKNAME);
			mType = GlobleType.COMPLETE_NICKNAME;
			startActivityForResult(nickNameIntent, 1);
			break;
		case R.id.addr_layout:
			Intent intent = new Intent();
			intent.setClass(mContext, TreeViewActivity.class);
			intent.putExtra("type",GlobleType.TreeViewActivity_City_TYPE);
			mType = GlobleType.COMPLETE_ADDR;
			startActivityForResult(intent, 1);
			break;
		case R.id.sex_layout:
			MMAlert.showAlert(mContext, "", mContext.getResources().
					getStringArray(R.array.sex_array), 
					null, new OnAlertSelectId() {

				@Override
				public void onClick(int whichButton) {
					switch (whichButton) {
					case 0:
						mInputSex = 0;
						mSexTextView.setText(mContext.getResources().getString(R.string.man));
						break;
					case 1:
						mInputSex = 1;
						mSexTextView.setText(mContext.getResources().getString(R.string.femal));
						break;
					default:
						break;
					}
				}
			});
			
			break;
		
		
		case R.id.sign_layout:
			Intent signIntent = new Intent();
			signIntent.setClass(mContext, WriteUserInfoActivity.class);
			signIntent.putExtra("content", mInputSign);
			signIntent.putExtra("type",GlobleType.COMPLETE_SIGN);
			mType = GlobleType.COMPLETE_SIGN;
			startActivityForResult(signIntent, 1);
			break;
		default:
			break;
		}
	}



	@Override
	protected void onDestroy() {
		super.onDestroy();
		if(mImageLoader.getImageBuffer().containsKey(mLogin.headsmall)){
			mImageView.setImageDrawable(null);
			if(mImageLoader.getImageBuffer().get(mLogin.headsmall)!=null){
				mImageLoader.getImageBuffer().get(mLogin.headsmall).recycle();
			}
		}
	}

	/*
	 * 页面回调事件
	 * (non-Javadoc)
	 * @see android.support.v4.app.FragmentActivity#onActivityResult(int, int, android.content.Intent)
	 */
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if(requestCode == 1 && resultCode == RESULT_OK){
			if(mType == GlobleType.COMPLETE_SEX){
				mInputSex = data.getIntExtra("sex",0);
				if(mInputSex == 0){
					mSexTextView.setText(mContext.getResources().getString(R.string.man));
				}else if(mInputSex == 1){
					mSexTextView.setText(mContext.getResources().getString(R.string.femal));
				}else if(mInputSex == 2){
					mSexTextView.setText(mContext.getResources().getString(R.string.no_limit));
				}

			}else if(mType == GlobleType.COMPLETE_NICKNAME){
				mInputNickName = data.getStringExtra("nickname");
				mNickNameTextView.setText(mInputNickName+" ");
			}else if(mType == GlobleType.COMPLETE_ADDR){
				mInputAddr = data.getStringExtra("addr");
				//省id
				//市id 
				mProvice = data.getStringExtra("provice");
				mCity = data.getStringExtra("city");
				mAddrTextView.setText(mInputAddr+" ");
			}else if(mType == GlobleType.COMPLETE_EMAIL){
				
			}else if(mType == GlobleType.COMPLETE_COMPANY){
				
			}else if(mType == GlobleType.COMPLETE_SIGN){
				mInputSign = data.getStringExtra("sign");
				mSiTextView.setText(mInputSign+" ");
			}else if(mType == GlobleType.COMPLETE_HANGYUE){
				
			}else if(mType == GlobleType.COMPLETE_SUBJECT){
			
			}
		}

		switch (requestCode) {
		case GlobalParam.REQUEST_GET_URI: 
			if (resultCode == RESULT_OK) {
				doChoose(true, data);
			}

			break;

		case GlobalParam.REQUEST_GET_IMAGE_BY_CAMERA:
			if(resultCode == RESULT_OK){
				doChoose(false, data);
			}
			break;
		case GlobalParam.REQUEST_GET_BITMAP:
			if(resultCode == RESULT_OK){

				Bundle extras = data.getExtras();
				if (extras != null) {

					mImageView.setImageBitmap(null);
					if(mBitmap != null && !mBitmap.isRecycled()){
						mBitmap.recycle();
						mBitmap = null;
					}

					mBitmap = extras.getParcelable("data");
					mImageView.setImageBitmap(mBitmap);
					File file = new File(Environment.getExternalStorageDirectory() + FeatureFunction.PUB_TEMP_DIRECTORY + TEMP_FILE_NAME);
					if(file != null && file.exists()){
						file.delete();
						file = null;
					}

					mImageFilePath = FeatureFunction.saveTempBitmap(mBitmap, "header.jpg");
				}

			}
			break;	
		default:
			break;
		}
	}

	/*
	 * 选择图片对话框
	 */
	private void selectImg(){
		MMAlert.showAlert(mContext, "", mContext.getResources().
				getStringArray(R.array.camer_item), 
				null, new OnAlertSelectId() {

			@Override
			public void onClick(int whichButton) {
				Log.e("whichButton", "whichButton: "+whichButton);
				switch (whichButton) {
				case 0:
					getImageFromGallery();
					break;
				case 1:
					getImageFromCamera();
					break;
				default:
					break;
				}
			}
		});
	}

	/*
	 * 拍一张
	 */
	private void getImageFromCamera() {
		Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

		if(FeatureFunction.newFolder(Environment.getExternalStorageDirectory() + FeatureFunction.PUB_TEMP_DIRECTORY)){
			File out = new File(Environment.getExternalStorageDirectory() + FeatureFunction.PUB_TEMP_DIRECTORY, TEMP_FILE_NAME);
			Uri uri = Uri.fromFile(out);
			intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);

			startActivityForResult(intent, GlobalParam.REQUEST_GET_IMAGE_BY_CAMERA);
		}

	}

	/*
	 * 从相册中选择
	 */
	private void getImageFromGallery() {
		Intent intent = new Intent(Intent.ACTION_GET_CONTENT, null);
		intent.setType("image/*");

		startActivityForResult(intent, GlobalParam.REQUEST_GET_URI);
	}

	/*
	 * 处理选择的图片
	 */
	private void doChoose(final boolean isGallery, final Intent data) {
		if(isGallery){
			originalImage(data);
		}else {
			if(data != null){
				originalImage(data);
			}else{
				// Here if we give the uri, we need to read it
				String path = Environment.getExternalStorageDirectory() + FeatureFunction.PUB_TEMP_DIRECTORY+TEMP_FILE_NAME;
				String extension = path.substring(path.indexOf("."), path.length());
				if(FeatureFunction.isPic(extension)){
					startPhotoZoom(Uri.fromFile(new File(path)));
				}else {
					//Toast.makeText(mContext, R.string.please_choose_pic, Toast.LENGTH_SHORT).show();
				}
				//mImageFilePath = FeatureFunction.PUB_TEMP_DIRECTORY+TEMP_FILE_NAME;
				//ShowBitmap(false);
			}
		}
	}

	private void originalImage(Intent data) {
		/*
		 * switch (requestCode) {
		 */
		// case FLAG_CHOOSE:
		Uri uri = data.getData();
		//Log.d("may", "uri=" + uri + ", authority=" + uri.getAuthority());
		if (!TextUtils.isEmpty(uri.getAuthority())) {
			Cursor cursor = getContentResolver().query(uri,
					new String[] { MediaStore.Images.Media.DATA }, null, null,
					null);
			if (null == cursor) {
				//Toast.makeText(mContext, R.string.no_found, Toast.LENGTH_SHORT).show();
				return;
			}
			cursor.moveToFirst();
			String path = cursor.getString(cursor
					.getColumnIndex(MediaStore.Images.Media.DATA));
			Log.d("may", "path=" + path);
			String extension = path.substring(path.lastIndexOf("."), path.length());
			if(FeatureFunction.isPic(extension)){
			
				startPhotoZoom(data.getData());

			}else {
				
			}


		} else {
			Log.d("may", "path=" + uri.getPath());
			String path = uri.getPath();
			String extension = path.substring(path.lastIndexOf("."), path.length());
			if(FeatureFunction.isPic(extension)){
				startPhotoZoom(uri);
			}else {
				//Toast.makeText(mContext, R.string.please_choose_pic, Toast.LENGTH_SHORT).show();
			}
			//mImageFilePath = uri.getPath();
			//ShowBitmap(false);
		}
	}

	/*
	 * 裁剪图片
	 */
	public void startPhotoZoom(Uri uri) {
		/*
		 * 至于下面这个Intent的ACTION是怎么知道的，大家可以看下自己路径下的如下网页
		 * yourself_sdk_path/docs/reference/android/content/Intent.html
		 * 直接在里面Ctrl+F搜：CROP ，之前小马没仔细看过，其实安卓系统早已经有自带图片裁剪功能,
		 * 是直接调本地库的，小马不懂C C++  这个不做详细了解去了，有轮子就用轮子，不再研究轮子是怎么
		 * 制做的了...吼吼
		 */
		Intent intent = new Intent("com.android.camera.action.CROP");
		intent.setDataAndType(uri, "image/*");
		//下面这个crop=true是设置在开启的Intent中设置显示的VIEW可裁剪
		intent.putExtra("crop", "true");
		// aspectX aspectY 是宽高的比例
		intent.putExtra("aspectX", 1);
		intent.putExtra("aspectY", 1);
		// outputX outputY 是裁剪图片宽高
		intent.putExtra("outputX", 180);
		intent.putExtra("outputY", 180);
		intent.putExtra("return-data", true);
		startActivityForResult(intent, GlobalParam.REQUEST_GET_BITMAP);
	}


}
