package com.music.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
@Data
@TableName
public class ListSong implements Serializable {

    private static final Long SerialVersionUID = 1L;

    private Long id;

    private Long songId;

    private Long songListId;
}
