package model;

import java.io.Serializable;

public class Follow implements Serializable{
	 private static final long serialVersionUID = 3617931430808763429L;
	private int userId;
	private int followId;
	private int isFriend;
	
	public Follow(){
		
	}
	
	public Follow(int userId,int followId,int isFriend){
		this.setUserId(userId);
		this.setFollowId(followId);
		this.setIsFriend(isFriend);
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public int getFollowId() {
		return followId;
	}

	public void setFollowId(int followId) {
		this.followId = followId;
	}

	public int getIsFriend() {
		return isFriend;
	}

	public void setIsFriend(int isFriend) {
		this.isFriend = isFriend;
	}
	
	
}
