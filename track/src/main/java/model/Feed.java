package model;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import model.Location;



public class Feed implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 3617931430808763429L;
    private String _id;
    private int userId;
    private String time;
    private String text;   
    private boolean showLocation;
    private Location location;
    private String shareArea;
    private List<Integer> mentionList;
    private List<Integer> picList;
    private int shareCount;
    private int commentCount;
    private int likeCount;
    private String position;
    private List<Like> likeList;
    private List<Comment> commentList;
    
    
   public Feed(){
	    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    	this.setTime(df.format(new Date(System.currentTimeMillis())));
    	this.shareCount=0;
    	this.commentCount=0;
    	this.likeCount=0;
    	this.likeList=new ArrayList<Like>();
    	this.commentList=new ArrayList<Comment>();
    	this.picList=new ArrayList<Integer>();
    }
   public String get_id() {
       return _id;
   }
   public void set_id(String _id) {
       this._id = _id;
   }
    public String getText() {
        return text;
    }
    public void setText(String text) {
        this.text = text;
    }

    public boolean getShowLocation() {
        return showLocation;
    }
 
    public void setShowLocation(boolean showLocation) {
        this.showLocation = showLocation;
    }
    public String getShareArea() {
        return shareArea;
    }
    public void setShareArea(String shareArea) {
        this.shareArea = shareArea;
    }
    
    public List<Integer> getMentionList() {
        return mentionList;
    }
    public void setMentionList(List<Integer> mentionList) {
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
	public List<Like> getLikeList() {
		return likeList;
	}
	public void setLikeList(List<Like> likeList) {
		this.likeList = likeList;
	}
	public List<Comment> getCommentList() {
		return commentList;
	}
	public void setCommentList(List<Comment> commentList) {
		this.commentList = commentList;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
}