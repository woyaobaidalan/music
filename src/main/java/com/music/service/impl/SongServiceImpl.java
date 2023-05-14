package com.music.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.music.common.api.ServiceResult;
import com.music.common.enums.CommonErrorCode;
import com.music.dao.SongMapper;
import com.music.entity.Song;
import com.music.service.SongService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@Slf4j
@Service
public class SongServiceImpl extends ServiceImpl<SongMapper, Song> implements SongService {

    @Autowired
    private RedisTemplate<Object, Object> redisTemplate = new RedisTemplate<>();



    private static final String PICPATH = "/img/songPic/tubiao.jpg";
    @Override
    public ServiceResult addSong(Song song, MultipartFile mpfile) {


        String fileName = mpfile.getOriginalFilename();
        String filePath = System.getProperty("user.dir") + System.getProperty("file.separator") + "song";
        File file1 = new File(filePath);
        if (!file1.exists()) {
            file1.mkdir();
        }


        File dest = new File(filePath + System.getProperty("file.separator") + fileName);
        String storeUrlPath = "/song/" + fileName;
        log.info("filePath :{}", storeUrlPath);


        try {
            mpfile.transferTo(dest);
            song.setPic(PICPATH);
            song.setUrl(storeUrlPath);


            boolean flag = save(song);
            if(flag){
                return ServiceResult.success("上传成功", storeUrlPath);
            }else{
                return ServiceResult.failure(CommonErrorCode.UPLOAD_SONG_ERROR);
            }
        } catch (IOException e) {
            log.error("上传失败", e);
            return ServiceResult.failure(CommonErrorCode.UPLOAD_SONG_ERROR);

        }
    }

    @Override
    public ServiceResult updateSongPic(MultipartFile urlFile, Long id) {

        String fileName = System.currentTimeMillis() + urlFile.getOriginalFilename();
        String filePath = System.getProperty("user.dir") + System.getProperty("file.separator") + "img" + System.getProperty("file.separator") + "songPic";
        File file1 = new File(filePath);
        if (!file1.exists()) {
            file1.mkdir();
        }

        File dest = new File(filePath + System.getProperty("file.separator") + fileName);
        String storeUrlPath = "/img/songPic/" + fileName;

        try {
            urlFile.transferTo(dest);
            LambdaUpdateWrapper<Song> lambdaUpdateWrapper = new LambdaUpdateWrapper<>();
            lambdaUpdateWrapper.eq(Song::getId, id).set(Song::getPic, storeUrlPath);
            boolean flag = update(lambdaUpdateWrapper);

            if (flag) {
                return ServiceResult.success("上传成功");
            } else {
                return ServiceResult.failure(CommonErrorCode.UPDATE_SONGPIC_ERROR);
            }
        } catch (IOException e) {
            log.error("上传失败", e);
            return ServiceResult.failure(CommonErrorCode.UPDATE_SONGPIC_ERROR);
        }

    }

    @Override
    public ServiceResult updateSongUrl(MultipartFile urlFile, Long id) {
        String fileName = urlFile.getOriginalFilename();
        String filePath = System.getProperty("user.dir") + System.getProperty("file.separator") + "song";
        File file1 = new File(filePath);
        if (!file1.exists()) {
            file1.mkdir();
        }

        File dest = new File(filePath + System.getProperty("file.separator") + fileName);
        String storeUrlPath = "/song/" + fileName;

        try {
            urlFile.transferTo(dest);
            LambdaUpdateWrapper<Song> lambdaUpdateWrapper = new LambdaUpdateWrapper<>();
            lambdaUpdateWrapper.eq(Song::getId, id).set(Song::getUrl, storeUrlPath);
            boolean flag = update(lambdaUpdateWrapper);

            if (flag) {
                return ServiceResult.success("上传成功");
            } else {
                return ServiceResult.failure(CommonErrorCode.UPDATE_SONGURL_ERROR);
            }
        } catch (IOException e) {
            log.error("更新歌曲失败", e);
            return ServiceResult.failure(CommonErrorCode.UPDATE_SONGURL_ERROR);
        }

    }
}
