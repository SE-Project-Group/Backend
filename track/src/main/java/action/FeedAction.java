package action;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.internal.ResponseParsers.ListImageStyleResponseParser;

import model.Feed;
import service.AppService;

public class FeedAction extends BaseAction{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String feedId;
	private int picCount;
	
	private AppService appService;
	
	public AppService getAppService() {
		return appService;
	}

	public void setAppService(AppService appService) {
		this.appService = appService;
	}
	
	
	public void setFeedId(String feedId) {
		this.feedId = feedId;
	}


	public void setPicCount(int picCount) {
		this.picCount = picCount;
	}

	public String getBestFeed(){
		Date date=new Date(System.currentTimeMillis());
		List<Feed> feeds=appService.getTodayFeedList(date);
		Collections.sort(feeds,new Comparator<Feed>() {
            @Override
            public int compare(Feed f1,Feed f2) {
                if(f1.getLikeCount()>f2.getLikeCount())return 1;
                else if(f1.getLikeCount()<f2.getLikeCount())return -1;
                else return 0;
            }
        });
		List<Feed> res=new ArrayList<Feed>();
		for(int i=0;i<20&&i<feeds.size();i++){
			res.add(feeds.get(i));
		}
		request().setAttribute("feeds", res);
		return SUCCESS;
	}
	
	
	public void getURL() throws Exception{
		String accessKeyId = "LTAIdLRXuEkhtIYE";
		String accessKeySecret = "bQ0VEJQkWXKwZ4NBlCMVe6YPVbuC4y";
		String endpoint = "http://oss-cn-shanghai.aliyuncs.com";
		String bucket = "sjtutest";
		java.util.Date expiration = new java.util.Date((new java.util.Date().getTime() + 3600 * 1000));
		OSSClient client = new OSSClient(endpoint, accessKeyId, accessKeySecret);
		
		List<String> list = new ArrayList<>();
		for(int i = 1; i <= picCount; i++){
			String key = feedId + "_" + String.valueOf(i);
			URL url = client.generatePresignedUrl(bucket, key, expiration);
			list.add(url.toString());
		}
		String listStr = list.toString();
		response().getWriter().write(listStr);
	}

	
	
}
