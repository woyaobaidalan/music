package com.music.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.music.common.api.ServiceResult;
import com.music.entity.Comment;

public interface CommentService extends IService<Comment> {
    ServiceResult commentOfLike(Comment comment);

    ServiceResult addComment(Comment comment);
}
