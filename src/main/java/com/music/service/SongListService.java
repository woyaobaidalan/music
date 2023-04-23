package com.music.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.music.common.api.ServiceResult;
import com.music.entity.SongList;
import org.springframework.web.multipart.MultipartFile;

public interface SongListService extends IService<SongList> {
    ServiceResult sonListOfLikeTitle(String title);

    ServiceResult sonListOfLikeStyle(String style);

    ServiceResult updateSongListMsg(SongList songList);

    ServiceResult updateSongListPic(MultipartFile avatorFile, Long id);
}
