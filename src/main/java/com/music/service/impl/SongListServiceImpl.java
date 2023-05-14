package com.music.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.music.common.api.ServiceResult;
import com.music.common.enums.CommonErrorCode;
import com.music.dao.SongListMapper;
import com.music.entity.SongList;
import com.music.service.SongListService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@Slf4j
@Service
public class SongListServiceImpl extends ServiceImpl<SongListMapper, SongList> implements SongListService {
    @Override
    public ServiceResult sonListOfLikeTitle(String title) {
        log.info("歌单标题包含的文字");
        LambdaQueryWrapper<SongList> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.like(SongList::getTitle, title);

        SongList one = getOne(lambdaQueryWrapper);

        return ServiceResult.success("返回成功", one);
    }

    @Override
    public ServiceResult sonListOfLikeStyle(String style) {
        log.info("指定类型的歌单");
        LambdaQueryWrapper<SongList> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.like(SongList::getStyle, style);

        return ServiceResult.success("返回成功", list(lambdaQueryWrapper));
    }

    @Override
    public ServiceResult updateSongListMsg(SongList songList) {
        boolean flag = updateById(songList);
        if(flag){
            return ServiceResult.success("添加成功");
        }else{
            return ServiceResult.failure(CommonErrorCode.UPDATE_SONGLIST_ERROR);
        }

    }

    @Override
    public ServiceResult updateSongListPic(MultipartFile avatorFile, Long id) {
        String fileName = System.currentTimeMillis() + avatorFile.getOriginalFilename();
        String filePath = System.getProperty("user.dir") + System.getProperty("file.separator") + "img" + System.getProperty("file.separator") + "songListPic";
        File file1 = new File(filePath);
        if (!file1.exists()) {
            file1.mkdir();
        }

        File dest = new File(filePath + System.getProperty("file.separator") + fileName);
        String imgPath = "/img/songListPic/" + fileName;


        try {
            avatorFile.transferTo(dest);

            LambdaUpdateWrapper<SongList> lambdaUpdateWrapper = new LambdaUpdateWrapper<>();
            lambdaUpdateWrapper.eq(SongList::getId, id).set(SongList::getPic, imgPath);

            boolean flag = update(lambdaUpdateWrapper);
            if(flag) {
                return ServiceResult.success("上传成功");
            } else {
                return ServiceResult.failure(CommonErrorCode.UPDATE_SONGLISTPIC_ERROR);
            }
        } catch (IOException e) {
            log.error("更改歌曲图片失败");
        }

        return ServiceResult.failure(CommonErrorCode.UPDATE_SONGLISTPIC_ERROR);

    }
}
