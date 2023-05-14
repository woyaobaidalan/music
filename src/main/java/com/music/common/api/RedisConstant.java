package com.music.common.api;

public class RedisConstant {
    //指定用户收藏歌曲列表的id
    public static final String USER_COLLECTIONLIST_KEY = "collect:user:";

    //根据用户id，从redis中获取信息
    public static final String CONSUMER_GETBYID_KEY = "consumer:getOne:";

    //根据songId获取评论
    public static final String COMMENT_GETBYSONGID_KEY = "comment:getBySongId:";
    //根据songListId获取歌单评论
    public static final String COMMENT_GETBYSONGListID_KEY = "comment:getBySongListId:";
    //根据歌手id获取歌曲信息
    public static final String SONG_GETBYSINGERID_KEY = "song:getBySingerId:";

}
