package com.music.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.music.common.api.ServiceResult;
import com.music.common.enums.CommonErrorCode;
import com.music.entity.ListSong;
import com.music.service.ListSongService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/listSong")
public class ListSongController {

    @Autowired
    private ListSongService listSongService;

    /**
     * 给歌单添加歌曲
     * @param listSong
     * @return
     */
    @PostMapping("/add")
    public ServiceResult addListSong(@RequestBody ListSong listSong){
        boolean flag = listSongService.save(listSong);
        if(flag){
            return ServiceResult.success("添加成功");
        }else{
            return ServiceResult.failure(CommonErrorCode.LISTSONG_ADD_ERROR);
        }
    }

    /**
     * 删除歌单里的歌曲
     * @param songId 歌曲id
     * @return
     */
    @GetMapping("/delete")
    public ServiceResult deleteListSong(@RequestParam("songId") Long songId){
        return listSongService.deleteListSong(songId);
    }

    /**
     * 返回歌单里指定歌单 ID 的歌曲
     * @param songListId 歌单id的歌曲
     * @return
     */
    @GetMapping("/detail")
    public ServiceResult listSongOfSongId(@RequestParam("songListId") Long songListId){
        LambdaQueryWrapper<ListSong> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(ListSong::getSongListId, songListId);
        return ServiceResult.success(null, listSongService.list(lambdaQueryWrapper));
    }


}
