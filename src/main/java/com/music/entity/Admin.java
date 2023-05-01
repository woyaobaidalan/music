package com.music.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;

import java.io.Serializable;
@Data
@TableName("admin")
public class Admin implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;

    private String name;

    private String password;

    private String roles;

    private String permission;
}
