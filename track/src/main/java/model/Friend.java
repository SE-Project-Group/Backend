package model;

public class Friend {
	
	private int userId;
	private int friendId;
	private boolean isFriend;
	
	public Friend(){
		
	}
	
	public Friend(int userId,int friendId,boolean isFriend){
		this.setUserId(userId);
		this.setFriendId(friendId);
		this.setFriend(isFriend);
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public int getFriendId() {
		return friendId;
	}

	public void setFriendId(int friendId) {
		this.friendId = friendId;
	}

	public boolean isFriend() {
		return isFriend;
	}

	public void setFriend(boolean isFriend) {
		this.isFriend = isFriend;
	}
	
	
}
