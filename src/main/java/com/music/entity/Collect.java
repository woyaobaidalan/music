package com.music.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@TableName("collect")
public class Collect implements Serializable {

    private static final Long SerialVersionUID = 1L;

    private Long id;

    private Long userId;

    private Byte type;

    private Long songId;

    private Long songListId;

    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

}
