package test;

import com.aliyuncs.http.MethodType;
import com.aliyuncs.http.ProtocolType;
import com.aliyuncs.push.model.v20160801.*;
import com.aliyuncs.utils.ParameterHelper;
import org.junit.Test;

import java.util.Date;

/**
 * Created by lingbo on 16/8/15.
 */
public class PushTest extends BaseTest {


    /**
     * 鎺ㄩ�佹秷鎭粰android
     * <p>
     * 鍙傝鏂囨。 https://help.aliyun.com/document_detail/48085.html
     */
    @Test
    public void testPushMessageToAndroid() throws Exception {

        PushMessageToAndroidRequest androidRequest = new PushMessageToAndroidRequest();
        //瀹夊叏鎬ф瘮杈冮珮鐨勫唴瀹瑰缓璁娇鐢℉TTPS
        androidRequest.setProtocol(ProtocolType.HTTPS);
        //鍐呭杈冨ぇ鐨勮姹傦紝浣跨敤POST璇锋眰
        androidRequest.setMethod(MethodType.POST);
        androidRequest.setAppKey(appKey);
        androidRequest.setTarget("ALL");
        androidRequest.setTargetValue("ALL");
        androidRequest.setTitle("66666666666");
        androidRequest.setBody("6");
        PushMessageToAndroidResponse pushMessageToAndroidResponse = client.getAcsResponse(androidRequest);
        System.out.printf("RequestId: %s, MessageId: %s\n",
                pushMessageToAndroidResponse.getRequestId(), pushMessageToAndroidResponse.getMessageId());

    }

    /**
     * 鎺ㄩ�侀�氱煡缁檃ndroid
     * <p>
     * 鍙傝鏂囨。 https://help.aliyun.com/document_detail/48087.html
     */
    @Test
    public void testPushNoticeToAndroid() throws Exception {

        PushNoticeToAndroidRequest androidRequest = new PushNoticeToAndroidRequest();
        //瀹夊叏鎬ф瘮杈冮珮鐨勫唴瀹瑰缓璁娇鐢℉TTPS
        androidRequest.setProtocol(ProtocolType.HTTPS);
        //鍐呭杈冨ぇ鐨勮姹傦紝浣跨敤POST璇锋眰
        androidRequest.setMethod(MethodType.POST);
        androidRequest.setAppKey(appKey);
        androidRequest.setTarget("TAG");
        androidRequest.setTargetValue("tag1");
        androidRequest.setTitle("title");
        androidRequest.setBody("Body");
        androidRequest.setExtParameters("{\"k1\":\"v1\"}");

        PushNoticeToAndroidResponse pushNoticeToAndroidResponse = client.getAcsResponse(androidRequest);
        System.out.printf("RequestId: %s, MessageId: %s\n",
                pushNoticeToAndroidResponse.getRequestId(), pushNoticeToAndroidResponse.getMessageId());

    }
    /**
     * 鎺ㄩ�佹秷鎭粰iOS
     * <p>
     * 鍙傝鏂囨。 https://help.aliyun.com/document_detail/48086.html
     */
    @Test
    public void testPushMessageToIOS() throws Exception {
        PushMessageToiOSRequest iOSRequest = new PushMessageToiOSRequest();
        //瀹夊叏鎬ф瘮杈冮珮鐨勫唴瀹瑰缓璁娇鐢℉TTPS
        iOSRequest.setProtocol(ProtocolType.HTTPS);
        //鍐呭杈冨ぇ鐨勮姹傦紝浣跨敤POST璇锋眰
        iOSRequest.setMethod(MethodType.POST);
        iOSRequest.setAppKey(appKey);
        iOSRequest.setTarget("DEVICE");
        iOSRequest.setTargetValue(deviceIds);
        iOSRequest.setTitle("title");
        iOSRequest.setBody("body");


        PushMessageToiOSResponse pushMessageToiOSResponse = client.getAcsResponse(iOSRequest);
        System.out.printf("RequestId: %s, MessageId: %s\n",
                pushMessageToiOSResponse.getRequestId(), pushMessageToiOSResponse.getMessageId());
    }

