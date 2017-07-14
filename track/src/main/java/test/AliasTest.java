package test;

import com.aliyuncs.push.model.v20160801.*;
import org.junit.Test;

/**
 * Created by lingbo on 16/8/15.
 */
public class AliasTest extends BaseTest {

    /**
     * 鏌ヨ鍒悕
     * 鍙傝�冩枃妗� 锛歨ttps://help.aliyun.com/document_detail/48078.html
     */
    @Test
    public void testQueryAliases() throws Exception {

        QueryAliasesRequest request = new QueryAliasesRequest();
        request.setAppKey(appKey);
        request.setDeviceId(deviceId);

        QueryAliasesResponse response = client.getAcsResponse(request);
        System.out.printf("RequestId: %s\n",
                response.getRequestId());
        for(QueryAliasesResponse.AliasInfo aliasInfo : response.getAliasInfos()){
            System.out.println("aliasName: " + aliasInfo.getAliasName());
        }

    }

    /**
     * 缁戝畾鍒悕
     * 鍙傝�冩枃妗� 锛歨ttps://help.aliyun.com/document_detail/48079.html
     */

    @Test
    public void testBindAlias() throws Exception {

        BindAliasRequest request = new BindAliasRequest();
        request.setAppKey(appKey);
        request.setDeviceId(deviceId);
        request.setAliasName(alias);

        BindAliasResponse response = client.getAcsResponse(request);
        System.out.printf("RequestId: %s\n",
                response.getRequestId());

    }

    /**
     * 瑙ｇ粦鍒悕
     * https://help.aliyun.com/document_detail/48080.html
     */

    @Test
    public void testUnbindAlias() throws Exception {

        UnbindAliasRequest request = new UnbindAliasRequest();
        request.setAppKey(appKey);
        request.setDeviceId(deviceId);
        request.setAliasName(alias);

        UnbindAliasResponse response = client.getAcsResponse(request);
        System.out.printf("RequestId: %s\n",
                response.getRequestId());

    }

}
