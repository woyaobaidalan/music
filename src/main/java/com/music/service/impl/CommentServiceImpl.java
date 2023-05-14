package com.music.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.music.common.api.RedisConstant;
import com.music.common.api.ServiceResult;
import com.music.common.enums.CommonErrorCode;
import com.music.dao.CommentMapper;
import com.music.entity.Comment;
import com.music.entity.Consumer;
import com.music.service.CommentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
@Slf4j
@Service
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements CommentService {

    @Autowired
    private RedisTemplate<Object, Object> redisTemplate;

    /**
     * 点在功能
     * @param comment
     * @return
     */
    @Override
    public ServiceResult commentOfLike(Comment comment) {

        LambdaUpdateWrapper<Comment> lambdaUpdateWrapper = new LambdaUpdateWrapper<>();
        lambdaUpdateWrapper.eq(Comment::getId, comment.getId())
                .set(Comment::getUp, comment.getUp() + 1);
        log.info("comment的信息是：{}", comment);

        boolean flag = update(lambdaUpdateWrapper);
        if (flag) {
            //添加歌单、歌曲评论到redis
            addCommentLikeToRedis(comment);
            return ServiceResult.success("点赞成功");
        }else{
            return ServiceResult.failure(CommonErrorCode.COMMENT_LIKEADD_ERROR);
        }
    }

    /**
     * 更改redis中评论点赞数
     * @param comment
     */
    private void addCommentLikeToRedis(Comment comment){
        Long songId = comment.getSongId();
        Long songListId = comment.getSongListId();
        log.info("songListId = {}", songListId);
        //判断是否为歌曲
        if(songId != null){
            List<Comment> redisList = (List<Comment>) redisTemplate.opsForValue().get(RedisConstant.COMMENT_GETBYSONGID_KEY + songId);
            //因为已经获取评论，redis中必定存在，无需判断是否在redis中
            for(Comment c : redisList){
                if(c.getId().equals(comment.getId())){
                    c.setUp(c.getUp() + 1);
                }
            }
            redisTemplate.opsForValue().set(RedisConstant.COMMENT_GETBYSONGID_KEY + songId.toString(), redisList);
        }
        //  判断是否是歌单
        if(songListId != null){
            List<Comment> redisList = (List<Comment>) redisTemplate.opsForValue().get(RedisConstant.COMMENT_GETBYSONGListID_KEY + songListId.toString());
            //因为已经获取评论，redis中必定存在，无需判断是否在redis中
            for(Comment c : redisList){
                if(c.getId().equals(comment.getId())){
                    c.setUp(c.getUp() + 1);
                }
            }
            redisTemplate.opsForValue().set(RedisConstant.COMMENT_GETBYSONGListID_KEY + songListId.toString(), redisList);

        }
    }

    /**
     * 添加评论
     * @param comment
     * @return
     */
    @Override
    public ServiceResult addComment(Comment comment) {
        comment.setCreateTime(new Date());
        boolean flag = save(comment);

        if(flag){
            //添加评论时，添加进数据库，修改redis缓存
            addCommentToRedis(comment);
            return ServiceResult.success("评论成功");
        }else{
            return ServiceResult.failure(CommonErrorCode.COMMENT_ADD_ERROR);
        }

    }

    /**
     * 添加评论到指定歌曲、歌单
     * @param comment
     */
    private void addCommentToRedis(Comment comment){

        //获取列表歌单的评论
        Long songId = comment.getSongId();
        Long songListId = comment.getSongListId();

        //将comment添加进相应的歌曲中
        if(songId != null){
            List<Comment> redisList = (List<Comment>) redisTemplate.opsForValue().get(RedisConstant.COMMENT_GETBYSONGID_KEY + songId);
            if(redisList != null){
                redisList.add(comment);
                redisTemplate.opsForValue().set(RedisConstant.COMMENT_GETBYSONGID_KEY + songId, redisList);
            }
        }
        //将comment添加进相应的歌单缓存中
        if(songListId != null){
            List<Comment> redisList = (List<Comment>) redisTemplate.opsForValue().get(RedisConstant.COMMENT_GETBYSONGListID_KEY + songListId);
            if(redisList != null){
                redisList.add(comment);
                redisTemplate.opsForValue().set(RedisConstant.COMMENT_GETBYSONGListID_KEY + songListId, redisList);
            }
        }
    }





    //获取指定
    @Override
    public ServiceResult commentOfSongId(Long songId) {

        List<Comment> redisList = (List<Comment>) redisTemplate.opsForValue().get(RedisConstant.COMMENT_GETBYSONGID_KEY + songId);
        //判断从redis是否有数据
        if(redisList != null) {
            return ServiceResult.success(null, redisList);
        }
        //从数据库中获取评论信息
        LambdaQueryWrapper<Comment> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(Comment::getSongId, songId);

        List<Comment> list = list(lambdaQueryWrapper);

        //存入redis中
        redisTemplate.opsForValue().set(RedisConstant.COMMENT_GETBYSONGID_KEY + songId, list);

        return ServiceResult.success(null, list);

    }

    /**
     * 根据SongListId获取评论
     * @param songListId
     * @return
     */
    @Override
    public ServiceResult commentOfSongListId(Long songListId) {

        List<Comment> redisList = (List<Comment>) redisTemplate.opsForValue().get(RedisConstant.COMMENT_GETBYSONGListID_KEY + songListId);
        //判断从redis是否有数据
        if(redisList != null) {
            return ServiceResult.success(null, redisList);
        }
        //从数据库中获取评论信息
        LambdaQueryWrapper<Comment> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(Comment::getSongListId, songListId);

        List<Comment> list = list(lambdaQueryWrapper);

        //存入redis中
        redisTemplate.opsForValue().set(RedisConstant.COMMENT_GETBYSONGListID_KEY + songListId, list);

        return ServiceResult.success(null, list);
    }

    @Override
    public ServiceResult deleteComment(Long id) {

        boolean flag = removeById(id);
        if(flag){
            deleteRedisComment(id);
            return ServiceResult.success("删除成功");
        }else{
            return ServiceResult.failure(CommonErrorCode.COMMENT_DELETE_ERROR);
        }
    }

    /**
     * 删除redis中存储的评论
     * @param id
     */
    private void deleteRedisComment(Long id){
        Comment comment = getById(id);

        Long songId = comment.getSongId();
        Long songListId = comment.getSongListId();

        //判断是否为歌曲
        if(songId != null){
            List<Comment> redisList = (List<Comment>) redisTemplate.opsForValue().get(RedisConstant.COMMENT_GETBYSONGID_KEY + songId);
            //因为已经获取评论，redis中必定存在，无需判断是否在redis中
            redisList.remove(comment);
            redisTemplate.opsForValue().set(RedisConstant.COMMENT_GETBYSONGID_KEY + songId, redisList);

        }
        //  判断是否是歌单
        if(songListId != null){
            List<Comment> redisList = (List<Comment>) redisTemplate.opsForValue().get(RedisConstant.COMMENT_GETBYSONGListID_KEY + songListId);
            //因为已经获取评论，redis中必定存在，无需判断是否在redis中
            redisList.remove(comment);
            redisTemplate.opsForValue().set(RedisConstant.COMMENT_GETBYSONGListID_KEY + songListId, redisList);
        }

    }
}
