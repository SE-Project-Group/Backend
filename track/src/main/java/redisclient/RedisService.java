package redisclient;

import org.springframework.stereotype.Service;

@Service("redisService") 
public class RedisService extends AbstractRedisService<String, String> { 
 
}