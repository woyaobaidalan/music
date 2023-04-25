package com.music.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.music.common.api.ServiceResult;
import com.music.common.enums.CommonErrorCode;
import com.music.entity.Collect;
import com.music.service.CollectService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@Slf4j
@RestController
@RequestMapping("/collection")
@CrossOrigin
public class CollectController {

    @Autowired
    private CollectService collectService;

    /**
     * 添加收藏的歌曲
     * @param collect
     * @return
     */
    @PostMapping("/add")
    public ServiceResult addCollection(@RequestBody Collect collect){
        collect.setCreateTime(new Date());
        boolean res = collectService.save(collect);
        if (res) {
            log.info("添加的结果成功:{}", res);
            return ServiceResult.success("收藏成功", true);
        }else{
            return ServiceResult.failure(CommonErrorCode.COLLECT_MUSIC_ERROR);
        }
    }

    /**
     * 取消收藏的歌曲
     * @param userId 用户id
     * @param songId 歌曲id
     * @return
     */
    @DeleteMapping("/delete")
    public ServiceResult deleteCollection(@RequestParam("userId")Long userId, @RequestParam("songId")Long songId){
        log.info("userId的信息是：{}， songID是：{}", userId, songId);
        return collectService.deleteCollection(userId, songId);
    }

    /**
     * 是否收藏歌曲
     * @param collect
     * @return
     */
    @PostMapping("/status")
    public ServiceResult isCollection(@RequestBody Collect collect){
        return collectService.isCollection(collect);
    }

    /**
     * 返回的指定用户 ID 收藏的列表
     * @param userId
     * @return
     */
    @GetMapping("/detail")
    public ServiceResult collectionOfUser(@RequestParam("userId") Long userId){
        LambdaQueryWrapper<Collect> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(Collect::getUserId, userId);
        return ServiceResult.success(null, collectService.list(lambdaQueryWrapper));
    }


}
