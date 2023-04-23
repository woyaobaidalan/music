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
