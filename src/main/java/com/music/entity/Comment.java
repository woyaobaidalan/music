package com.music.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@TableName("comment")
public class Comment implements Serializable {

    private static final Long SerialVersionUID = 1L;

    private Long id;

    private Long userId;

    private Long songId;

    private Long songListId;

    private String content;

    private Date createTime;

    private Byte type;

    private Long up;
}
