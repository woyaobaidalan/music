package com.music.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.music.common.api.ServiceResult;
import com.music.common.enums.CommonErrorCode;
import com.music.entity.RankList;
import com.music.service.RankListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/rankList")
public class RankListController {

    @Autowired
    private RankListService rankListService;

    /**
     * 提交评分
     * @param rankList
     * @return
     */
    @PostMapping("/add")
    public ServiceResult addRank(@RequestBody RankList rankList){
        boolean flag = rankListService.save(rankList);
        if(flag){
            return ServiceResult.success("评价成功");
        }else {
            return ServiceResult.failure(CommonErrorCode.RANKLIST_ADD_ERROR);
        }
    }

    /**
     * 获取指定歌单的评分
     * @param songListId
     * @return
     */
    @GetMapping
    public ServiceResult rankOfSongListId(@RequestParam("songListId") Long songListId){
        return rankListService.rankOfSongListId(songListId);
    }

    /**
     * 获取指定用户的歌单评分
     * @param songListId
     * @param consumerId
     * @return
     */
    @GetMapping("/user")
    public ServiceResult getUserRank(@RequestParam("songListId") Long songListId, @RequestParam("consumerId") Long consumerId){
        return rankListService.getUserRank(songListId, consumerId);
    }






}
