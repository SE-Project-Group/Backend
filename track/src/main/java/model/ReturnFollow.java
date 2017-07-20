package model;

/**
 * Created by irving on 2017/7/4.
 */

public class ReturnFollow {
    private int user_id;
    private String portrait_url;
    private String user_name;
    private String state;

    public final String FRIEND = "friend";
    public final String FOLLOW = "following";
    public final String FOLLOWER = "follower";
    public final String STRANGER="stranger";
    
    public ReturnFollow(){
    	
    }

    public ReturnFollow(String user_name, String portrait_url, int user_id, String state){
        this.user_id = user_id;
        this.user_name = user_name;
        this.portrait_url = portrait_url;
        this.state = state;
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

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getportrait_url() {
        return portrait_url;
    }

    public void setportrait_url(String portrait_url) {
        this.portrait_url = portrait_url;
    }
}