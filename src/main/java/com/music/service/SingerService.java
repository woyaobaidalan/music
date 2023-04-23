package com.music.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.music.common.api.ServiceResult;
import com.music.entity.Singer;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface SingerService extends IService<Singer> {
    ServiceResult addSinger(Singer singer);

    ServiceResult deleteSinger(Long id);

    ServiceResult updateSinger(Singer singer);

    ServiceResult updateSingerPic(MultipartFile avatorFile, Long id);
}

