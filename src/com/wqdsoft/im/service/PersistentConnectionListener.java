/*
 * Copyright (C) 2010 Moduad Co., Ltd.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.wqdsoft.im.service;

import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.playcar.R;
import com.wqdsoft.im.global.FeatureFunction;
import com.wqdsoft.im.global.GlobalParam;
import com.wqdsoft.im.global.IMCommon;
import com.wqdsoft.im.map.BMapApiApp;
import com.xmpp.push.sns.ConnectionListener;

public class PersistentConnectionListener implements ConnectionListener {

	private static final String LOGTAG = "PersissstentConnectionListener";

	private final XmppManager xmppManager;

	public PersistentConnectionListener(XmppManager xmppManager) {
		this.xmppManager = xmppManager;
	}

	@Override
	public void connectionClosed() {
		Log.e(LOGTAG, "connectionClosed()...");
		try {
			if(IMCommon.verifyNetwork(xmppManager.getSnsService()) 
					&& xmppManager.getSnsService().isServiceRunState())
			{
				xmppManager.startReconnectionThread();
			}
		} catch (Exception e) {
		}
	}

	@Override
	public void connectionClosedOnError(Exception e) {
		Log.e(LOGTAG, "connectionClosedOnError()...");
		if(e.getMessage().contains("stream:error (conflict)")){
			
			//清楚通知栏所有的通知
			NotificationManager notificationManager = (NotificationManager) BMapApiApp.getInstance().getSystemService(Context.NOTIFICATION_SERVICE);
			notificationManager.cancelAll();
			
			Intent toastIntent = new Intent(GlobalParam.ACTION_SHOW_TOAST);
			toastIntent.putExtra("toast_msg",BMapApiApp.getInstance().getResources().getString(R.string.account_repeat));
			BMapApiApp.getInstance().sendBroadcast(toastIntent);

			IMCommon.saveLoginResult(BMapApiApp.getInstance(),null);
			IMCommon.setUid("");
			
    		BMapApiApp.getInstance().sendBroadcast(new Intent(GlobalParam.ACTION_DESTROY_CURRENT_ACTIVITY));
    		Intent serviceIntent = new Intent(BMapApiApp.getInstance(), SnsService.class);
    		BMapApiApp.getInstance().stopService(serviceIntent);

    		BMapApiApp.getInstance().sendBroadcast(new Intent(GlobalParam.ACTION_LOGIN_OUT));
		}else{
			try {

				xmppManager.getConnection().disconnect();
			} catch (Exception e2) {
				e.printStackTrace();
				e2.printStackTrace();
			}
			try {
				if(IMCommon.verifyNetwork(xmppManager.getSnsService()) && xmppManager.getSnsService().isServiceRunState()){
					xmppManager.startReconnectionThread();
				}
			} catch (Exception e2) {
			}
		}

	}

	@Override
	public void reconnectingIn(int seconds) {
		Log.e(LOGTAG, "reconnectingIn()...");
	}

	@Override
	public void reconnectionFailed(Exception e) {
		Log.e(LOGTAG, "reconnectionFailed()...");
	}

	@Override
	public void reconnectionSuccessful() {
		Log.e(LOGTAG, "reconnectionSuccessful()...");
	}

}
