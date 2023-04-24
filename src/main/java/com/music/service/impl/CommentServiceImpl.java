package com.music.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.music.common.api.ServiceResult;
import com.music.common.enums.CommonErrorCode;
import com.music.dao.CommentMapper;
import com.music.entity.Comment;
import com.music.service.CommentService;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements CommentService {
    @Override
    public ServiceResult commentOfLike(Comment comment) {

        LambdaUpdateWrapper<Comment> lambdaUpdateWrapper = new LambdaUpdateWrapper<>();
        lambdaUpdateWrapper.eq(Comment::getId, comment.getId())
                .set(Comment::getUp, comment.getUp());

        boolean flag = update(lambdaUpdateWrapper);
        if (flag) {
            return ServiceResult.success("点赞成功");
        }else{
            return ServiceResult.failure(CommonErrorCode.COMMENT_LIKEADD_ERROR);
        }
    }

    @Override
    public ServiceResult addComment(Comment comment) {
        comment.setCreateTime(new Date());

        boolean flag = save(comment);
        if(flag){
            return ServiceResult.success("评论成功");
        }else{
            return ServiceResult.failure(CommonErrorCode.COMMENT_ADD_ERROR);
        }

    }
}
