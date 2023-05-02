package com.music.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.music.common.api.ServiceResult;
import com.music.common.enums.CommonErrorCode;
import com.music.entity.SongList;
import com.music.service.SongListService;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.websocket.server.PathParam;

@Slf4j
@RestController
@RequestMapping("/songList")
public class SongListController {

    @Autowired
    private SongListService songListService;

    /**
     * 添加歌曲功能
     * @param songList 实体信息
     * @return
     */
    @PostMapping("/add")
    @RequiresRoles(value = {"administrator","all"}, logical= Logical.OR)
    @RequiresPermissions(value = {"singer:write"})
    public ServiceResult addSongList(@RequestBody SongList songList){
        log.info("添加的歌曲信息是：{}", songList);
        boolean flag = songListService.save(songList);
        if(flag){
            return ServiceResult.success("添加成功");
        }else {
            return ServiceResult.failure(CommonErrorCode.ADD_SONGLIST_ERROR);
        }
    }

    /**
     * 展示所有歌单列表
     * @return
     */
    @GetMapping()
    @RequiresRoles(value = {"administrator","all"}, logical= Logical.OR)
    public ServiceResult allSongList(){
        return ServiceResult.success(null, songListService.list());
    }

    /**
     * 删除歌曲功能
     * @param id 为自增id
     * @return
     */
    @GetMapping("/delete")
    @RequiresRoles(value = {"administrator","all"}, logical= Logical.OR)
    @RequiresPermissions(value = {"singer:write"})
    public ServiceResult deleteSongList(@RequestParam("id") Long id){
        boolean flag = songListService.removeById(id);
        if(flag){
            return ServiceResult.success("删除成功");
        }else{
            return ServiceResult.failure(CommonErrorCode.DELETE_SONGLIST_ERROR);
        }
    }

    /**
     * 返回标题包含文字的歌单
     * @param title 包含文字的歌单
     * @return
     */
    @GetMapping("/likeTitle/detail")
    @RequiresRoles(value = {"administrator","all"}, logical= Logical.OR)
    public ServiceResult songListOfLikeTitle(@RequestParam("title")String title){
        return songListService.sonListOfLikeTitle(title);
    }

    /**
     * 返回指定类型的歌单
     * @param style 歌曲类型
     * @return
     */
    @GetMapping("/style/detail")
    @RequiresRoles(value = {"administrator","all"}, logical= Logical.OR)
    public ServiceResult songListOfStyle(@RequestParam("style")String style){
        return songListService.sonListOfLikeStyle(style);
    }

    /**
     * 更新歌单信息
     * @param songList 前端返回的实体信息
     * @return
     */
    @PostMapping("/update")
    @RequiresRoles(value = {"administrator","all"}, logical= Logical.OR)
    @RequiresPermissions(value = {"singer:write"})
    public ServiceResult updateSongListMsg(@RequestBody SongList songList){
        return songListService.updateSongListMsg(songList);
    }

    /**
     * 更改歌曲图片
     * @param avatorFile
     * @param id
     * @return
     */
    @PostMapping("/img/update")
    public ServiceResult updateSongListPic(@RequestParam("file") MultipartFile avatorFile, @RequestParam("id") Long id){
        return songListService.updateSongListPic(avatorFile, id);
    }


}
