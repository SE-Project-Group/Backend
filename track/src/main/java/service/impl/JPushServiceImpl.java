package service.impl;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import cn.jiguang.common.resp.APIConnectionException;
import cn.jiguang.common.resp.APIRequestException;
import cn.jpush.api.JPushClient;
import cn.jpush.api.push.PushResult;
import cn.jpush.api.push.model.PushPayload;
import dao.FollowDao;
import service.JPushService;
import util.JPushUtil;

public class JPushServiceImpl implements JPushService{
	private FollowDao followDao;
	public  FollowDao getFollowDao() {
		return followDao;
	}

	public void setfollowDao(FollowDao followDao) {
		this.followDao = followDao;
	}
	
	  
	    private final static String appKey = "44e21f0a606fd0182958de50";  
	  
	    private final static String masterSecret = "582079c436f4f6fba28d2281";  
	    /** 
	     * �������ߵ�ʱ������Ϊ��λ�����֧��10�죨864000�룩�� 0 ��ʾ����Ϣ���������ߡ������û��������Ϸ�������ǰ�������û��������յ�����Ϣ�� 
	     * �˲������������ʾĬ�ϣ�Ĭ��Ϊ����1���������Ϣ��86400��)�� 
	     */  
	    private static long timeToLive = 60 * 60 * 24;  
	  
	    private static JPushClient jPushClient = null;  
	  
	    @SuppressWarnings("deprecation")
		@Override  
	    public  boolean sendPushAll(String msgContent) {  
	        jPushClient = new JPushClient(masterSecret, appKey, (int)timeToLive);  
	        boolean flag = false;  
	        try {  
	       
	            PushPayload payload = JPushUtil.buildPushObjectALL(msgContent);  
	            System.out.println("�������������ݣ�" + payload.toString());  
	  
    	  PushResult result = jPushClient.sendPush(payload);  
	            if (null != result) {  
	            	System.out.println("Get resul ---" + result);  
	                flag = true;   
	            }  
	        } catch (APIConnectionException e) {  
	        	System.out.println("Connection error. Should retry later. "+ e);  
	            flag = false;   
	        } catch (APIRequestException e) {  
	            System.out.println("Error response from JPush server. Should review and fix it. "+e);  
	            System.out.println("HTTP Status: " + e.getStatus());  
	            System.out.println("Error Code: " + e.getErrorCode());  
	            System.out.println("Error Message: " + e.getErrorMessage());  
	            System.out.println("Msg ID: " + e.getMsgId());  
	            flag = false;   
	        }  
	        return flag;  
	          
	    }  
	    @Override  
	    public  boolean  sendPushByRegesterId(List<String> regeSterIds,String msgContent) {  
	        jPushClient = new JPushClient(masterSecret, appKey);  
	        boolean flag = false;  
//	      String content = "���ID����";  
	        try {  
	            PushPayload payload = JPushUtil.buildPushObjectRegesterIds(regeSterIds,msgContent);  
	            System.out.println("�������������ݣ�" + payload.toString());  
	            PushResult result = jPushClient.sendPush(payload);  
	            if (null != result) {  
	            	System.out.println("Get result ----" + result);  
	                flag = true;  
	            }  
	        } catch (APIConnectionException e) {  
	        	System.out.println("Connection error. Should retry later. "+e);  
	            flag = false;   
	        } catch (APIRequestException e) {  
	        	System.out.println("Error response from JPush server. Should review and fix it. "+ e);  
	        	System.out.println("HTTP Status: " + e.getStatus());  
	        	System.out.println("Error Code: " + e.getErrorCode());  
	        	System.out.println("Error Message: " + e.getErrorMessage());  
	        	System.out.println("Msg ID: " + e.getMsgId());  
	            flag = false;   
	        }  
	  
	        return flag;  
	    }  
	    @Override  
	    public  boolean  sendPushByAlias(Collection<String> alias,String msgContent) {  
	        jPushClient = new JPushClient(masterSecret, appKey);  
	        boolean flag = false;  
//	      String content = "���ID����";  
	        try {  
	            PushPayload payload = JPushUtil.buildPushObjectAlias(alias,msgContent);  
	            System.out.println("�������������ݣ�" + payload.toString());  
	            PushResult result = jPushClient.sendPush(payload);  
	            if (null != result) {  
	            	System.out.println("Get result ----" + result);  
	                flag = true;  
	            }  
	        } catch (APIConnectionException e) {  
	        	System.out.println("Connection error. Should retry later. "+e);  
	            flag = false;   
	        } catch (APIRequestException e) {  
	        	System.out.println("Error response from JPush server. Should review and fix it. "+ e);  
	        	System.out.println("HTTP Status: " + e.getStatus());  
	        	System.out.println("Error Code: " + e.getErrorCode());  
	        	System.out.println("Error Message: " + e.getErrorMessage());  
	        	System.out.println("Msg ID: " + e.getMsgId());  
	            flag = false;   
	        }  
	  
	        return flag;  
	    } 
	   
