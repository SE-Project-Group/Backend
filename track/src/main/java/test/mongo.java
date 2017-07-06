package test;
import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.data.redis.core.RedisTemplate;

import dao.FeedRepository;
import model.Feed;
import net.sf.json.JSONObject;
public class mongo {

  
    private  FeedRepository pr=null;


    public void init(){

         ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
          pr= (FeedRepository)ctx.getBean("feedRepository");



    }

   /* public void insert(){

        Feed feed=new Feed();
        List list=null;
       feed.setUser_id(1);
       
       feed.setMentionList(list);
       feed.setShareArea("public");
       feed.setText("hello");
       feed.setShowLocation(true);
     
         pr.insert(feed);
    }*/

   /* public void findOne(){
        String id="1";
        long  time=1499150692324L;
        Feed p= pr.findOne("1",time);
    String pstring=   p.toString();
    System.out.println(pstring);
    }
*/


   /* public void listAll(){

        List<Feed> list=pr.findByUser_id(2);

        for (Feed p:list){
        	String pstring=  JSONObject.fromObject(p).toString();
        	
            System.out.println(pstring);
        }


    }*/
/*public void update(){
	Feed feed=new Feed();
    List list=null;
   feed.setUser_id("1");
   feed.setLatitude("1");
   feed.setLongtitude("2");
   feed.setMentionList(list);
   feed.setShareArea("public");
   feed.setText("hello");
   feed.setShowLocation("false");
   long  time=1499150692324L;
   feed.setTime(time);
 
     pr.update(feed);
     List<Feed> lists=pr.findByUser_id("1");

     for (Feed p:lists){
     	String pstring=  p.toString();
         System.out.println(pstring);
     }
}*/
   public void remove(){
    	
    	pr.removeOne("595d9fa3b9350a2658fa6298");
        List<Feed> list=pr.findByUser_id(2);

        for (Feed p:list){
        	String pstring=  p.toString();
            System.out.println(pstring);
        }


    }
   public void start(){
        init();
//update();
        //insert();        
        //listAll();
remove();
        //findOne();
    }

    public static void main(String[] args) {
        mongo t=new mongo();
        t.start();
    }

}