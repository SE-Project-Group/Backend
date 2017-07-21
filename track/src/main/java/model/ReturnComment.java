package model;

public class ReturnComment {
	private String portrait_url;
	private int user_id;
	private String user_name;
	private String comment_text;
	private int reply_id;
	private String time;
	   
	public ReturnComment(String portrait_url,int user_id,String user_name,String comment_text,int reply_id,String time){
		this.portrait_url=portrait_url;
		this.user_id=user_id;
		this.user_name=user_name;
		this.comment_text=comment_text;
		this.reply_id=reply_id;
		this.time=time;
	}
	
	public String getPortrait_url() {
		return portrait_url;
	}
	public void setPortrait_url(String portrait_url) {
		this.portrait_url = portrait_url;
	}
	public int getUser_id() {
		return user_id;
	}
	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}
	public String getUser_name() {
		return user_name;
	}
	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}
	public String getComment_text() {
		return comment_text;
	}
	public void setComment_text(String comment_text) {
		this.comment_text = comment_text;
	}

	public int getReply_id() {
		return reply_id;
	}

	public void setReply_id(int reply_id) {
		this.reply_id = reply_id;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}
}
