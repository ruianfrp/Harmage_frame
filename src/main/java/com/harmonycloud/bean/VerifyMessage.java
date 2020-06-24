package com.harmonycloud.bean;

import com.harmonycloud.bean.account.LoginInfoView;

public class VerifyMessage {
	public Message message;
	public LoginInfoView user;
	public VerifyMessage(){}
	public VerifyMessage(Message message,LoginInfoView user){
		this.message=message;
		this.user=user;
	}
}
