package util;

import org.springframework.data.redis.core.StringRedisTemplate; 

public class RedisUtil {
	
	 private StringRedisTemplate redisTemplate;
	
	 public void put(String key, String value) {  
	        if (key==null || "".equals(key)) {  
	            return;  
	        }  
	        redisTemplate.opsForValue().set(key, value);            
	 }
	 
	 public void put(int key, String value) {  
	        redisTemplate.opsForValue().set(String.valueOf(key), value);            
	 }
	 
	 public String get(String key) {  
	        Object obj = redisTemplate.opsForValue().get(key);  
	        if(obj == null){  
	            return null;  
	        }else{  
	            return String.valueOf(obj);  
	        }  
	 }  
	 
	 public String get(int key) {  
	        Object obj = redisTemplate.opsForValue().get(String.valueOf(key));  
	        if(obj == null){  
	            return null;  
	        }else{  
	            return String.valueOf(obj);  
	        }  
	 }  
}
