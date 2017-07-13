package model;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Like {

	
	private String time;
	private int userId;
	    
	public Like(){
		 SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	    	this.setTime(df.format(new Date(System.currentTimeMillis())));
	}
	public void setTime(String time) {
	         this.time = time;
	     }
	public String getTime() {
	         return time;
	     }
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}

}
