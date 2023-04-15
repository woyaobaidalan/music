package com.music.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
@Data
@TableName("admin")
public class Admin implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer id;

    private String name;

    private String password;
}
