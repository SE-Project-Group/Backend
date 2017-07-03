package util;

import org.springframework.data.redis.core.StringRedisTemplate; 

public class RedisUtil {
	
	 private StringRedisTemplate redisTemplate;
	
	 public void put(String key, String value) {  
	        if (key==null || "".equals(key)) {  
	            return;  
	        }  
	        redisTemplate.opsForHash().put(key, key, value);            
	 }
	 
	 public void put(int key, String value) {  
	        redisTemplate.opsForHash().put(String.valueOf(key), String.valueOf(key), value);            
	 }
	 
	 public String get(String key) {  
	        Object obj = redisTemplate.opsForHash().get(key, key);  
	        if(obj == null){  
	            return null;  
	        }else{  
	            return String.valueOf(obj);  
	        }  
	 }  
	 
	 public String get(int key) {  
	        Object obj = redisTemplate.opsForHash().get(String.valueOf(key), String.valueOf(key));  
	        if(obj == null){  
	            return null;  
	        }else{  
	            return String.valueOf(obj);  
	        }  
	 }  
}
