package model;

public class Like {

	
	private String _id;
	private String feed_id;
	private String time;
	private int user_id;
	    
	public void set_id(String _id) {
	         this._id = _id;
	     }
	public String get_id() {
	         return _id;
	     }

	public void setFeed_id(String feed_id) {
	         this.feed_id = feed_id;
	     }
	public String getFeed_id() {
	         return feed_id;
	     }

	public void setTime(String time) {
	         this.time = time;
	     }
	public String getTime() {
	         return time;
	     }

	public void setUser_id(int user_id) {
	         this.user_id = user_id;
	     }
	 public int getUser_id() {
	         return user_id;
	     }

	


}
