package model;

/**
 * Created by thor on 2017/7/15.
 */

public class ReturnUserInfo {
    private int user_id;
    private String name;
    private String portrait_url;
    private int follow_cnt;
    private int follower_cnt;
    private int like_cnt;
    private int share_cnt;
    private String relationship;
    
    public ReturnUserInfo(int user_id,String name,String portrait_url,int follow_cnt,int follower_cnt,int like_cnt,int share_cnt,String relationship){
    	this.user_id=user_id;
    	this.name=name;
    	this.portrait_url=portrait_url;
    	this.follow_cnt=follow_cnt;
    	this.follower_cnt=follower_cnt;
    	this.like_cnt=like_cnt;
    	this.share_cnt=share_cnt;
    	this.relationship=relationship;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getFollow_cnt() {
        return follow_cnt;
    }

    public void setFollow_cnt(int follow_cnt) {
        this.follow_cnt = follow_cnt;
    }

    public int getFollower_cnt() {
        return follower_cnt;
    }

    public void setFollower_cnt(int follower_cnt) {
        this.follower_cnt = follower_cnt;
    }

    public int getLike_cnt() {
        return like_cnt;
    }

    public void setLike_cnt(int like_cnt) {
        this.like_cnt = like_cnt;
    }

    public int getShare_cnt() {
        return share_cnt;
    }

    public void setShare_cnt(int share_cnt) {
        this.share_cnt = share_cnt;
    }

    public String getPortrait_url() {
        return portrait_url;
    }

    public void setPortrait_url(String portrait_url) {
        this.portrait_url = portrait_url;
    }

	public String getRelationship() {
		return relationship;
	}

	public void setRelationship(String relationship) {
		this.relationship = relationship;
	}
}
