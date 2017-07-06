package model;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import model.Location;



public class Feed implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 3617931430808763429L;

    private int user_id;
    private String time;
    private String text;   
    private boolean showLocation;
    private Location location;
    private String shareArea;
    private List<String> mentionList;
    private List<Integer> picList;
    private int shareCount;
    private int commentCount;
    private int likeCount;
    private String position;
    
    
    
   public Feed(){
	    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    	this.setTime(df.format(new Date(System.currentTimeMillis())));
    	this.shareCount=0;
    	this.commentCount=0;
    	this.likeCount=0;
    }
    /**
     * @return the user_id
     */
    public int getUser_id() {
        return user_id;
    }
    /**
     * @param user_id the user_id to set
     */
    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }
    /**
     * @return the text
     */
    public String getText() {
        return text;
    }
    /**
     * @param text the text to set
     */
    public void setText(String text) {
        this.text = text;
    }
    /**
     * @return the showLocation
     */
    public boolean getShowLocation() {
        return showLocation;
    }
    /**
     * @param showLocation the showLocation to set
     */
    public void setShowLocation(boolean showLocation) {
        this.showLocation = showLocation;
    }
    public String getShareArea() {
        return shareArea;
    }
    public void setShareArea(String shareArea) {
        this.shareArea = shareArea;
    }
    
    public List<String> getMentionList() {
        return mentionList;
    }
    public void setMentionList(List<String> mentionList) {
        this.mentionList = mentionList;
    }
	public Location getLocation() {
		return location;
	}
	public void setLocation(Location location) {
		this.location = location;
	}
	public List<Integer> getPicList() {
		return picList;
	}
	public void setPicList(List<Integer> picList) {
		this.picList = picList;
	}
	public int getShareCount() {
		return shareCount;
	}
	public void setShareCount(int shareCount) {
		this.shareCount = shareCount;
	}
	public int getCommentCount() {
		return commentCount;
	}
	public void setCommentCount(int commentCount) {
		this.commentCount = commentCount;
	}
	public int getLikeCount() {
		return likeCount;
	}
	public void setLikeCount(int likeCount) {
		this.likeCount = likeCount;
	}
	public String getPosition() {
		return position;
	}
	public void setPosition(String position) {
		this.position = position;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
}