    /**
     * 鎺ㄩ�侀�氱煡缁檌OS
     * <p>
     * 鍙傝鏂囨。 https://help.aliyun.com/document_detail/48088.html
     */
    @Test
    public void testPushNoticeToIOS_toAll() throws Exception {

        PushNoticeToiOSRequest iOSRequest = new PushNoticeToiOSRequest();
        //瀹夊叏鎬ф瘮杈冮珮鐨勫唴瀹瑰缓璁娇鐢℉TTPS
        iOSRequest.setProtocol(ProtocolType.HTTPS);
        //鍐呭杈冨ぇ鐨勮姹傦紝浣跨敤POST璇锋眰
        iOSRequest.setMethod(MethodType.POST);
        iOSRequest.setAppKey(appKey);
        // iOS鐨勯�氱煡鏄�氳繃APNS涓績鏉ュ彂閫佺殑锛岄渶瑕佸～鍐欏搴旂殑鐜淇℃伅. DEV :琛ㄧず寮�鍙戠幆澧�, PRODUCT: 琛ㄧず鐢熶骇鐜
        iOSRequest.setApnsEnv("PRODUCT");
        iOSRequest.setTarget("DEVICE");
        iOSRequest.setTargetValue("e24155d9f3db4e3791e5444d737c81db");
        //iOSRequest.setTitle("eewwewe");
        iOSRequest.setBody("Body");
        iOSRequest.setExtParameters("{\"k1\":\"v1\",\"k2\":\"v2\"}");


        PushNoticeToiOSResponse pushNoticeToiOSResponse = client.getAcsResponse(iOSRequest);
        System.out.printf("RequestId: %s, MessageId: %s\n",
                pushNoticeToiOSResponse.getRequestId(), pushNoticeToiOSResponse.getMessageId());
    }
    /**
     * 鎺ㄩ�侀珮绾ф帴鍙�
     * <p>
     * 鍙傝鏂囨。 https://help.aliyun.com/document_detail/48089.html
     * //
     */
    @Test
    public void testAdvancedPush() throws Exception {

        PushRequest pushRequest = new PushRequest();
        //瀹夊叏鎬ф瘮杈冮珮鐨勫唴瀹瑰缓璁娇鐢℉TTPS
        pushRequest.setProtocol(ProtocolType.HTTPS);
        //鍐呭杈冨ぇ鐨勮姹傦紝浣跨敤POST璇锋眰
        pushRequest.setMethod(MethodType.POST);
        // 鎺ㄩ�佺洰鏍�
        pushRequest.setAppKey(appKey);
        pushRequest.setTarget("DEVICE"); //鎺ㄩ�佺洰鏍�: DEVICE:鎸夎澶囨帹閫� ALIAS : 鎸夊埆鍚嶆帹閫� ACCOUNT:鎸夊笎鍙锋帹閫�  TAG:鎸夋爣绛炬帹閫�; ALL: 骞挎挱鎺ㄩ��
        pushRequest.setTargetValue(deviceIds); //鏍规嵁Target鏉ヨ瀹氾紝濡俆arget=DEVICE, 鍒欏搴旂殑鍊间负 璁惧id1,璁惧id2. 澶氫釜鍊间娇鐢ㄩ�楀彿鍒嗛殧.(甯愬彿涓庤澶囨湁涓�娆℃渶澶�100涓殑闄愬埗)
//        pushRequest.setTarget("ALL"); //鎺ㄩ�佺洰鏍�: device:鎺ㄩ�佺粰璁惧; account:鎺ㄩ�佺粰鎸囧畾甯愬彿,tag:鎺ㄩ�佺粰鑷畾涔夋爣绛�; all: 鎺ㄩ�佺粰鍏ㄩ儴
//        pushRequest.setTargetValue("ALL"); //鏍规嵁Target鏉ヨ瀹氾紝濡俆arget=device, 鍒欏搴旂殑鍊间负 璁惧id1,璁惧id2. 澶氫釜鍊间娇鐢ㄩ�楀彿鍒嗛殧.(甯愬彿涓庤澶囨湁涓�娆℃渶澶�100涓殑闄愬埗)
        pushRequest.setPushType("NOTICE"); // 娑堟伅绫诲瀷 MESSAGE NOTICE
        pushRequest.setDeviceType("ALL"); // 璁惧绫诲瀷 ANDROID iOS ALL.


        // 鎺ㄩ�侀厤缃�
        pushRequest.setTitle("ALi Push Title"); // 娑堟伅鐨勬爣棰�
        pushRequest.setBody("Ali Push Body"); // 娑堟伅鐨勫唴瀹�

        // 鎺ㄩ�侀厤缃�: iOS
        pushRequest.setiOSBadge(5); // iOS搴旂敤鍥炬爣鍙充笂瑙掕鏍�
	pushRequest.setiOSSilentNotification(false);//寮�鍚潤榛橀�氱煡
        pushRequest.setiOSMusic("default"); // iOS閫氱煡澹伴煶
        pushRequest.setiOSSubtitle("iOS10 subtitle");//iOS10閫氱煡鍓爣棰樼殑鍐呭
        pushRequest.setiOSNotificationCategory("iOS10 Notification Category");//鎸囧畾iOS10閫氱煡Category
        pushRequest.setiOSMutableContent(true);//鏄惁鍏佽鎵╁睍iOS閫氱煡鍐呭
        pushRequest.setiOSApnsEnv("DEV");//iOS鐨勯�氱煡鏄�氳繃APNs涓績鏉ュ彂閫佺殑锛岄渶瑕佸～鍐欏搴旂殑鐜淇℃伅銆�"DEV" : 琛ㄧず寮�鍙戠幆澧� "PRODUCT" : 琛ㄧず鐢熶骇鐜
        pushRequest.setiOSRemind(true); // 娑堟伅鎺ㄩ�佹椂璁惧涓嶅湪绾匡紙鏃笌绉诲姩鎺ㄩ�佺殑鏈嶅姟绔殑闀胯繛鎺ラ�氶亾涓嶉�氾級锛屽垯杩欐潯鎺ㄩ�佷細鍋氫负閫氱煡锛岄�氳繃鑻规灉鐨凙PNs閫氶亾閫佽揪涓�娆°�傛敞鎰忥細绂荤嚎娑堟伅杞�氱煡浠呴�傜敤浜庣敓浜х幆澧�
        pushRequest.setiOSRemindBody("iOSRemindBody");//iOS娑堟伅杞�氱煡鏃朵娇鐢ㄧ殑iOS閫氱煡鍐呭锛屼粎褰搃OSApnsEnv=PRODUCT && iOSRemind涓簍rue鏃舵湁鏁�
        pushRequest.setiOSExtParameters("{\"_ENV_\":\"DEV\",\"k2\":\"v2\"}"); //閫氱煡鐨勬墿灞曞睘鎬�(娉ㄦ剰 : 璇ュ弬鏁拌浠son map鐨勬牸寮忎紶鍏�,鍚﹀垯浼氳В鏋愬嚭閿�)
        // 鎺ㄩ�侀厤缃�: Android
        pushRequest.setAndroidNotifyType("NONE");//閫氱煡鐨勬彁閱掓柟寮� "VIBRATE" : 闇囧姩 "SOUND" : 澹伴煶 "BOTH" : 澹伴煶鍜岄渿鍔� NONE : 闈欓煶
        pushRequest.setAndroidNotificationBarType(1);//閫氱煡鏍忚嚜瀹氫箟鏍峰紡0-100
        pushRequest.setAndroidNotificationBarPriority(1);//閫氱煡鏍忚嚜瀹氫箟鏍峰紡0-100
        pushRequest.setAndroidOpenType("URL"); //鐐瑰嚮閫氱煡鍚庡姩浣� "APPLICATION" : 鎵撳紑搴旂敤 "ACTIVITY" : 鎵撳紑AndroidActivity "URL" : 鎵撳紑URL "NONE" : 鏃犺烦杞�
        pushRequest.setAndroidOpenUrl("http://www.aliyun.com"); //Android鏀跺埌鎺ㄩ�佸悗鎵撳紑瀵瑰簲鐨剈rl,浠呭綋AndroidOpenType="URL"鏈夋晥
        pushRequest.setAndroidActivity("com.alibaba.push2.demo.XiaoMiPushActivity"); // 璁惧畾閫氱煡鎵撳紑鐨刟ctivity锛屼粎褰揂ndroidOpenType="Activity"鏈夋晥
        pushRequest.setAndroidMusic("default"); // Android閫氱煡闊充箰
        pushRequest.setAndroidXiaoMiActivity("com.ali.demo.MiActivity");//璁剧疆璇ュ弬鏁板悗鍚姩灏忕背鎵樼寮圭獥鍔熻兘, 姝ゅ鎸囧畾閫氱煡鐐瑰嚮鍚庤烦杞殑Activity锛堟墭绠″脊绐楃殑鍓嶆彁鏉′欢锛�1. 闆嗘垚灏忕背杈呭姪閫氶亾锛�2. StoreOffline鍙傛暟璁句负true锛�
        pushRequest.setAndroidXiaoMiNotifyTitle("Mi title");
        pushRequest.setAndroidXiaoMiNotifyBody("MiActivity Body");
        pushRequest.setAndroidExtParameters("{\"k1\":\"android\",\"k2\":\"v2\"}"); //璁惧畾閫氱煡鐨勬墿灞曞睘鎬с��(娉ㄦ剰 : 璇ュ弬鏁拌浠� json map 鐨勬牸寮忎紶鍏�,鍚﹀垯浼氳В鏋愬嚭閿�)


//        // 鎺ㄩ�佹帶鍒�
        Date pushDate = new Date(System.currentTimeMillis()) ; // 30绉掍箣闂寸殑鏃堕棿鐐�, 涔熷彲浠ヨ缃垚浣犳寚瀹氬浐瀹氭椂闂�
        String pushTime = ParameterHelper.getISO8601Time(pushDate);
        pushRequest.setPushTime(pushTime); // 寤跺悗鎺ㄩ�併�傚彲閫夛紝濡傛灉涓嶈缃〃绀虹珛鍗虫帹閫�
        String expireTime = ParameterHelper.getISO8601Time(new Date(System.currentTimeMillis() + 12 * 3600 * 1000)); // 12灏忔椂鍚庢秷鎭け鏁�, 涓嶄細鍐嶅彂閫�
        pushRequest.setExpireTime(expireTime);
        pushRequest.setStoreOffline(true); // 绂荤嚎娑堟伅鏄惁淇濆瓨,鑻ヤ繚瀛�, 鍦ㄦ帹閫佹椂鍊欙紝鐢ㄦ埛鍗充娇涓嶅湪绾匡紝涓嬩竴娆′笂绾垮垯浼氭敹鍒�


        PushResponse pushResponse = client.getAcsResponse(pushRequest);
        System.out.printf("RequestId: %s, MessageID: %s\n",
                    pushResponse.getRequestId(), pushResponse.getMessageId());



    }

    /**
     * 鍙栨秷瀹氭椂鎺ㄩ��
     * <p>
     * //
     */
    @Test
    public void testCancelPush() throws Exception {
        CancelPushRequest request = new CancelPushRequest();
        request.setAppKey(appKey);
        request.setMessageId("510456");
        CancelPushResponse response = client.getAcsResponse(request);
        System.out.println(response.getRequestId());

    }

}
