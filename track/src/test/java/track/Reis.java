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
         if(rs.check("testtt", "123456789000")){ //</span><span style="font-family: 微软雅黑;">lwj</span><span style="font-family: 微软雅黑;">已事先写进redis的数据</span><span style="font-family:微软雅黑;">  
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
        //添加一个 key 
        ValueOperations<String, Object> value = redisTemplate.opsForValue();
        value.set("lp", "hello word");
        //获取 这个 key 的值
        System.out.println(value.get("lp"));
        //添加 一个 hash集合
        HashOperations<String, Object, Object>  hash = redisTemplate.opsForHash();
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("name", "lp");
        map.put("age", "26");
        hash.putAll("lpMap", map);
        //获取 map
        System.out.println(hash.entries("lpMap"));
        //添加 一个 list 列表
        ListOperations<String, Object> list = redisTemplate.opsForList();
        list.rightPush("lpList", "lp");
        list.rightPush("lpList", "26");
        //输出 list
        System.out.println(list.range("lpList", 0, 1));
        //添加 一个 set 集合
        SetOperations<String, Object> set = redisTemplate.opsForSet();
        set.add("lpSet", "lp");
        set.add("lpSet", "26");
        set.add("lpSet", "178cm");
        //输出 set 集合
        System.out.println(set.members("lpSet"));
        //添加有序的 set 集合
        ZSetOperations<String, Object> zset = redisTemplate.opsForZSet();
        zset.add("lpZset", "lp", 0);
        zset.add("lpZset", "26", 1);
        zset.add("lpZset", "178cm", 2);
        //输出有序 set 集合
        System.out.println(zset.rangeByScore("lpZset", 0, 2));
    }
>>>>>>> 427ad375ac12c17b0c1932efbd40fd81b3dcc64e
}

