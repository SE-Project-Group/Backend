package test;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import org.junit.BeforeClass;

import java.io.InputStream;
import java.util.Properties;

import static org.junit.Assert.assertNotNull;

/**
 * ���͵�OpenAPI�ĵ� https://help.aliyun.com/document_detail/mobilepush/api-reference/openapi.html
 */
public class BaseTest {

    protected static String region;
    protected static long appKey;
    protected static String deviceIds;
    protected static String deviceId;
    protected static String accounts;
    protected static String account;
    protected static String aliases;
    protected static String alias;
    protected static String tag;
    protected static String tagExpression;

    protected static DefaultAcsClient client;

    /**
     * �������ļ��ж�ȡ����ֵ����ʼ��Client
     * <p>
     * 1. ��λ�ȡ accessKeyId/accessKeySecret/appKey �ռ�README.md �е�˵��<br/>
     * 2. ���� push.properties �����ļ��� ������Ļ�ȡ��ֵ
     */
    @BeforeClass
    public static void beforeClass() throws Exception {
        InputStream inputStream = BaseTest.class.getClassLoader().getResourceAsStream("push.properties");
        Properties properties = new Properties();
        properties.load(inputStream);

        String accessKeyId = properties.getProperty("accessKeyId");
        assertNotNull("���� push.properties �����ļ������� accessKeyId", accessKeyId);

        String accessKeySecret = properties.getProperty("accessKeySecret");
        assertNotNull("���� push.properties �����ļ������� accessKeySecret", accessKeySecret);

        String key = properties.getProperty("appKey");
        assertNotNull("���� push.properties �����ļ������� appKey", key);

        region = properties.getProperty("regionId");
        appKey = Long.valueOf(key);
        deviceIds = properties.getProperty("deviceIds");
        deviceId = properties.getProperty("deviceId");
        accounts = properties.getProperty("accounts");
        account = properties.getProperty("account");
        aliases = properties.getProperty("aliases");
        alias = properties.getProperty("alias");
        tag = properties.getProperty("tag");
        tagExpression = properties.getProperty("tagExpression");

        IClientProfile profile = DefaultProfile.getProfile(region, accessKeyId, accessKeySecret);
        client = new DefaultAcsClient(profile);
    }
}
