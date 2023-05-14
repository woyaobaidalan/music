package com.music;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.music.common.util.JwtUtils;
import com.music.common.util.SerializeUtil;
import com.music.entity.Consumer;
import com.music.entity.Song;
import com.music.service.ConsumerService;
import com.music.service.SongService;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.junit.jupiter.api.Test;
import org.redisson.Redisson;
import org.redisson.api.RBucket;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@SpringBootTest
class MusicApplicationTests {

    @Test
    void contextLoads() {
        //String password = new SimpleHash("MD5", "123", "admin", 1024).toString();
        String password =
                new SimpleHash(
                        "SHA-1",
                        "123", "Yin",
                        16).toString();
        System.out.println(password);
    }

    @Autowired
    private SongService songService;
    @Test
    void changeUrl(){
        List<Song> list = songService.list();

        for(Song song : list){
            String url = song.getUrl();
            String s = url.substring(7);
            String newUrl = "/song/" + s;
            LambdaQueryWrapper<Song> lambdaQueryWrapper = new LambdaQueryWrapper<>();
            lambdaQueryWrapper.eq(Song::getUrl, url);
            Song song1 = songService.getOne(lambdaQueryWrapper);
            song1.setUrl(newUrl);

            boolean flag = songService.updateById(song1);
            System.out.println(newUrl);

        }

    }

    @Autowired
    private RedisTemplate<Object, Object> stringRedisTemplate;

    @Test
    void RedisTest(){

        String key = JwtUtils.AUTH_HEADER;
        String value = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJleHAiOjE2ODMxMjUyNjIsInVzZXJuYW1lIjoiWWluIn0.WsI3mgGdUBFAuOLPLKzjS9tHSyt4-AFYyElwH189PtI";

//        String result = stringRedisTemplate.opsForValue().get("name");

//        if(result == null)System.out.println(result);

        //        RBucket<String> bucket = redissonClient.getBucket(key);
//
//        bucket.set(value);
//
//        String result = bucket.get();
//
//        System.out.println("存储到 Redis 中的字符串为：" + result);
    }

    @Autowired
    private ConsumerService consumerService;
    @Test
    void Serize(){
        Consumer consumer = consumerService.getById(1L);
        List<Consumer> list = new ArrayList<>();
        list.add(consumer);
        stringRedisTemplate.opsForValue().set("key" + 1L, list);
        List<Consumer> key = (List<Consumer>) stringRedisTemplate.opsForValue().get("key"+1L);

        System.out.println(key);

    }


}
