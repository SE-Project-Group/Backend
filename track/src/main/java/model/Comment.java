package model;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Comment {
	private int user_id;
	private String text;
	private String time;
	private int comment_id;
//reply_id : 如果回复动态为0，如果回复评论的话为被回复评论的ID
	private int reply_id;
	public Comment(){
		 SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	    	this.setTime(df.format(new Date(System.currentTimeMillis())));
	}
	public Comment(int user_id,int reply_id,String text){
		this.user_id=user_id;
		this.reply_id=reply_id;
		this.text=text;
		 SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	    	this.setTime(df.format(new Date(System.currentTimeMillis())));
	}

public void setUser_id(int user_id) {
        this.user_id = user_id;
    }
public int getUser_id() {
        return user_id;
    }
public void setTime(String time) {
    this.time = time;
}
public String getTime() {
    return time;
}
public String getText() {
    return text;
}
public void setText(String text) {
    this.text = text;
}
public void setComment_id(int comment_id) {
    this.comment_id = comment_id;
}
public int getComment_id() {
    return comment_id;
}	
public void setReply_id(int reply_id) {
    this.reply_id = reply_id;
}
public int getReply_id() {
    return reply_id;
}		
}
