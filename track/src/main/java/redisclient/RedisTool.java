package redisclient;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class RedisTool {
	 private static ApplicationContext factory;
	 private static RedisService redisService;
	 
	 public static ApplicationContext getFactory(){
	  if (factory == null){
	   factory = new ClassPathXmlApplicationContext("classpath:redis.xml");
	  }
	  return factory;
	 } 
	 public static RedisService getRedisService(){
	  if (redisService == null){
	   redisService = (RedisService) getFactory().getBean("redisService");
	  }  
	  return redisService;
	 }
}
