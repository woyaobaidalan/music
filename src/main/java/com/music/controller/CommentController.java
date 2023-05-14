package com.music.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.music.common.api.ServiceResult;
import com.music.common.enums.CommonErrorCode;
import com.music.entity.Comment;
import com.music.service.CommentService;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/comment")
@RequiresRoles(value = {"all", "administrator"}, logical= Logical.OR)
public class CommentController {

    @Autowired
    private CommentService commentService;

    /**
     * 添加评论
     * @param comment
     * @return
     */
    @PostMapping("/add")
    public ServiceResult addComment(@RequestBody Comment comment){
        return commentService.addComment(comment);

    }

    /**
     * 删除评论
     * @param id
     * @return
     */
    @GetMapping("/delete")
    public ServiceResult deleteComment(@RequestParam("id") Long id){
        return commentService.deleteComment(id);
    }

    /**
     * 获得指定歌曲 ID 的评论列表
     * @param songId
     * @return
     */
    @GetMapping("/song/detail")
    public ServiceResult commentOfSongId(@RequestParam("songId") Long songId){
//        LambdaQueryWrapper<Comment> lambdaQueryWrapper = new LambdaQueryWrapper<>();
//        lambdaQueryWrapper.eq(Comment::getSongId, songId);
//        return ServiceResult.success(null, );commentService.list(lambdaQueryWrapper)
        return commentService.commentOfSongId(songId);
    }

    /**
     * 获得指定歌单 ID 的评论列表
     * @param songListId
     * @return
     */
    @GetMapping("/songList/detail")
    public ServiceResult commentOfSongListId(@RequestParam("songListId") Long songListId){
//        LambdaQueryWrapper<Comment> lambdaQueryWrapper = new LambdaQueryWrapper<>();
//        lambdaQueryWrapper.eq(Comment::getSongListId, songListId);
//        return ServiceResult.success(null, commentService.list(lambdaQueryWrapper));
        return commentService.commentOfSongListId(songListId);
    }

    /**
     * 点赞功能
     * @param comment
     * @return
     */
    @PostMapping("/like")
    public ServiceResult commentOfLike(@RequestBody Comment comment){
        return commentService.commentOfLike(comment);
    }



}
