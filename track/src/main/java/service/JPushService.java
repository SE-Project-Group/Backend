package service;

import java.util.Collection;
import java.util.List;
import java.util.Map;

public interface JPushService {
//给所有平台的所有用户发通知 内容为msgContent
	boolean sendPushAll(String msgContent);
	
	//给regesterId List 中用户发通知 内容为msgContent
	boolean senPushByRegesterId(List<String> regeSterIds, String msgContent);
	
	//给Alias Collection 中用户发通知 内容为msgContent,里面是用户别名
	boolean senPushByAlias(Collection<String> alias, String msgContent);
	
	//给Alias用户发通知 内容为msgContent，用户别名为alias
	boolean senPushByAlias(String alias,String msgContent);
	
	//给所有平台的所有用户发message 内容为msgContent
	boolean sendMessageAll(String msgContent);
	
	//给Alias Collection 中用户发message 内容为msgContent,里面是用户别名
	boolean senMessageByAlias(String alias, String msgContent);
	
	//给Alias用户发message 内容为msgContent，用户别名Wiealias
	boolean senMessageByAlias(Collection<String> alias, String msgContent);
	
	//获取id为id的用户的follower列表
	public List<String> getFollowerIdById(int id);
}
