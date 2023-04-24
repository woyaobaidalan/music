package com.music.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.music.common.api.ServiceResult;
import com.music.entity.ListSong;

public interface ListSongService extends IService<ListSong> {
    ServiceResult deleteListSong(Long songId);
}
