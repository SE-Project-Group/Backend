package service;

import java.sql.Date;
import java.text.ParseException;
import java.util.List;

import model.Feed;

public  interface WebService {
	
	
	
	/*
	 * 
	 * Admin
	 */
	/**
	 * web端管理员登陆
	 * @param adminName
	 * @param password
	 * @return
	 */
	public boolean managerLogin(String adminName,String password);
	/**
	 * 找出日期为date的所有的feed
	 * @param date
	 * @return
	 */
	public List<Feed> getTodayFeedList(Date date);
	/*
	 * best feed
	 * 
	 */
	/**
	 * 将id为feedId的分享设置为最佳分享
	 * @param feedId
	 * @return
	 * @throws ParseException
	 */
	public String setBestFeed(String feedId) throws ParseException;
}
