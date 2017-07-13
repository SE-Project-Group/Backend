package action;

import java.sql.Date;
import java.util.List;

import model.Feed;
import service.AppService;

public class BestFeedAction  extends BaseAction{
	private static final long serialVersionUID = 1L;
	
private Date time;


private AppService appService;
public void setAppService(AppService appService) {
	this.appService = appService;
}
public Date getTime() {
	return time;
}

public void setTime(Date time) {
	this.time = time;
}


public String getFeedList(){
	
	List<Feed> feeds = appService.getTodayFeedList(time);
	request().setAttribute("feeds", feeds);
	return SUCCESS;
	
}
}
