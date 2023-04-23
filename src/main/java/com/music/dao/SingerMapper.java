package com.music.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.music.entity.Singer;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface SingerMapper extends BaseMapper<Singer> {
}
