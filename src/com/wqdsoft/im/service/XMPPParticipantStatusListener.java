package com.wqdsoft.im.service;

import android.util.Log;

import com.wqdsoft.im.global.IMCommon;
import com.xmpp.push.sns.muc.ParticipantStatusListener;

public class XMPPParticipantStatusListener implements ParticipantStatusListener{
	
	public static final String LOGTAG = "XMPPParticipantStatusListener";
	private XmppManager xmppManager;
	
	public XMPPParticipantStatusListener(XmppManager xmppManager) {
		super();
		this.xmppManager = xmppManager;
	}

	@Override
	public void left(String participant) {
		Log.e("XMPPParticipantStatusListener", "Left " + participant);
	}

	@Override
	public void kicked(String participant, String actor, String reason) {
		String uid = participant.split("@")[0];
		if(uid.equals(IMCommon.getUserId(xmppManager.getSnsService()))){
			Log.e("XMPPParticipantStatusListener", "Be kicked");
		}
	}

	@Override
	public void voiceGranted(String participant) {
		
	}

	@Override
	public void voiceRevoked(String participant) {
		
	}

	@Override
	public void banned(String participant, String actor, String reason) {
		
	}

	@Override
	public void membershipGranted(String participant) {
		
	}

	@Override
	public void membershipRevoked(String participant) {
		
	}

	@Override
	public void moderatorGranted(String participant) {
		
	}

	@Override
	public void moderatorRevoked(String participant) {
		
	}

	@Override
	public void ownershipGranted(String participant) {
		
	}

	@Override
	public void ownershipRevoked(String participant) {
		
	}

	@Override
	public void adminGranted(String participant) {
		
	}

	@Override
	public void adminRevoked(String participant) {
		
	}

	@Override
	public void nicknameChanged(String participant, String newNickname) {
		
	}

	@Override
	public void joined(String participant) {
		
	}

}
