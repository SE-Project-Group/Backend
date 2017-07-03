package track;



import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import redis.clients.jedis.Jedis;
import redisclient.RedisService;
 
public class Reis {
    public static void main(String[] args) {  
        
        @SuppressWarnings("resource")    
        ApplicationContext factory=new ClassPathXmlApplicationContext("classpath:redis.xml");  
             
        RedisService rs= (RedisService) factory.getBean("redisService");  
        if(rs!=null){  
         System.out.println("RedisService : "+rs);  
         if(rs.check("lwj", "123456789000")){ //</span><span style="font-family: Î¢ÈíÑÅºÚ;">lwj</span><span style="font-family: Î¢ÈíÑÅºÚ;">ÒÑÊÂÏÈÐ´½øredisµÄÊý¾Ý</span><span style="font-family:Î¢ÈíÑÅºÚ;">  
      System.out.println("redis has contianed ");
          System.out.println("add one new key-value : lwj 1234567 expire 15000");  
          rs.set("lwj", "123456", 15000);  
         }else{  
          System.out.println("add one new key-value : lwj 123456 expire 15000");  
          rs.set("lwj", "123456789000", 15000);  
         }  
           
         
         String value=rs.get("lwj");  
         System.out.println(value);  
      }}  
}


