package dao;

import java.sql.Date;
import java.util.List;

import model.BestFeed;

public interface BestFeedDao {
	public BestFeed getBestFeedById(String feedId);
	public void insert(BestFeed bestFeed);
	public void delete(BestFeed bestFeed);
	public List<BestFeed> getAllByDate(Date date);
}
