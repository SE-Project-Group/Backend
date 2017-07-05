package model;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import model.Location;



public class Feed implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 3617931430808763429L;

    private String user_id;
    private long time;
    private String text;   
    private String showLocation;
    private Location location;
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
    /*public String toString() {   
        return "Feed[user_id="+user_id+",time="+time+",text="+text+",showLocation="+showLocation+
        		",latitude="+location.get("latitude")+",longtitude="+location.get("longtitude")+",shareArea="+shareArea+"]";   
    }*/
    /*showLocation,latitude,longtitude; ,shareArea;*/
	public Location getLocation() {
		return location;
	}
	public void setLocation(Location location) {
		this.location = location;
	}
}