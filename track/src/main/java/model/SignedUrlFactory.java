package model;

import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.aliyun.oss.HttpMethod;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.GeneratePresignedUrlRequest;
import com.aliyun.oss.model.GetObjectRequest;

public class SignedUrlFactory {
	private final static String accessKeyId = "LTAIdLRXuEkhtIYE";
	private final static String accessKeySecret = "bQ0VEJQkWXKwZ4NBlCMVe6YPVbuC4y";
	private final static String endpoint = "http://oss-cn-shanghai.aliyuncs.com";
	private final static String bucket = "sjtutest";
	
	// get thumbnail pic urls
	public List<String> getPicUrls(String feedId, int picCount){
		Date expiration = new Date((new java.util.Date().getTime() + 3600 * 1000));
		OSSClient client = new OSSClient(endpoint, accessKeyId, accessKeySecret);
		List<String> list = new ArrayList<>();
		for(int i = 1; i <= picCount; i++){
			String key = feedId + "_" + String.valueOf(i);
			String style = "style/thumbnail";
			
			GeneratePresignedUrlRequest req = new GeneratePresignedUrlRequest(bucket, key, HttpMethod.GET);
		    req.setExpiration(expiration);
		    req.setProcess(style);
		    
			URL url = client.generatePresignedUrl(req);
			list.add(url.toString());
		}
		
		return list;
	}
	
	// get big pic urls
	public List<String> getBigPicUrls(String feedId, int picCount){
		Date expiration = new Date((new java.util.Date().getTime() + 3600 * 1000));
		OSSClient client = new OSSClient(endpoint, accessKeyId, accessKeySecret);
		List<String> list = new ArrayList<>();
		for(int i = 1; i <= picCount; i++){
			String key = feedId + "_" + String.valueOf(i);
			String style = "style/big_pic";
			
			GeneratePresignedUrlRequest req = new GeneratePresignedUrlRequest(bucket, key, HttpMethod.GET);
		    req.setExpiration(expiration);
		    req.setProcess(style);
		    
			URL url = client.generatePresignedUrl(req);
			list.add(url.toString());
		}
		
		return list;
	}
	
	public String getPortraitUrl(int user_id){
		Date expiration = new Date((new java.util.Date().getTime() + 3600 * 1000));
		OSSClient client = new OSSClient(endpoint, accessKeyId, accessKeySecret);
		String key = user_id + "_portrait";
		String style = "style/thumbnail_portrait";
		
		GeneratePresignedUrlRequest req = new GeneratePresignedUrlRequest(bucket, key, HttpMethod.GET);
	    req.setExpiration(expiration);
	    req.setProcess(style);
	    
		URL url = client.generatePresignedUrl(req);
		return url.toString();
	}
	
	public String getBigPortraitUrl(int user_id){
		Date expiration = new Date((new java.util.Date().getTime() + 3600 * 1000));
		OSSClient client = new OSSClient(endpoint, accessKeyId, accessKeySecret);
		String key = user_id + "_portrait";
		String style = "style/big_pic";
		
		GeneratePresignedUrlRequest req = new GeneratePresignedUrlRequest(bucket, key, HttpMethod.GET);
	    req.setExpiration(expiration);
	    req.setProcess(style);
	    
		URL url = client.generatePresignedUrl(req);
		return url.toString();
	}
	
	public String getOriginPhoto(String file_name){
		Date expiration = new Date((new java.util.Date().getTime() + 300 * 1000));  // expiration time is 5 min
		OSSClient client = new OSSClient(endpoint, accessKeyId, accessKeySecret);
		String key = file_name;
		String style = "style/watermark_origin";
		
		GeneratePresignedUrlRequest req = new GeneratePresignedUrlRequest(bucket, key, HttpMethod.GET);
	    req.setExpiration(expiration);
	    req.setProcess(style);
	    
		URL url = client.generatePresignedUrl(req);
		return url.toString();
	}
}
