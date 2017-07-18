package util;

import java.util.Collection;
import java.util.List;

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
    public static PushPayload buildPushObject_all_all_alert(String content) {  
        return PushPayload.alertAll(content);  
    }  
    /** 
     * ����ƽ̨�������豸������Ϊ content ��message 
     *  
     * @param content 
     * @return 
     */  
    public static PushPayload buildPushObject_all_all_message(String content) {  
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
    public static PushPayload buildPushObject_all_all_regesterIds(List<String> regesterIds, String content) {  
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
    public static PushPayload buildPushObject_all_all_regesterIds_massage(List<String> regesterIds, String content) {  
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
    public static PushPayload buildPushObject_all_alias_alert(Collection<String> alias, String content) {  
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
    public static PushPayload buildPushObject_all_alias_message(Collection<String> alias, String content) {  
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
    public static PushPayload buildPushObject_all_alias_message(String alias, String content) {  
        return PushPayload.newBuilder().setPlatform(Platform.all()).setAudience(Audience.alias(alias))  
        		.setMessage(Message.content(content))
        		.build();  
    } }