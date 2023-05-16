package com.music.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class MessageDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private String name;

    private Double time;

}
