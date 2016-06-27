package com.btime.im.server.ons.listener;

import com.aliyun.openservices.ons.api.Action;
import com.aliyun.openservices.ons.api.ConsumeContext;
import com.aliyun.openservices.ons.api.Message;
import com.aliyun.openservices.ons.api.MessageListener;

public class ChatMessageListener implements MessageListener {

	@Override
	public Action consume(Message message, ConsumeContext context) {
		System.out.println("Receive: " + message.getMsgID());
		try {
			// do something..
			return Action.CommitMessage;
		} catch (Exception e) {
			// 消费失败
			return Action.ReconsumeLater;
		}
	}

}
