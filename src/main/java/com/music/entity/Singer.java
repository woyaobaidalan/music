package com.music.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;


@Data
@TableName("singer")
public class Singer implements Serializable {
    private static final long serialVersionUID = 1L;

//    @JsonSerialize(using = ToStringSerializer.class)

    private Long id;

    private String name;

    private Byte sex;

    private String pic;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date birth;

    private String location;

    private String introduction;
}
