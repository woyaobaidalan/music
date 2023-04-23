package com.music.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class ConsumerDto implements Serializable {

    private static final Long serialVersionUID = 1L;

    private Long id;

    private String username;

    private String old_password;

    private String password;
}
