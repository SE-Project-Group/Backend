package model;

import java.io.Serializable;
import java.sql.Date;

public class BestFeed implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String feedId;
	private Date date;
	
	public BestFeed(){
		
	}
	
	public BestFeed(String feedId,Date date){
		this.feedId=feedId;
		this.date=date;
	}
	
	public String getFeedId() {
		return feedId;
	}
	public void setFeedId(String feedId) {
		this.feedId = feedId;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
}
