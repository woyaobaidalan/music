package com.music.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.music.common.api.ServiceResult;
import com.music.common.enums.CommonErrorCode;
import com.music.dao.RankListMapper;
import com.music.entity.RankList;
import com.music.service.RankListService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class RankListServiceImpl extends ServiceImpl<RankListMapper, RankList> implements RankListService {
    @Override
    public ServiceResult getUserRank(Long songListId, Long consumerId) {
        log.info("songListId是:{}, consumerId是:{}", songListId, consumerId);
        LambdaQueryWrapper<RankList> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(RankList::getSongListId, songListId)
                .eq(RankList::getConsumerId, consumerId);

        RankList rankList = getOne(lambdaQueryWrapper);

//        int score = rankList == null ? 0 : rankList.getScore();
        if(rankList == null){
            return ServiceResult.failure("未评价", null);
        }
        return ServiceResult.success("已评价", rankList.getScore());
    }

    @Override
    public ServiceResult rankOfSongListId(Long songListId) {
        log.info("songListId的信息是：{}", songListId);
        LambdaQueryWrapper<RankList> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(RankList::getSongListId, songListId);

        List<RankList> rankList = list(lambdaQueryWrapper);
        int score = 0;
        for(RankList rank : rankList){
            score += rank.getScore();
        }
        if(rankList.size() != 0){
            score = score / rankList.size();
        }

        return ServiceResult.success(null, score);
    }
}