	    @Override  
	    public  boolean  sendPushByAlias(String alias,String msgContent) {  
	        jPushClient = new JPushClient(masterSecret, appKey);  
	        boolean flag = false;  
//	      String content = "���ID����";  
	        try {  
	            PushPayload payload = JPushUtil.buildPushObjectAlias(alias,msgContent);  
	            System.out.println("�������������ݣ�" + payload.toString());  
	            PushResult result = jPushClient.sendPush(payload);  
	            if (null != result) {  
	            	System.out.println("Get result ----" + result);  
	                flag = true;  
	            }  
	        } catch (APIConnectionException e) {  
	        	System.out.println("Connection error. Should retry later. "+e);  
	            flag = false;   
	        } catch (APIRequestException e) {  
	        	System.out.println("Error response from JPush server. Should review and fix it. "+ e);  
	        	System.out.println("HTTP Status: " + e.getStatus());  
	        	System.out.println("Error Code: " + e.getErrorCode());  
	        	System.out.println("Error Message: " + e.getErrorMessage());  
	        	System.out.println("Msg ID: " + e.getMsgId());  
	            flag = false;   
	        }  
	  
	        return flag;  
	    } 
	    @Override  
	    public  boolean  sendPushByAlias(Collection<String> alias,String title, String content,Map<String,String>extra) {  
	        jPushClient = new JPushClient(masterSecret, appKey);  
	        boolean flag = false;  
//	      String content = "���ID����";  
	        try {  
	            PushPayload payload = JPushUtil.buildPushObjectAliasExtra(alias,title,content,extra);  
	            System.out.println("�������������ݣ�" + payload.toString());  
	            PushResult result = jPushClient.sendPush(payload);  
	            if (null != result) {  
	            	System.out.println("Get result ----" + result);  
	                flag = true;  
	            }  
	        } catch (APIConnectionException e) {  
	        	System.out.println("Connection error. Should retry later. "+e);  
	            flag = false;   
	        } catch (APIRequestException e) {  
	        	System.out.println("Error response from JPush server. Should review and fix it. "+ e);  
	        	System.out.println("HTTP Status: " + e.getStatus());  
	        	System.out.println("Error Code: " + e.getErrorCode());  
	        	System.out.println("Error Message: " + e.getErrorMessage());  
	        	System.out.println("Msg ID: " + e.getMsgId());  
	            flag = false;   
	        }  
	  
	        return flag;  
	    } 
	    public boolean sendMessageAll(String msgContent){
	        jPushClient = new JPushClient(masterSecret, appKey, (int)timeToLive);  
	        boolean flag = false;  
	        try {  
	           
	            PushPayload payload = JPushUtil.buildMessageObjectALL(msgContent);  
	            System.out.println("�������������ݣ�" + payload.toString());  
	  
    	  PushResult result = jPushClient.sendPush(payload);  
	            if (null != result) {  
	            	System.out.println("Get resul ---" + result);  
	                flag = true;   
	            }  
	        } catch (APIConnectionException e) {  
	        	System.out.println("Connection error. Should retry later. "+ e);  
	            flag = false;   
	        } catch (APIRequestException e) {  
	            System.out.println("Error response from JPush server. Should review and fix it. "+e);  
	            System.out.println("HTTP Status: " + e.getStatus());  
	            System.out.println("Error Code: " + e.getErrorCode());  
	            System.out.println("Error Message: " + e.getErrorMessage());  
	            System.out.println("Msg ID: " + e.getMsgId());  
	            flag = false;   
	        }  
	        return flag; 
	    }
	    @Override  
	    public  boolean  sendMessageByAlias(String alias,String msgContent) {  
	        jPushClient = new JPushClient(masterSecret, appKey);  
	        boolean flag = false;  
//	      String content = "���ID����";  
	        try {  
	            PushPayload payload = JPushUtil.buildMessageObjectAlias(alias,msgContent);  
	            System.out.println("�������������ݣ�" + payload.toString());  
	            PushResult result = jPushClient.sendPush(payload);  
	            if (null != result) {  
	            	System.out.println("Get result ----" + result);  
	                flag = true;  
	            }  
	        } catch (APIConnectionException e) {  
	        	System.out.println("Connection error. Should retry later. "+e);  
	            flag = false;   
	        } catch (APIRequestException e) {  
	        	System.out.println("Error response from JPush server. Should review and fix it. "+ e);  
	        	System.out.println("HTTP Status: " + e.getStatus());  
	        	System.out.println("Error Code: " + e.getErrorCode());  
	        	System.out.println("Error Message: " + e.getErrorMessage());  
	        	System.out.println("Msg ID: " + e.getMsgId());  
	            flag = false;   
	        }  
	  
	        return flag;  
	    } 
	    @Override  
	    public boolean sendMessageByAlias(Collection<String> alias, String msgContent){
	    	 jPushClient = new JPushClient(masterSecret, appKey);  
		        boolean flag = false;  
//		      String content = "���ID����";  
		        try {  
		            PushPayload payload = JPushUtil.buildMessageObjectAlias(alias,msgContent);  
		            System.out.println("�������������ݣ�" + payload.toString());  
		            PushResult result = jPushClient.sendPush(payload);  
		            if (null != result) {  
		            	System.out.println("Get result ----" + result);  
		                flag = true;  
		            }  
		        } catch (APIConnectionException e) {  
		        	System.out.println("Connection error. Should retry later. "+e);  
		            flag = false;   
		        } catch (APIRequestException e) {  
		        	System.out.println("Error response from JPush server. Should review and fix it. "+ e);  
		        	System.out.println("HTTP Status: " + e.getStatus());  
		        	System.out.println("Error Code: " + e.getErrorCode());  
		        	System.out.println("Error Message: " + e.getErrorMessage());  
		        	System.out.println("Msg ID: " + e.getMsgId());  
		            flag = false;   
		        }  
		  
		        return flag;  
	    }
	    public List<String> getFollowerIdById(int id){
	    return	followDao.getFollowerIdById(id);
	    }
}
