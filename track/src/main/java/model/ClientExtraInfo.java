package model;

public class ClientExtraInfo {
private int userId;
private String career;
private String education;
private String hobbyone;
private String hobbytwo;
private String hobbythree;



public ClientExtraInfo(int userId,String career,String education,String hobbyone,String hobbytwo,String hobbythree)
{
	this.userId=userId;
	this.career=career;
	this.education=education;
	this.hobbyone=hobbyone;
	this.hobbytwo=hobbytwo;
	this.hobbythree=hobbythree;
	
}
public int getUserId() {
	return userId;
}
public void setUserId(int userId) {
	this.userId = userId;
}
public String getCareer() {
	return career;
}
public void setCareer(String career) {
	this.career = career;
}
public String getEducation() {
	return education;
}
public void setEducation(String education) {
	this.education = education;
}
public String getHobbyone() {
	return hobbyone;
}
public void setHobbyone(String hobbyone) {
	this.hobbyone = hobbyone;
}
public String getHobbytwo() {
	return hobbytwo;
}
public void setHobbytwo(String hobbytwo) {
	this.hobbytwo = hobbytwo;
}
public String getHobbythree() {
	return hobbythree;
}
public void setHobbythree(String hobbythree) {
	this.hobbythree = hobbythree;
}

}
