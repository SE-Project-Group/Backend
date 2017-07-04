package model;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;



public class Feed implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 3617931430808763429L;

    private String user_id;
    private long time;
    private String text;   
    private String showLocation;
    private String latitude;
    private String longtitude; 
    private String shareArea;
    private List<String> mentionList;
    
    
   public Feed(){
    	this.time=System.currentTimeMillis();
    }
    /**
     * @return the user_id
     */
    public String getUser_id() {
        return user_id;
    }
    /**
     * @param user_id the user_id to set
     */
    public void setUser_id(String user_id) {
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
    public String getShowLocation() {
        return showLocation;
    }
    /**
     * @param showLocation the showLocation to set
     */
    public void setShowLocation(String showLocation) {
        this.showLocation = showLocation;
    }
    public long getTime() {
        return time;
    }
    public void setTime(long time) {
        this.time = time;
    }
    public String getLatitude() {
        return latitude;
    }
    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }
    
    public String getLongtitude() {
        return longtitude;
    }
    public void setLongtitude(String longtitude) {
        this.longtitude = longtitude;
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

}
