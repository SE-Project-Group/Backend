package test;

import cn.jiguang.common.resp.APIConnectionException;
import cn.jiguang.common.resp.APIRequestException;
import cn.jpush.api.JPushClient;
import cn.jpush.api.push.PushResult;
import cn.jpush.api.push.model.Message;
import cn.jpush.api.push.model.Platform;
import cn.jpush.api.push.model.PushPayload;
import cn.jpush.api.push.model.audience.Audience;
import cn.jpush.api.push.model.notification.Notification;

public class JPush {
public static void main(String args[]){
	push();
}
public static void push(){
	String masterSecret="499b098b106a0fc2727e44ce";
	String APPKey="1ef067ddcbc477cf37235fcc";
	String message="testmessage";
	JPushClient  jpushClient=new JPushClient(masterSecret,APPKey);
	PushPayload payload=PushPayload.newBuilder()
			.setPlatform(Platform.all())
			.setAudience(Audience.all())
			.setMessage(Message.content(message))
			.setNotification(Notification.alert("jpushalartTest"))
			.build();
	try {
		PushResult result=jpushClient.sendPush(payload);
		System.out.println("success");
		System.out.println(result.msg_id);
		System.out.println(result.sendno);
		
	} catch (APIConnectionException e) {
		System.out.println("connection error");		
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (APIRequestException e) {
		// TODO Auto-generated catch block
		System.out.println("request error");
		e.printStackTrace();
	}
}
}
