package track;

import java.util.HashMap;
import java.util.Map;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.core.ZSetOperations;

<<<<<<< HEAD
import redis.clients.jedis.Jedis;
import redisclient.RedisService;
 
public class Reis {
    public static void main(String[] args) {  
        
        @SuppressWarnings("resource")    
        ApplicationContext factory=new ClassPathXmlApplicationContext("classpath:redis.xml");  
             
        RedisService rs= (RedisService) factory.getBean("redisService");  
        if(rs!=null){  
         System.out.println("RedisService : "+rs);  
         if(rs.check("testtt", "123456789000")){ //</span><span style="font-family: ΢���ź�;">lwj</span><span style="font-family: ΢���ź�;">������д��redis������</span><span style="font-family:΢���ź�;">  
      System.out.println("redis has contianed ");
          System.out.println("add one new key-value : lwj 1234567 expire 15000");  
          rs.set("lwj", "123456", 15000);  
         }else{  
          System.out.println("add one new key-value : lwj 123456 expire 15000");  
          rs.set("testtt", "123456789000", 15000);  
         }  
           
         
         String value=rs.get("lwj");  
         System.out.println(value);  
      }}  
=======
public class Reis{
public static void main(String[] args) {
        ClassPathXmlApplicationContext appCtx = new ClassPathXmlApplicationContext("applicationContext.xml");
        final RedisTemplate<String, Object> redisTemplate = appCtx.getBean("redisTemplate",RedisTemplate.class);
        //���һ�� key 
        ValueOperations<String, Object> value = redisTemplate.opsForValue();
        value.set("lp", "hello word");
        //��ȡ ��� key ��ֵ
        System.out.println(value.get("lp"));
        //��� һ�� hash����
    }
>>>>>>> 427ad375ac12c17b0c1932efbd40fd81b3dcc64e
}

