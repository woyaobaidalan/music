package com.music.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.music.common.api.ServiceResult;
import com.music.common.enums.CommonErrorCode;
import com.music.dao.ListSongMapper;
import com.music.entity.ListSong;
import com.music.service.ListSongService;
import org.springframework.stereotype.Service;

@Service
public class ListSongServiceImpl extends ServiceImpl<ListSongMapper, ListSong> implements ListSongService {
    @Override
    public ServiceResult deleteListSong(Long songId) {
        LambdaQueryWrapper<ListSong> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(ListSong::getSongId, songId);
        boolean flag = remove(lambdaQueryWrapper);
        if(flag){
            return ServiceResult.success("删除成功");
        }else{
            return ServiceResult.failure(CommonErrorCode.LISTSONG_DELETE_ERROR);
        }
    }
}
