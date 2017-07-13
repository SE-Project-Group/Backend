package model;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Comment {
	private int userId;
	private String text;
	private String time;
	private int commentId;
//reply_id : 如果回复动态为0，如果回复评论的话为被回复评论的ID
	private int replyId;
	public Comment(){
		 SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	    	this.setTime(df.format(new Date(System.currentTimeMillis())));
	}
	public Comment(int userId,int replyId,String text){
		this.userId=userId;
		this.replyId=replyId;
		this.setText(text);
		 SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	    	this.setTime(df.format(new Date(System.currentTimeMillis())));
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public int getCommentId() {
		return commentId;
	}
	public void setCommentId(int commentId) {
		this.commentId = commentId;
	}
	public int getReplyId() {
		return replyId;
	}
	public void setReplyId(int replyId) {
		this.replyId = replyId;
	}
	
}
