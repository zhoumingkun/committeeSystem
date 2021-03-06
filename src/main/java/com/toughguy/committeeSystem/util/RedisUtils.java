package com.toughguy.committeeSystem.util;

import java.util.Map;
import java.util.concurrent.TimeUnit;

import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import com.google.gson.JsonObject;



@Component
public class RedisUtils { 	
	@Autowired	
	private  RedisTemplate<String, String> redisTemplate; 	
	/**	 
	 *  读取缓存	  	 
	 * @param key	 
	 * @return	 
	 */	
	public  Object get(final String key) {		
		return redisTemplate.opsForValue().get(key);	
	} 	
	
	/**	 
	 * 写入缓存String	
	 */	
	public  boolean set(final String key, String value) {		
		boolean result = false;		
		try {			
			redisTemplate.opsForValue().set(key, value);
			result = true;		
		} catch (Exception e) {
			e.printStackTrace();		
			}		
		return result;	
	} 	
	
	/**	 
	 * 写入缓存String	
	 */	
	public  boolean setMap(final String key, Object value,int expire) {		
		boolean result = false;		
		try {			
			redisTemplate.opsForValue().set(key, JSONObject.toJSONString(value), expire, TimeUnit.SECONDS);
			result = true;		
		} catch (Exception e) {
			e.printStackTrace();		
			}		
		return result;	
	} 	
	
	/**	 
	 *  更新缓存	 
	 */	
	public  boolean getAndSet(final String key, Object value) {
		boolean result = false;		
		try {			
			redisTemplate.opsForValue().getAndSet(key, JSONObject.toJSONString(value));
			result = true;		
		} catch (Exception e) {			
				e.printStackTrace();		
		}		return result;	
	} 	
	
	/**	 
	 *  删除缓存	
	 */	
	public  boolean delete(final String key) {
		boolean result = false;		
		try {			
			redisTemplate.delete(key);			
			result = true;		
		} catch (Exception e) {			
			e.printStackTrace();		
		}		return result;	
	}
	
	 /**
     * 指定缓存失效时间
     * @param key 键
     * @param time 时间(秒)
     * @return
     */
    public  boolean expire(String key,long time){
        try {
            if(time>0){
                redisTemplate.expire(key, time, TimeUnit.SECONDS);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    /**
     * 判断key是否存在
     * @param key 键
     * @return true 存在 false不存在
     */
    public  boolean hasKey(String key){
        try {
            return redisTemplate.hasKey(key);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}

