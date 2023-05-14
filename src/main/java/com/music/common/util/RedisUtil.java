package com.music.common.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.stream.Record;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.Map;
@Configuration
public class RedisUtil {

    @Autowired
    private RedisTemplate redisTemplate;


    /**
     * description: 创建消费组
     * @return java.lang.String
     */
    public String createGroup(String key, String group){
        return redisTemplate.opsForStream().createGroup(key, group);
    }

    /**
     * description: 添加Map消息
     * @param: key
     * @param: value
     * @return
     */
    public String addMap(String key, Map<String, String> value){
        return redisTemplate.opsForStream().add(key, value).getValue();
    }

    /**
     * description: 添加Record消息
     * @param: record
     * @return
     */
    public String addRecord(Record<String, Object> record){
        return redisTemplate.opsForStream().add(record).getValue();
    }

    /**
     * description: 确认消费
     * @param: key
     * @param: group
     * @param: recordIds
     * @return java.lang.Long
     */
    public Long ack(String key, String group, String... recordIds){
        return redisTemplate.opsForStream().acknowledge(key, group, recordIds);
    }

    /**
     * description: 删除消息。当一个节点的所有消息都被删除，那么该节点会自动销毁
     * @param: key
     * @param: recordIds
     * @return java.lang.Long
     */
    public Long del(String key, String... recordIds){
        return redisTemplate.opsForStream().delete(key, recordIds);
    }
}
