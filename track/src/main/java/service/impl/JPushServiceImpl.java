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
	
	  
	    private final static String appKey = "1ef067ddcbc477cf37235fcc";  
	  
	    private final static String masterSecret = "499b098b106a0fc2727e44ce";  
	    /** 
	     * 保存离线的时长。秒为单位。最多支持10天（864000秒）。 0 表示该消息不保存离线。即：用户在线马上发出，当前不在线用户将不会收到此消息。 
	     * 此参数不设置则表示默认，默认为保存1天的离线消息（86400秒)。 
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
	            System.out.println("服务器返回数据：" + payload.toString());  
	  
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
	    public  boolean  senPushByRegesterId(List<String> regeSterIds,String msgContent) {  
	        jPushClient = new JPushClient(masterSecret, appKey);  
	        boolean flag = false;  
//	      String content = "多个ID测试";  
	        try {  
	            PushPayload payload = JPushUtil.buildPushObjectRegesterIds(regeSterIds,msgContent);  
	            System.out.println("服务器返回数据：" + payload.toString());  
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
	    public  boolean  senPushByAlias(Collection<String> alias,String msgContent) {  
	        jPushClient = new JPushClient(masterSecret, appKey);  
	        boolean flag = false;  
//	      String content = "多个ID测试";  
	        try {  
	            PushPayload payload = JPushUtil.buildPushObjectAlias(alias,msgContent);  
	            System.out.println("服务器返回数据：" + payload.toString());  
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
	    public  boolean  senPushByAlias(String alias,String msgContent) {  
	        jPushClient = new JPushClient(masterSecret, appKey);  
	        boolean flag = false;  
//	      String content = "多个ID测试";  
	        try {  
	            PushPayload payload = JPushUtil.buildPushObjectAlias(alias,msgContent);  
	            System.out.println("服务器返回数据：" + payload.toString());  
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
	            System.out.println("服务器返回数据：" + payload.toString());  
	  
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
	    public  boolean  senMessageByAlias(String alias,String msgContent) {  
	        jPushClient = new JPushClient(masterSecret, appKey);  
	        boolean flag = false;  
//	      String content = "多个ID测试";  
	        try {  
	            PushPayload payload = JPushUtil.buildMessageObjectAlias(alias,msgContent);  
	            System.out.println("服务器返回数据：" + payload.toString());  
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
	    public boolean senMessageByAlias(Collection<String> alias, String msgContent){
	    	 jPushClient = new JPushClient(masterSecret, appKey);  
		        boolean flag = false;  
//		      String content = "多个ID测试";  
		        try {  
		            PushPayload payload = JPushUtil.buildMessageObjectAlias(alias,msgContent);  
		            System.out.println("服务器返回数据：" + payload.toString());  
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
