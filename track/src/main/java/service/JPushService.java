package service;

import java.util.Collection;
import java.util.List;
import java.util.Map;

public interface JPushService {
//������ƽ̨�������û���֪ͨ ����ΪmsgContent
	boolean sendPushAll(String msgContent);
	
	//��regesterId List ���û���֪ͨ ����ΪmsgContent
	boolean senPushByRegesterId(List<String> regeSterIds, String msgContent);
	
	//��Alias Collection ���û���֪ͨ ����ΪmsgContent,�������û�����
	boolean senPushByAlias(Collection<String> alias, String msgContent);
	
	//��Alias�û���֪ͨ ����ΪmsgContent���û�����Ϊalias
	boolean senPushByAlias(String alias,String msgContent);
	
	//������ƽ̨�������û���message ����ΪmsgContent
	boolean sendMessageAll(String msgContent);
	
	//��Alias Collection ���û���message ����ΪmsgContent,�������û�����
	boolean senMessageByAlias(String alias, String msgContent);
	
	//��Alias�û���message ����ΪmsgContent���û�����Wiealias
	boolean senMessageByAlias(Collection<String> alias, String msgContent);
	
	//��ȡidΪid���û���follower�б�
	public List<String> getFollowerIdById(int id);
}
