package test;

import com.aliyuncs.push.model.v20160801.*;
import org.junit.Test;

import java.util.List;

/**
 * Created by lingbo on 16/8/15.
 */
public class TagTest extends BaseTest {

    /**
     * TAG鍒楄〃
     * 鍙傝�冩枃妗� 锛歨ttps://help.aliyun.com/document_detail/48082.html
     */

    @Test
    public void testListTags() throws Exception {

        ListTagsRequest request = new ListTagsRequest();
        request.setAppKey(appKey);

        ListTagsResponse response = client.getAcsResponse(request);
        List<ListTagsResponse.TagInfo> tagInfos = response.getTagInfos();
        for (ListTagsResponse.TagInfo tagInfo : tagInfos) {
            System.out.printf("tagName: %s \n" , tagInfo.getTagName());
        }

    }

    /**
     * 鏌ヨTAG
     * https://help.aliyun.com/document_detail/48081.html?
     */

    @Test
    public void testQueryTag() throws Exception {

        QueryTagsRequest request = new QueryTagsRequest();
        request.setAppKey(appKey);
        request.setKeyType("DEVICE");//DEVICE锛氭槸璁惧锛孉CCOUNT锛氭槸璐﹀彿锛孉LIAS锛氭槸鍒悕
        request.setClientKey(deviceIds);


        QueryTagsResponse response = client.getAcsResponse(request);
        System.out.printf("RequestId: %s, tags: %s\n",
                response.getRequestId(), response.getTagInfos().size());
        for(QueryTagsResponse.TagInfo info : response.getTagInfos()){
            System.out.println("tagName : " + info.getTagName());
        }

    }
    /**
     * 缁戝畾TAG
     * 鍙傝�冩枃妗� 锛歨ttps://help.aliyun.com/document_detail/48083.html
     */
    @Test
    public void testBindTag() throws Exception {

        BindTagRequest request = new BindTagRequest();
        request.setAppKey(appKey);
        request.setKeyType("DEVICE");//DEVICE锛氭槸璁惧锛孉CCOUNT锛氭槸璐﹀彿锛孉LIAS锛氭槸鍒悕
        request.setClientKey(deviceIds);
        request.setTagName(tag);//tag闀垮害闄愬埗涓�128Byte,涓�涓狝pp鏈�澶氱粦瀹�128涓猼ag

        BindTagResponse response = client.getAcsResponse(request);
        System.out.printf("RequestId: %s\n",
                response.getRequestId());

    }
    /**
     * 瑙ｇ粦TAG
     * 鍙傝�冩枃妗� 锛歨ttps://help.aliyun.com/document_detail/48084.html
     */
    @Test
    public void testUnbindTag() throws Exception {

        UnbindTagRequest request = new UnbindTagRequest();
        request.setAppKey(appKey);
        request.setClientKey(deviceIds);
        request.setKeyType("DEVICE");//DEVICE锛氭槸璁惧锛孉CCOUNT锛氭槸璐﹀彿锛孉LIAS锛氭槸鍒悕
        request.setTagName(tag);

        UnbindTagResponse response = client.getAcsResponse(request);
        System.out.printf("RequestId: %s\n",
                response.getRequestId());

    }

}
