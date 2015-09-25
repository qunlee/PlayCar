package com.wqdsoft.im.receiver;

import android.app.ActivityManager;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.playcar.R;
import com.wqdsoft.im.MainActivity;
import com.wqdsoft.im.MettingDetailActivity;
import com.wqdsoft.im.DB.DBHelper;
import com.wqdsoft.im.DB.RoomTable;
import com.wqdsoft.im.DB.SessionTable;
import com.wqdsoft.im.DB.UserTable;
import com.wqdsoft.im.Entity.Login;
import com.wqdsoft.im.Entity.MessageInfo;
import com.wqdsoft.im.Entity.MessageType;
import com.wqdsoft.im.Entity.Room;
import com.wqdsoft.im.Entity.SNSMessage;
import com.wqdsoft.im.Entity.UnReadSessionInfo;
import com.wqdsoft.im.fragment.ChatFragment;
import com.wqdsoft.im.global.FeatureFunction;
import com.wqdsoft.im.global.GlobalParam;
import com.wqdsoft.im.global.GlobleType;
import com.wqdsoft.im.global.IMCommon;
import com.wqdsoft.im.service.SnsService;

public class ChatMessageNotifiy extends AbstractNotifiy{
	private static final String LOGTAG = "msgNotifiy";
	
	private Context mContext;
	public ChatMessageNotifiy(SnsService context) {
		super(context);
		mContext = context;
	}

