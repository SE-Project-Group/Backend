package service;

import java.util.Collection;
import java.util.List;

public interface JPushService {

	boolean sendPushAll();

	boolean senPushByRegesterId(List<String> regeSterIds, String msgContent);

	boolean senPushByAlias(Collection<String> alias, String msgContent);
	
	boolean senMessageByAlias(String alias, String msgContent);
	
	public List<String> getFollowerIdById(int id);
}
