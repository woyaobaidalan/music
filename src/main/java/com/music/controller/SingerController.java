package com.music.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.music.common.api.ServiceResult;
import com.music.entity.Singer;
import com.music.service.SingerService;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
@Slf4j
@RestController
@RequestMapping("/singer")
@CrossOrigin
public class SingerController {

    @Autowired
    private SingerService singerService;

    /**
     * 添加歌手
     * @param singer
     * @return
     */
    @PostMapping("/add")
    @RequiresRoles(value = {"administrator", "admin_singer", "all"}, logical= Logical.OR)
    public ServiceResult addSinger(@RequestBody Singer singer){
        return singerService.addSinger(singer);
    }

    /**
     * 删除歌手
     * @param id
     * @return
     */
    @GetMapping("/delete")
    @RequiresRoles(value = {"administrator", "admin_singer", "all"}, logical= Logical.OR)
    public ServiceResult deleteSinger(@RequestParam("id") Long id){
        return singerService.deleteSinger(id);
    }

    /**
     * 展示所有歌手列表
     * @return
     */
    @GetMapping()
    @RequiresRoles(value = {"administrator", "admin_singer", "all"}, logical= Logical.OR)
    public ServiceResult allSinger() {
//        return new ServiceResult<List<Singer>>(null, singerService.allSinger());
        return ServiceResult.success("成功", singerService.list());
    }

    /**
     * 根据性别查找歌手
     * @param gender
     * @return
     */
    @GetMapping("/sex/detail")
    @RequiresRoles(value = {"administrator", "admin_singer", "all"}, logical= Logical.OR)
    public ServiceResult singerOfSex(@RequestParam("sex") String gender){
        LambdaQueryWrapper<Singer> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(Singer::getSex, gender);
        return ServiceResult.success("成功", singerService.list(lambdaQueryWrapper));
    }

    /**
     * 更新操作
     * @param singer
     * @return
     */
    @PostMapping("/update")
    @RequiresRoles(value = {"administrator", "admin_singer", "all"}, logical= Logical.OR)
    @RequiresPermissions(value = {"singer:write"})
    public ServiceResult updateSingerMsg(@RequestBody Singer singer){
        return singerService.updateSinger(singer);
    }

    @PostMapping("/avatar/update")
    public ServiceResult updateSingerPic(@RequestParam("file") MultipartFile avatorFile, @RequestParam("id") Long id){
        log.info("更新了图片");
        return singerService.updateSingerPic(avatorFile, id);
    }

}