	@Override
	public void notifiy(SNSMessage message) {
		Log.d(LOGTAG, "notify()...");
		MessageInfo messageInfo = null;
		if(message instanceof MessageInfo){
			messageInfo = (MessageInfo) message;
		}else{
			return;
		}
		
		String fuid = messageInfo.fromid;
		
		String msg = null;

		switch (messageInfo.typefile) {
		case MessageType.PICTURE:
			msg = messageInfo.fromname + " <" + mContext.getString(R.string.get_picture) + " > ";
			break;
		case MessageType.TEXT:
			msg = messageInfo.fromname + " : " +  messageInfo.getContent();
			break;
		case MessageType.VOICE:
			msg = messageInfo.fromname + " <" + mContext.getString(R.string.get_voice) + " > ";
			break;
		case MessageType.MAP:
			msg = messageInfo.fromname + " <" + mContext.getString(R.string.get_location) + " > ";
			break;

		default:
			break;
		}
			
		// Notification
		Notification notification = new Notification(R.drawable.tab_bar_icon_comment_d, 
				messageInfo.fromname + mContext.getResources().getString(R.string.send_one_msg),
				System.currentTimeMillis());
		
		//notification.icon = R.drawable.ic_launcher; // 设置通知的图标
		long currentTime = System.currentTimeMillis();
		if (currentTime - IMCommon.getNotificationTime(mContext) > IMCommon.NOTIFICATION_INTERVAL) {
			if(IMCommon.getLoginResult(mContext).isAcceptNew/*IMCommon.getOpenSound(mContext)*/){
				if(IMCommon.getLoginResult(mContext).isOpenVoice){
					notification.defaults |= Notification.DEFAULT_SOUND;
				}
				if(IMCommon.getLoginResult(mContext).isOpenShake){
					notification.defaults |= Notification.DEFAULT_VIBRATE;
				}
			}
			IMCommon.saveNotificationTime(mContext, currentTime);
			
		}
		notification.defaults |= Notification.DEFAULT_LIGHTS;
		notification.flags |= Notification.FLAG_AUTO_CANCEL;
		// 音频将被重复直到通知取消或通知窗口打开。
		// notification.flags |= Notification.FLAG_INSISTENT;
		notification.flags |= Notification.FLAG_SHOW_LIGHTS;
		notification.when = currentTime;
		int acceptId =  messageInfo.getFromId().hashCode();
		Login user = new Login();
		SQLiteDatabase dbDatabase = DBHelper.getInstance(mContext).getWritableDatabase();
		int isGetMsg = 0;
		if(messageInfo.typechat == 100){
			acceptId = messageInfo.getFromId().hashCode();
			user.uid = messageInfo.getFromId();
			user.nickname = messageInfo.fromname;
			user.headsmall = messageInfo.fromurl;
			UserTable userTable = new UserTable(dbDatabase);
			Login dbLogin = userTable.query(messageInfo.getFromId());
			if(dbLogin!=null){
				isGetMsg = dbLogin.isGetMsg;
			}
		}else {
			acceptId = messageInfo.getToId().hashCode();
			user.uid = messageInfo.getToId();
			user.nickname = messageInfo.toname;
			user.headsmall = messageInfo.tourl;
			RoomTable roomTable = new RoomTable(dbDatabase);
			Room room = roomTable.query(messageInfo.getToId());
			if(room!=null ){
				isGetMsg = room.isgetmsg;
			}
		}
		
		try {
			ActivityManager am = (ActivityManager) mContext.getSystemService(Context.ACTIVITY_SERVICE);
			ComponentName cn = am.getRunningTasks(1).get(0).topActivity;
			if(cn.getClassName().equals(cn.getPackageName() + ".ChatMainActivity")){
				if(FeatureFunction.isAppOnForeground(mContext)){
					return;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
			
		 if(messageInfo.typechat != GlobleType.MEETING_CHAT){
			 mContext.sendBroadcast(new Intent(ChatFragment.ACTION_REFRESH_SESSION));
			mContext.sendBroadcast(new Intent(GlobalParam.ACTION_UPDATE_SESSION_COUNT));
		}else{
			Intent intent = new Intent(GlobalParam.ACTION_SHOW_FOUND_NEW_TIP);
			intent.putExtra("found_type", 1);
			mContext.sendBroadcast(intent);
			mContext.sendBroadcast(new Intent(MettingDetailActivity.ACTION_SHOW_NEW_MEETING_TIP));
		}
	
		if(isGetMsg == 0){
			return;
		}
		if(!FeatureFunction.isAppOnForeground(mContext) && !IMCommon.getLoginResult(mContext).isAcceptNew){
			return;
		}
		
		Intent intent = new Intent(mContext, MainActivity.class);
		intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
		intent.putExtra("data", user);
		intent.putExtra("chatnotify", true);
		intent.putExtra("type", messageInfo.typechat);
		
		PendingIntent contentIntent = PendingIntent.getActivity(mContext, messageInfo.getToId().hashCode(), intent, PendingIntent.FLAG_UPDATE_CURRENT);
		//notification.setLatestEventInfo(mContext, mContext.getString(R.string.get_new_message), msg, contentIntent);
		//getNotificationManager().notify(acceptId, notification);
		
		SQLiteDatabase db = DBHelper.getInstance(mContext).getReadableDatabase();
		SessionTable table = new SessionTable(db);
		UnReadSessionInfo sessionInfo = table.queryUnReadSessionInfo();
		
		String notificationTitle = "";
		String notificationContent = "";
		if (sessionInfo.sessionCount > 1) {
			notificationTitle = mContext.getString(R.string.ochat_app_name);
			
			notificationContent = sessionInfo.sessionCount + mContext.getString(R.string.contact_count) 
					+ mContext.getString(R.string.send_in) + sessionInfo.msgCount 
					+ mContext.getString(R.string.msg_count_tip);
        }
		else {
			notificationTitle = messageInfo.fromname;
			
			if (sessionInfo.msgCount > 1) {
				notificationContent = mContext.getString(R.string.send_in) + sessionInfo.msgCount 
						+ mContext.getString(R.string.msg_count_tip);
			}
			else {
				notificationContent = messageInfo.getContent();
			}
		}
		
		notification.setLatestEventInfo(mContext, notificationTitle, notificationContent, contentIntent);
		
		getNotificationManager().notify(0, notification);

		
	}

}
