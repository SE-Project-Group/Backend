 package test;


import java.sql.Date;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import java.util.List;
 
 
 import org.springframework.context.ApplicationContext;

 import org.springframework.context.support.ClassPathXmlApplicationContext;

 
 
import dao.FeedRepository;

import model.Feed;

import model.Like;
import model.Location;
import net.sf.json.JSONObject;

 public class mongo {

    private  FeedRepository pr=null;


    public void init(){

         ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
          pr= (FeedRepository)ctx.getBean("feedRepository");



    }

    @SuppressWarnings("unchecked")
	public void insert(){

        Feed feed=new Feed();
        List list=null;
        Location location=new Location();
        location.setLatitude(30);
        location.setLongitude(91);
     
    
      feed.setUserId(1);
       feed.setLocation(location);
       feed.setMentionList(list);
       feed.setShareArea("private");
      feed.setText("hello");
       feed.setShowLocation(true);
    
      feed.setPosition("there");
      /* feed.setLikeList(likelist);
       feed.setCommentList(list);*/
    
         pr.insert(feed);
    }
   /* public void findOne(){
       String id="1";
       long  time=1499150692324L;
       Feed p= pr.findOne("1",time);
  String pstring=   p.toString();
   System.out.println(pstring);
   }*/



   public void listAll(){

        List<Feed> list=pr.findAll();

        for (Feed p:list){
        	String pstring=  JSONObject.fromObject(p).toString();
       	
            System.out.println(pstring);
        }


    }
public void update(){
	Feed feed=new Feed();
	List list=null;
    Location location=new Location();
    location.setLatitude(30);
    location.setLongitude(91);
    List<Like>likelist=new ArrayList<Like>();
    Like first =new Like();
    first.setUserId(3);
    //first.setTime("20170506112422");
    likelist.add(first);
    feed.set_id("595f1f902f217d22e83985ed");
   feed.setUserId(3);
   feed.setLocation(location);
  feed.setPosition("here");
   feed.setMentionList(list);
   feed.setShareArea("public");
   feed.setText("hello");
   feed.setShowLocation(true);
   feed.setPicCount(9);
  /* feed.setLikeList(likelist);
  feed.setCommentList(list);*/
 
     pr.update(feed);
     List<Feed> lists=pr.findByUserId(3);

     for (Feed p:lists){
    	 String pstring=  JSONObject.fromObject(p).toString();
         System.out.println(pstring);
    }
}
   public void remove(){
    	
    	pr.removeOne("595ef14f2f217d286c6a6066");
        List<Feed> list=pr.findByUserId(1);

        for (Feed p:list){
        	String pstring=  JSONObject.fromObject(p).toString();
            System.out.println(pstring);
        }


    }
   public void findPublicFeedsByTime(){
	   String time="2017-07-13 09:25:30";
	   Timestamp ts = new Timestamp(System.currentTimeMillis()); 
	   ts=Timestamp.valueOf(time);
	   List<Feed> list= pr.findPublicFeedsByTime(ts);
	   for (Feed p:list){
       	String pstring=  JSONObject.fromObject(p).toString();
           System.out.println(pstring);
       }
	   if (list ==null)  System.out.println("error");
	   
   }
   public void GetTodayFeedList() throws ParseException{
	   String dateStr = "2017-07-12";   
       Date date = new Date(System.currentTimeMillis());   
       //注意format的格式要与日期String的格式相匹配   
       DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd"); 
       date = Date.valueOf(dateStr);
       //List<Feed> list=  pr.GetTodayFeedList(date);
       System.out.println(date.toString());
      // for (Feed p:list){
          	/*String pstring=  JSONObject.fromObject(p).toString();
              System.out.println(pstring);
          }*/
   	   //if (list ==null)  System.out.println("error");
   }
   public void start() throws ParseException{
        init();
        //update();
        insert();        
        listAll();
//remove();
        //findOne();
        //findPublicFeedsByTime();
       // GetTodayFeedList();
    }

   
     public static void main(String[] args) throws ParseException {
        mongo t=new mongo();
    	
        t.start();
    	
        
     }}