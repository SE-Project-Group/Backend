package model;

import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.GeneratePresignedUrlRequest;

public class SignedUrlFactory {
	private final static String accessKeyId = "LTAIdLRXuEkhtIYE";
	private final static String accessKeySecret = "bQ0VEJQkWXKwZ4NBlCMVe6YPVbuC4y";
	private final static String endpoint = "http://oss-cn-shanghai.aliyuncs.com";
	private final static String bucket = "sjtutest";
	
	public List<String> getUrls(String feedId, int picCount){
		
		Date expiration = new Date((new java.util.Date().getTime() + 3600 * 1000));
		OSSClient client = new OSSClient(endpoint, accessKeyId, accessKeySecret);
		List<String> list = new ArrayList<>();
		for(int i = 1; i <= picCount; i++){
			String key = feedId + "_" + String.valueOf(i);
			URL url = client.generatePresignedUrl(bucket, key, expiration);
			list.add(url.toString());
		}
		
		return list;
	}
}
