package test;

import com.aliyuncs.push.model.v20160801.*;
import com.aliyuncs.utils.ParameterHelper;
import java.util.Date;
import org.junit.Test;

/**
 * Created by lingbo on 16/8/25.
 */
public class StatTest extends BaseTest {

    /**
     * 浠诲姟缁村害鎺ㄩ�佺粺璁�
     * 鏌ヨ: 鍙戦�佹暟,鍒拌揪鏁�,鎵撳紑鏁�,鍒犻櫎鏁�
     * 鍙傝�冩枃妗� 锛歨ttps://help.aliyun.com/document_detail/48097.html
     */
    @Test
    public void testQueryPushStatByMsg() throws Exception {
        QueryPushStatByMsgRequest request = new QueryPushStatByMsgRequest();
        request.setAppKey(appKey);
        request.setMessageId("500028");//娑堟伅鎺ㄩ�佸悗杩斿洖鐨凪essageID

        QueryPushStatByMsgResponse response = client.getAcsResponse(request);
        System.out.printf("RequestId: %s\n" , response.getRequestId());

        for (QueryPushStatByMsgResponse.PushStat item : response.getPushStats()) {
            System.out.printf("MessageId: %s , SentCount: %s, ReceivedCount: %s, OpenedCount: %s, DeletedCount: %s\n",
                    item.getMessageId(), item.getSentCount() ,item.getReceivedCount(),item.getOpenedCount(),item.getDeletedCount());
        }
    }

    /**
     * APP缁村害鎺ㄩ�佺粺璁�
     * 鍙傝�冩枃妗� 锛歨ttps://help.aliyun.com/document_detail/48093.html
     */
    @Test
    public void testQueryPushStatByApp() throws Exception {

        QueryPushStatByAppRequest request = new QueryPushStatByAppRequest();
        request.setAppKey(appKey);
        request.setGranularity("DAY");//DAY: 澶╃矑搴�

        Date startDate = new Date(System.currentTimeMillis() - 3 * 24 * 3600 * 1000);
        String startTime = ParameterHelper.getISO8601Time(startDate);
        Date endDate = new Date(System.currentTimeMillis());
        String endTime = ParameterHelper.getISO8601Time(endDate);

        request.setStartTime(startTime);
        request.setEndTime(endTime);

        QueryPushStatByAppResponse response = client.getAcsResponse(request);
        System.out.printf("RequestId: %s\n",response.getRequestId());

        for (QueryPushStatByAppResponse.AppPushStat item : response.getAppPushStats()) {
            System.out.printf("Time: %s , SentCount: %s, ReceivedCount: %s, OpenedCount: %s, DeletedCount: %s\n",
                    item.getTime(), item.getSentCount() ,item.getReceivedCount(),item.getOpenedCount(),item.getDeletedCount());
        }
    }

    /**
     * 璁惧鏂板涓庣暀瀛�
     * 鍙傝�冩枃妗� 锛歨ttps://help.aliyun.com/document_detail/48094.html
     */
    @Test
    public void testQueryDeviceStat() throws Exception {
        QueryDeviceStatRequest request = new QueryDeviceStatRequest();
        request.setAppKey(appKey);
        request.setQueryType("NEW");//NEW: 鏂板璁惧鏌ヨ, TOTAL: 鐣欏瓨璁惧鏌ヨ
        request.setDeviceType("iOS");//iOS,ANDROID,ALL

        Date startDate = new Date(System.currentTimeMillis() - 7 * 24 * 3600 * 1000);
        String startTime = ParameterHelper.getISO8601Time(startDate);
        Date endDate = new Date(System.currentTimeMillis());
        String endTime = ParameterHelper.getISO8601Time(endDate);


        request.setStartTime(startTime);
        request.setEndTime(endTime);

        QueryDeviceStatResponse response = client.getAcsResponse(request);
        System.out.printf("RequestId: %s\n",response.getRequestId());

        for (QueryDeviceStatResponse.AppDeviceStat stat : response.getAppDeviceStats()) {
            System.out.printf("Time: %s, DeviceType: %s, Count: %s\n",
                    stat.getTime(), stat.getDeviceType(), stat.getCount());
        }
    }

    /**
     * 鍘婚噸璁惧缁熻
     * 鍙傝�冩枃妗� 锛歨ttps://help.aliyun.com/document_detail/48092.html
     */
    @Test
    public void testQueryUniqueDeviceStat() throws Exception {

        QueryUniqueDeviceStatRequest request = new QueryUniqueDeviceStatRequest();
        request.setAppKey(appKey);
        request.setGranularity("DAY");//DAY: 澶╃矑搴� MONTH: 鏈堢矑搴�

        Date startDate = new Date(System.currentTimeMillis() - 7 * 24 * 3600 * 1000);
        String startTime = ParameterHelper.getISO8601Time(startDate);
        Date endDate = new Date(System.currentTimeMillis());
        String endTime = ParameterHelper.getISO8601Time(endDate);


        request.setStartTime(startTime);
        request.setEndTime(endTime);

        QueryUniqueDeviceStatResponse response = client.getAcsResponse(request);
        System.out.printf("RequestId: %s\n",response.getRequestId());

        for (QueryUniqueDeviceStatResponse.AppDeviceStat stat : response.getAppDeviceStats()) {
            System.out.printf("Time: %s, Count: %s\n",
                    stat.getTime(), stat.getCount());
        }
    }

}
