package com.music.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.music.common.api.RedisConstant;
import com.music.common.api.ServiceResult;
import com.music.common.enums.CommonErrorCode;
import com.music.dao.CollectMapper;
import com.music.entity.Collect;
import com.music.service.CollectService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutorService;

@Slf4j
@Service
public class CollectServiceImpl extends ServiceImpl<CollectMapper, Collect> implements CollectService {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public ServiceResult deleteCollection(Long userId, Long songId) {
        log.info("展示歌曲是否被点赞的信息:userId{}, songId:{}", userId, songId);
        LambdaQueryWrapper<Collect> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(Collect::getUserId, userId)
                .eq(Collect::getSongId, songId);
        boolean flag = remove(lambdaQueryWrapper);
        log.info("取消歌曲收藏结果:{}", flag);
        stringRedisTemplate.opsForList().remove(RedisConstant.USER_COLLECTIONLIST_KEY + String.valueOf(userId), 0, String.valueOf(songId));
        if(flag){
            return ServiceResult.success("取消收藏成功");
        }else{
            return ServiceResult.failure(CommonErrorCode.DISCOLLECT_MUSIC_ERROR);
        }

    }

    @Override
    public ServiceResult isCollection(Collect collect) {
        LambdaQueryWrapper<Collect> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(Collect::getUserId, collect.getUserId())
                .eq(Collect::getSongId, collect.getSongId());
        Collect flag = getOne(lambdaQueryWrapper);
        if(flag != null) {
            return ServiceResult.success("已收藏", true);
        }else{
            return ServiceResult.failure("未收藏", false);
        }

    }

    /**
     * 返回指定用户歌曲收藏列表
     * @param userId
     * @return
     */
    @Override
    public ServiceResult collectionOfUser(Long userId) {
        //在redis中查找是否可以找到
        String id = String.valueOf(userId);
        List<String> list = stringRedisTemplate.opsForList().range(RedisConstant.USER_COLLECTIONLIST_KEY + id,
                0, -1);
        //返回时是Long类型数据，需要新添加一个List<Long>作为返回值
        List<Long> longList = new ArrayList<>();
        for (String str : list) {
            longList.add(Long.parseLong(str));
        }

        //可以找到，直接返回
        if(longList.size() != 0) {
            return ServiceResult.success(null, ServiceResult.success(null, longList));
        }
        //找不到去数据库中查找，添加到redis中

        LambdaQueryWrapper<Collect> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(Collect::getUserId, userId);

        List<Collect> collectList = list(lambdaQueryWrapper);

        for(Collect collect : collectList){
            longList.add(collect.getSongId());
            String tmp = String.valueOf(collect.getSongId());
            list.add(tmp);
        }
        //添加到redis中为List<String>
        if(list.size() != 0){
            stringRedisTemplate.opsForList().rightPushAll(RedisConstant.USER_COLLECTIONLIST_KEY + id, list);
        }

        return ServiceResult.success(null, ServiceResult.success(null, longList));
    }

    /**
     * 添加collect，并修改redis
     * @param collect
     * @return
     */
    @Override
    public ServiceResult addCollection(Collect collect) {

        collect.setCreateTime(new Date());
        boolean res = save(collect);
        String id = String.valueOf(collect.getUserId());
        stringRedisTemplate.opsForList().rightPushAll(RedisConstant.USER_COLLECTIONLIST_KEY + id, String.valueOf(collect.getSongId()));
        if (res) {
            return ServiceResult.success("收藏成功", true);
        }else{
            return ServiceResult.failure(CommonErrorCode.COLLECT_MUSIC_ERROR);
        }

    }
}
