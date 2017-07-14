package action;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import model.Feed;
import service.AppService;

public class FeedAction extends BaseAction{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private AppService appService;
	
	public AppService getAppService() {
		return appService;
	}

	public void setAppService(AppService appService) {
		this.appService = appService;
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
		for(int i=0;i<10&&i<feeds.size();i++){
			res.add(feeds.get(i));
		}
		request().setAttribute("feeds", res);
		return SUCCESS;
	}

	
	
}
