package com.music.common.enums;

import com.music.common.exception.ApplicationExceptionEnum;

public enum CommonErrorCode implements ApplicationExceptionEnum {

    NOT_LOGIN(500, "用户名或密码错误"),
    //歌手singer
    Add_SINGER_ERROR(501, "添加用户失败"),
    DELETE_SINGER_ERROR(502, "删除用户失败"),
    UPDATE_SINGER_ERROR(503, "修改歌手信息失败"),
    UPDATE_SINGERPIC_ERROR(504, "上传歌手信息失败"),
    //歌曲列表songList
    ADD_SONGLIST_ERROR(505, "添加歌曲失败"),
    DELETE_SONGLIST_ERROR(506, "删除歌曲列表失败"),
    UPDATE_SONGLIST_ERROR(507, "更新歌曲失败"),
    UPDATE_SONGLISTPIC_ERROR(504, "上传歌曲图片失败"),
    //song
    UPLOAD_SONG_ERROR(505, "上传歌曲失败"),
    DELETE_SONG_ERROR(506, "删除歌曲失败"),
    UPDATE_SONG_ERROR(507, "更新歌曲信息失败"),
    UPDATE_SONGPIC_ERROR(508, "更新歌曲图片失败"),
    UPDATE_SONGURL_ERROR(509, "更新歌曲失败"),
    //user
    EXIST_USERNAME(510, "用户名已存在"),
    USERNAME_PASSWORD_ERROR(511, "用户名或密码错误"),
    DELETE_CONSUME_ERROR(512, "删除用户失败"),
    UPDATE_CONSUME_ERROR(513, "更新用户信息失败"),
    UPLOAD_PASSWORD_ERROR(514, "密码输入错误"),
    UPDATE_PASSWORD_ERROR(515, "修改密码失败"),
    UPLOAD_PIC_ERROR(516, "上传失败"),
    //收藏
    COLLECT_MUSIC_ERROR(517, "收藏失败"),
    DISCOLLECT_MUSIC_ERROR(518, "取消收藏失败"),


    ;

    private final Integer code;
    private final String error;

    CommonErrorCode(Integer code, String error) {
        this.code = code;
        this.error = error;
    }
    @Override
    public Integer getCode() {
        return this.code;
    }

    @Override
    public String getError() {
        return this.error;
    }
}
