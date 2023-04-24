package com.music.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.music.common.api.ServiceResult;
import com.music.entity.Collect;

public interface CollectService extends IService<Collect> {
    ServiceResult deleteCollection(Long userId, Long songId);

    ServiceResult isCollection(Collect collect);
}
