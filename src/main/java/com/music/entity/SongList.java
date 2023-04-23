package com.music.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;

import java.io.Serializable;
@Data
@TableName("song_list")
public class SongList implements Serializable {

    private static final Long serialVersionUID = 1L;

    private Long id;

    private String title;

    private String pic;

    private String style;

    private String introduction;
}
