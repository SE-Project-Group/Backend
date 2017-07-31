package dao.impl;

import java.sql.Date;
import java.util.List;

import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import dao.BestFeedDao;
import model.BestFeed;

public class BestFeedDaoImpl extends HibernateDaoSupport implements BestFeedDao{

	@Override
	public BestFeed getBestFeedById(String feedId) {
		@SuppressWarnings("unchecked")
		List<BestFeed> bestFeeds = (List<BestFeed>) getHibernateTemplate().find("from BestFeed as bf where bf.feedId=?", feedId);
		BestFeed bestFeed = bestFeeds.size() > 0 ? bestFeeds.get(0) : null;
		return bestFeed;
	}
	
	@Override
	public void insert(BestFeed bestFeed) {
		getHibernateTemplate().save(bestFeed);
	}

	@Override
	public void delete(BestFeed bestFeed) {
		getHibernateTemplate().delete(bestFeed);	
	}

	@Override
	public List<BestFeed> getAllByDate(Date date) {
		@SuppressWarnings("unchecked")
		List<BestFeed> bestFeeds = (List<BestFeed>) getHibernateTemplate().find("from BestFeed as bf where bf.date=?", date);
		return bestFeeds;
	}

	

}
