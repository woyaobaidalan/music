package com.music.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.music.common.api.ServiceResult;
import com.music.entity.Song;
import org.springframework.web.multipart.MultipartFile;

public interface SongService extends IService<Song> {
    ServiceResult addSong(Song song, MultipartFile mpfile);

    ServiceResult updateSongPic(MultipartFile urlFile, Long id);

    ServiceResult updateSongUrl(MultipartFile urlFile, Long id);
}
