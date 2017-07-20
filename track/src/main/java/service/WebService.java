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
	 * web�˹���Ա��½
	 * @param adminName
	 * @param password
	 * @return
	 */
	public boolean managerLogin(String adminName,String password);
	/**
	 * �ҳ�����Ϊdate�����е�feed
	 * @param date
	 * @return
	 */
	public List<Feed> getTodayFeedList(Date date);
	/*
	 * best feed
	 * 
	 */
	/**
	 * ��idΪfeedId�ķ�������Ϊ��ѷ���
	 * @param feedId
	 * @return
	 * @throws ParseException
	 */
	public String setBestFeed(String feedId) throws ParseException;
}
