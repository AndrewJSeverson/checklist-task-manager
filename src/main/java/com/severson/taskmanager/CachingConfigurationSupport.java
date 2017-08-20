package com.severson.taskmanager;

import java.lang.reflect.Method;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;

@Configuration
@EnableCaching
public class CachingConfigurationSupport extends CachingConfigurerSupport {
	
	@Bean
	  public JedisConnectionFactory redisConnectionFactory() {
	    JedisConnectionFactory redisConnectionFactory = new JedisConnectionFactory();

	    // Defaults to local redis when system level not detected
	    redisConnectionFactory.setHostName(
				System.getenv("spring_redis_host") != null ? System.getenv("spring_redis_host") : "127.0.0.1");
		redisConnectionFactory.setPort(Integer
				.parseInt(System.getenv("spring_redis_port") != null ? System.getenv("spring_redis_port") : "6379"));
		redisConnectionFactory.setPassword(
				System.getenv("spring_redis_password") != null ? System.getenv("spring_redis_password") : "");
		return redisConnectionFactory;
	  }

	  @Bean
	  public RedisTemplate<String, String> redisTemplate(RedisConnectionFactory cf) {
	    RedisTemplate<String, String> redisTemplate = new RedisTemplate<String, String>();
	    redisTemplate.setConnectionFactory(cf);
	    return redisTemplate;
	  }

	  @Bean
	  public CacheManager cacheManager(RedisTemplate redisTemplate) {
	    RedisCacheManager cacheManager = new RedisCacheManager(redisTemplate);

	    // Number of seconds before expiration. Defaults to unlimited (0)
	    cacheManager.setDefaultExpiration(3600);
	    return cacheManager;
	  }
	  
	  @Bean
	  public KeyGenerator keyGenerator() {
	    return new KeyGenerator() {
	      @Override
	      public Object generate(Object o, Method method, Object... objects) {
	        // This will generate a unique key of the class name, the method name,
	        // and all method parameters appended.
	        StringBuilder sb = new StringBuilder();
	        sb.append(o.getClass().getName());
	        sb.append(method.getName());
	        for (Object obj : objects) {
	          sb.append(obj.toString());
	        }
	        return sb.toString();
	      }
	    };
	  }
	}
