package com.music.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
@Data
@TableName("rank_list")
public class RankList implements Serializable {
    private Long id;

    private Long songListId;

    private Long consumerId;

    private Integer score;
}
