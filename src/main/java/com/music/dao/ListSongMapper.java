package com.music.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.music.entity.ListSong;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ListSongMapper extends BaseMapper<ListSong> {
}
