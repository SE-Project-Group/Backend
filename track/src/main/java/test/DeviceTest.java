package test;

import com.alibaba.fastjson.JSON;
import com.aliyuncs.push.model.v20160801.CheckDeviceRequest;
import com.aliyuncs.push.model.v20160801.CheckDeviceResponse;
import com.aliyuncs.push.model.v20160801.QueryDeviceInfoRequest;
import com.aliyuncs.push.model.v20160801.QueryDeviceInfoResponse;
import org.junit.Test;

/**
 * Created by lingbo on 2016/12/15.
 * 璁惧鐩稿叧鎺ュ彛demo
 */
public class DeviceTest extends BaseTest {

    /**
     * 鏌ヨ璁惧鏄惁涓烘湁鏁堣澶�
     * 鍙傝�冩枃妗ｏ細https://help.aliyun.com/document_detail/48300.html
     * */
    @Test
    public void testCheckDevice() throws Exception {
        CheckDeviceRequest request = new CheckDeviceRequest();
        request.setAppKey(appKey);
        request.setDeviceId(deviceIds);
        CheckDeviceResponse response = client.getAcsResponse(request);
        System.out.print("Available: " + response.getAvailable());
    }

    /**
     * 鏌ヨ璁惧淇℃伅
     * 鍙傝�冩枃妗ｏ細https://help.aliyun.com/document_detail/48098.html
     */
    @Test
    public void testQueryDeviceInfo() throws Exception {

        QueryDeviceInfoRequest request = new QueryDeviceInfoRequest();
        request.setAppKey(appKey);
        request.setDeviceId(deviceIds);

        QueryDeviceInfoResponse response = client.getAcsResponse(request);
        System.out.printf("RequestId: %s\n", response.getRequestId());
        QueryDeviceInfoResponse.DeviceInfo deviceInfo = response.getDeviceInfo();
        System.out.print(JSON.toJSONString(deviceInfo));

    }

}
