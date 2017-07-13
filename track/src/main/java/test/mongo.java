package test;
import java.util.ArrayList;
 import java.util.List;
 
 import org.springframework.context.ApplicationContext;
import org.springframework.data.redis.core.RedisTemplate;
 
 import dao.FeedRepository;
 import model.Feed;
import model.Like;
import model.Location;
 import net.sf.json.JSONObject;

 
 public class mongo {
 
  
 

   public void insert(){
 
         Feed feed=new Feed();
         List list=null;
     
       Location location=new Location();
        location.setLatitude(30);
        location.setLongitude(91);
       List<Like>likelist=new ArrayList<Like>();
       Like first =new Like();
        first.setUser_id(2);
        //first.setTime("20170506112422");
        likelist.add(first);
       feed.setUser_id(22);
       feed.setLocation(location);
        feed.setMentionList(list);
        feed.setShareArea("public");
        feed.setText("hello");
        feed.setShowLocation(true);
      feed.setPicList(list);
       feed.setPosition("there");
      /* feed.setLikeList(likelist);
       feed.setCommentList(list);*/
      
          pr.insert(feed);

    }
 
    /* public void findOne(){
         String id="1";
@@ -43,13 +58,13 @@ public class mongo {
         Feed p= pr.findOne("1",time);
     String pstring=   p.toString();
     System.out.println(pstring);

    }*/
 
 

 

    public void listAll(){

        List<Feed> list=pr.findByUser_id(22);
 
         for (Feed p:list){
         	String pstring=  JSONObject.fromObject(p).toString();

         }
 


    
public void update(){
 	Feed feed=new Feed();

	List list=null;
    Location location=new Location();
    location.setLatitude(30);
    location.setLongitude(91);
    List<Like>likelist=new ArrayList<Like>();
    Like first =new Like();
   first.setUser_id(3);
    //first.setTime("20170506112422");
    likelist.add(first);
    feed.set_id("595f1f902f217d22e83985ed");
   feed.setUser_id(3);
   feed.setLocation(location);
   feed.setPosition("here");
    feed.setMentionList(list);
    feed.setShareArea("public");
    feed.setText("hello");

  feed.setShowLocation(true);
   feed.setPicList(list);
  /* feed.setLikeList(likelist);
  feed.setCommentList(list);*/
  
      pr.update(feed);

     List<Feed> lists=pr.findByUser_id(3);
 
      for (Feed p:lists){

    	 String pstring=  JSONObject.fromObject(p).toString();
          System.out.println(pstring);
      }

}
    public void remove(){
     	

    	pr.removeOne("595ef14f2f217d286c6a6066");
        List<Feed> list=pr.findByUser_id(1);
 
         for (Feed p:list){

       	String pstring=  JSONObject.fromObject(p).toString();
             System.out.println(pstring);
         }}
 
 
    public void start(){
         init();
//update();

         //insert();        


        listAll();
//remove();
         //findOne();
     }}