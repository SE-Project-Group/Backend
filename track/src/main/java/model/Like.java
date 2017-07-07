package model;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Like {

	
	private String time;
	private int user_id;
	    
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

	public void setUser_id(int user_id) {
	         this.user_id = user_id;
	     }
	 public int getUser_id() {
	         return user_id;
	     }

	


}
