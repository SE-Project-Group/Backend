package service.impl;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import dao.BestFeedDao;
import dao.FeedRepository;
import dao.ManagerDao;
import model.BestFeed;
import model.Feed;
import service.WebService;

public class WebServiceImpl implements WebService{
private ManagerDao managerDao;
	private FeedRepository feedRepository;
	private BestFeedDao bestFeedDao;
	
	public ManagerDao getManagerDao() {
		return managerDao;
	}

	public void setManagerDao(ManagerDao managerDao) {
		this.managerDao = managerDao;
	}
	public  FeedRepository getFeedRepository() {
		return feedRepository;
	}

	public void setFeedRepository(FeedRepository feedRepository) {
		this.feedRepository = feedRepository;
	}
	public BestFeedDao getBestFeedDao() {
		return bestFeedDao;
	}

	public void setBestFeedDao(BestFeedDao bestFeedDao) {
		this.bestFeedDao = bestFeedDao;
	}



	@Override
	public boolean managerLogin(String adminName, String password) {
		// TODO Auto-generated method stub
		return managerDao.checkLogin(adminName, password);
	}

	@Override
	public List<Feed> getTodayFeedList(Date date) {
		// TODO Auto-generated method stub
         List<Feed>feeds= feedRepository.getTodayFeedList(date);
		
		return feeds;
	}

	@Override
	public String setBestFeed(String feedId) throws ParseException {
		// TODO Auto-generated method stub
		Calendar calendar = Calendar.getInstance();
		SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd");
		String dateString = df.format(calendar.getTime());
		java.util.Date dateTemp=df.parse(dateString);
		Date date=new Date(dateTemp.getTime());
        BestFeed bestFeed=new BestFeed(feedId,date);
        List<BestFeed>bestFeeds=bestFeedDao.getAllByDate(date);
        for(int i=0;i<bestFeeds.size();i++){
        	if(bestFeeds.get(i).getFeedId().equals(feedId)){
        		return "It is one of the best feeds already!";
        	}
        }
        if(bestFeeds.size()>=10){
        	return "10 best feeds at most!";
        }
        bestFeedDao.insert(bestFeed);
        return "Set successfully!";
	}

}
