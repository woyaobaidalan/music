package com.music.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.music.common.api.ServiceResult;
import com.music.entity.RankList;

public interface RankListService extends IService<RankList> {
    ServiceResult getUserRank(Long songListId, Long consumerId);

    ServiceResult rankOfSongListId(Long songListId);
}
