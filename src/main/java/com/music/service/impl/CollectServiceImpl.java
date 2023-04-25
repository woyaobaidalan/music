package com.music.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.music.common.api.ServiceResult;
import com.music.common.enums.CommonErrorCode;
import com.music.dao.CollectMapper;
import com.music.entity.Collect;
import com.music.service.CollectService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
@Slf4j
@Service
public class CollectServiceImpl extends ServiceImpl<CollectMapper, Collect> implements CollectService {
    @Override
    public ServiceResult deleteCollection(Long userId, Long songId) {
        log.info("展示歌曲是否被点赞的信息:userId{}, songId:{}", userId, songId);
        LambdaQueryWrapper<Collect> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(Collect::getUserId, userId)
                .eq(Collect::getSongId, songId);
        boolean flag = remove(lambdaQueryWrapper);
        log.info("取消歌曲收藏结果:{}", flag);
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
}
