package com.music.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.music.entity.SongList;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface SongListMapper extends BaseMapper<SongList> {
}
