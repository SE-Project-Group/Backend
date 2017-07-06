package model;

import java.sql.Timestamp;
import java.util.List;

public class ReturnFeed {
	private String feed_owner;
	private Timestamp timestamp;
	private String text;
	private int like_cnt;
	private int share_cnt;
	private int comment_cnt;
	private List<Integer> pic_id_list;
	private int user_ID;
	private String position;
	private double latitude;
	private double longitude;
	
	
	public String getFeed_owner() {
		return feed_owner;
	}
	public void setFeed_owner(String feed_owner) {
		this.feed_owner = feed_owner;
	}
	public Timestamp getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(Timestamp timestamp) {
		this.timestamp = timestamp;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public int getLike_cnt() {
		return like_cnt;
	}
	public void setLike_cnt(int like_cnt) {
		this.like_cnt = like_cnt;
	}
	public int getShare_cnt() {
		return share_cnt;
	}
	public void setShare_cnt(int share_cnt) {
		this.share_cnt = share_cnt;
	}
	public int getComment_cnt() {
		return comment_cnt;
	}
	public void setComment_cnt(int comment_cnt) {
		this.comment_cnt = comment_cnt;
	}
	public List<Integer> getPic_id_list() {
		return pic_id_list;
	}
	public void setPic_id_list(List<Integer> pic_id_list) {
		this.pic_id_list = pic_id_list;
	}
	public int getUser_ID() {
		return user_ID;
	}
	public void setUser_ID(int user_ID) {
		this.user_ID = user_ID;
	}
	public String getPosition() {
		return position;
	}
	public void setPosition(String position) {
		this.position = position;
	}
	public double getLatitude() {
		return latitude;
	}
	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}
	public double getLongitude() {
		return longitude;
	}
	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}
}
