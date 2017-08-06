package service;

import java.util.Collection;
import java.util.List;
import java.util.Map;

public interface JPushService {
//������ƽ̨�������û���֪ͨ ����ΪmsgContent
	boolean sendPushAll(String msgContent);
	
	//��regesterId List ���û���֪ͨ ����ΪmsgContent
	boolean sendPushByRegesterId(List<String> regeSterIds, String msgContent);
	
	//��Alias Collection ���û���֪ͨ ����ΪmsgContent,�������û�����
	boolean sendPushByAlias(Collection<String> alias, String msgContent);
	
	//��Alias�û���֪ͨ ����ΪmsgContent���û�����Ϊalias
	boolean sendPushByAlias(String alias,String msgContent);
	
	//��Alias�û���֪ͨ ����ΪmsgContent���û�����Ϊalias
	boolean sendPushByAlias(Collection<String> alias,String title, String content,Map<String,String>extra);
	//������ƽ̨�������û���message ����ΪmsgContent
	boolean sendMessageAll(String msgContent);
	
	//��Alias Collection ���û���message ����ΪmsgContent,�������û�����
	boolean sendMessageByAlias(String alias, String msgContent);
	
	//��Alias�û���message ����ΪmsgContent���û�����Wiealias
	boolean sendMessageByAlias(Collection<String> alias, String msgContent);
	
	//��ȡidΪid���û���follower�б�
	public List<String> getFollowerIdById(int id);
}
