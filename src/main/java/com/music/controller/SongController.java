package com.music.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.music.common.api.ServiceResult;
import com.music.common.enums.CommonErrorCode;
import com.music.entity.Song;
import com.music.service.SongService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@RestController
@RequestMapping("/song")
public class SongController {

    @Autowired
    private SongService songService;

    /**
     * 添加歌曲
     * @param song song详细信息
     * @param mpfile 歌曲文件
     * @return ServiceResult
     */
    @PostMapping("/add")
    public ServiceResult addSong(@ModelAttribute Song song, @RequestParam("file") MultipartFile mpfile){
        return songService.addSong(song, mpfile);
    }

    /**
     * 删除歌曲id；
     * @param id 主键id
     * @return ServiceResult
     */
    @GetMapping("/delete")
    public ServiceResult deleteSong(@RequestParam("id") Long id){
        boolean flag = songService.removeById(id);
        if(flag){
            return ServiceResult.success("删除成功");
        }else{
            return ServiceResult.failure(CommonErrorCode.DELETE_SONG_ERROR);
        }
    }

    /**
     * 返回所有歌曲
     * @return
     */
    @GetMapping
    public ServiceResult allSong(){
        return ServiceResult.success(null, songService.list());
    }

    /**
     * 返回指定歌手ID的歌曲
     * @param id 主键id
     * @return
     */
    @GetMapping("/detail")
    public ServiceResult songOfId(@RequestParam("id") Long id){
        LambdaQueryWrapper<Song> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(Song::getId , id);
        return ServiceResult.success(null, songService.list(lambdaQueryWrapper));
    }

    /**
     * 返回指定歌手ID的歌曲
     * @param singerId 歌手ID
     * @return
     */
    @GetMapping("/singer/detail")
    public ServiceResult songOfSingerId(@RequestParam("singerId") Long singerId){
        LambdaQueryWrapper<Song> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(Song::getSingerId , singerId);
        return ServiceResult.success(null, songService.list(lambdaQueryWrapper));
    }

    /**
     * 返回指定歌手姓名的歌曲
     * @param singerName 歌手姓名
     * @return
     */
    @GetMapping("/singerName/detail")
    public ServiceResult songOfSingerName(@RequestParam("name") String singerName){
        LambdaQueryWrapper<Song> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.like(Song::getName , singerName);
        return ServiceResult.success(null, songService.list(lambdaQueryWrapper));
    }

    /**
     * 更新歌曲信息
     * @param song 不包含歌曲更改
     * @return
     */
    @PostMapping("/update")
    public ServiceResult updateSongMsg(@RequestBody Song song){
        boolean flag = songService.updateById(song);
        if(flag){
            return ServiceResult.success("修改成功");
        }else{
            return ServiceResult.failure(CommonErrorCode.UPDATE_SONG_ERROR);
        }

    }

    /**
     * 更新歌曲图片
     * @param urlFile 图片文件
     * @param id 歌曲id
     * @return
     */
    @PostMapping("/img/update")
    public ServiceResult updateSongPic(@RequestParam("file") MultipartFile urlFile, @RequestParam("id") Long id){
        return songService.updateSongPic(urlFile, id);
    }

    @PostMapping("/url/update")
    public ServiceResult updateSongUrl(@RequestParam("file") MultipartFile urlFile, @RequestParam("id") Long id){
        return songService.updateSongUrl(urlFile, id);
    }





}
