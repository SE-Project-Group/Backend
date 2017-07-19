package util;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import cn.jpush.api.push.model.Message;
import cn.jpush.api.push.model.Platform;
import cn.jpush.api.push.model.PushPayload;
import cn.jpush.api.push.model.audience.Audience;
import cn.jpush.api.push.model.notification.Notification;

/** 
 * �������͹����� https://github.com/ 
 *  
 * 
 */  
public class JPushUtil {  
    /** 
     * ����ƽ̨�������豸������Ϊ content ��֪ͨ 
     *  
     * @param content 
     * @return 
     */  
    public static PushPayload buildPushObjectALL(String content) {  
        return PushPayload.alertAll(content);  
    }  
    /** 
     * ����ƽ̨�������豸������Ϊ content ��message 
     *  
     * @param content 
     * @return 
     */  
    public static PushPayload buildMessageObjectALL(String content) {  
        return PushPayload.messageAll(content);
    }  
  
    /** 
     * ���� �豸�ն�ID ������Ϣ 
     *  
     * @param regesterIds 
     *            �豸�ն�ID���� 
     * @param content 
     *            ���� 
     * @return 
     */  
    public static PushPayload buildPushObjectRegesterIds(List<String> regesterIds, String content) {  
        return PushPayload.newBuilder().setPlatform(Platform.all())  
                .setAudience(Audience.registrationId(regesterIds))  
                .setNotification(Notification.alert(content)) 
                
                .build();  
  
    }  
    /** 
     * ���� �豸�ն�ID message 
     *  
     * @param regesterIds 
     *            �豸�ն�ID���� 
     * @param content 
     *            ���� 
     * @return 
     */  
    public static PushPayload buildMessageObjectRegesterIds(List<String> regesterIds, String content) {  
        return PushPayload.newBuilder().setPlatform(Platform.all())  
                .setAudience(Audience.registrationId(regesterIds))  
                .setMessage(Message.content(content))
                .build();  
  
    }  
    /** 
     * ����ƽ̨������Ŀ���Ǳ���Ϊ "alias"��֪ͨ����Ϊ TEST 
     *  
     * @param alias 
     * @param content 
     * @return 
     */  
    public static PushPayload buildPushObjectAlias(Collection<String> alias, String content) {  
        return PushPayload.newBuilder().setPlatform(Platform.all()).setAudience(Audience.alias(alias))  
                .setNotification(Notification.alert(content)).build();  
    }
    /** 
     * ����ƽ̨����ϢĿ���Ǳ���Ϊ "alias"��֪ͨ����Ϊ TEST 
     *  
     * @param alias 
     * @param content 
     * @return 
     */  
    public static PushPayload buildMessageObjectAlias(Collection<String> alias, String content) {  
        return PushPayload.newBuilder().setPlatform(Platform.all()).setAudience(Audience.alias(alias))  
        		.setMessage(Message.content(content))
        		.build();  
    } 
    /** 
     * ����ƽ̨����ϢĿ���Ǳ���Ϊ "alias"��֪ͨ����Ϊ TEST 
     *  
     * @param alias 
     * @param content 
     * @return 
     */  
    public static PushPayload buildPushObjectAlias(String alias, String content) {  
        return PushPayload.newBuilder().setPlatform(Platform.all()).setAudience(Audience.alias(alias))  
        		.setNotification(Notification.alert(content))
        		.build();  
    }
    /** 
     * ����ƽ̨����ϢĿ���Ǳ���Ϊ "alias"��֪ͨ����Ϊ TEST 
     *  
     * @param alias 
     * @param content 
     * @return 
     */  
    public static PushPayload buildMessageObjectAlias(String alias, String content) {  
        return PushPayload.newBuilder().setPlatform(Platform.all()).setAudience(Audience.alias(alias))  
        		.setMessage(Message.content(content))
        		.build();  
    }}