package service;

import java.util.Collection;
import java.util.List;
import java.util.Map;

public interface JPushService {
//给所有平台的所有用户发通知 内容为msgContent
	boolean sendPushAll(String msgContent);
	
	//给regesterId List 中用户发通知 内容为msgContent
	boolean sendPushByRegesterId(List<String> regeSterIds, String msgContent);
	
	//给Alias Collection 中用户发通知 内容为msgContent,里面是用户别名
	boolean sendPushByAlias(Collection<String> alias, String msgContent);
	
	//给Alias用户发通知 内容为msgContent，用户别名为alias
	boolean sendPushByAlias(String alias,String msgContent);
	
	//给Alias用户发通知 内容为msgContent，用户别名为alias
	boolean sendPushByAlias(Collection<String> alias,String title, String content,Map<String,String>extra);
	//给所有平台的所有用户发message 内容为msgContent
	boolean sendMessageAll(String msgContent);
	
	//给Alias Collection 中用户发message 内容为msgContent,里面是用户别名
	boolean sendMessageByAlias(String alias, String msgContent);
	
	//给Alias用户发message 内容为msgContent，用户别名Wiealias
	boolean sendMessageByAlias(Collection<String> alias, String msgContent);
	
	//获取id为id的用户的follower列表
	public List<String> getFollowerIdById(int id);
}
