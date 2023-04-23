package com.music.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.music.common.api.ServiceResult;
import com.music.common.enums.CommonErrorCode;
import com.music.dao.SingerMapper;
import com.music.entity.Singer;
import com.music.service.SingerService;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
@Slf4j
@Service
public class SingerServiceImpl extends ServiceImpl<SingerMapper, Singer> implements SingerService {

    private static final String picturePath = "/img/avatorImages/user.jpg";

    @Override
    public ServiceResult addSinger(Singer singer) {
        log.info("添加歌手的信息:{}", singer);
        singer.setPic(picturePath + singer.getId());

        boolean flag = save(singer);
        if(flag){
            return ServiceResult.success("添加成功");
        }else{
            return ServiceResult.failure(CommonErrorCode.Add_SINGER_ERROR);
        }

    }

    @Override
    public ServiceResult deleteSinger(Long id) {
        log.info("删除用户的信息是：{},类型是：{}", id, id.getClass());
        LambdaQueryWrapper<Singer> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(Singer::getId , id);
        boolean flag = remove(lambdaQueryWrapper);
        if(flag){
            return ServiceResult.success("删除成功");
        }else{
            return ServiceResult.failure(CommonErrorCode.DELETE_SINGER_ERROR);
        }

    }

    @Override
    public ServiceResult updateSinger(Singer singer) {

        log.info("要修改的singer信息是：{}",singer);
        LambdaUpdateWrapper<Singer> lambdaUpdateWrapper = new LambdaUpdateWrapper<>();
        lambdaUpdateWrapper.eq(Singer::getId, singer.getId());
        boolean update = update(singer, lambdaUpdateWrapper);

        if(update){
            return ServiceResult.success("修改成功");
        }else{
            return  ServiceResult.failure(CommonErrorCode.UPDATE_SINGER_ERROR);
        }
    }

    @Override
    public ServiceResult updateSingerPic(MultipartFile avatorFile, Long id) {
        log.info("图片的字节是：{}",avatorFile);
        log.info("用户的id:{}", id);
        String fileName = System.currentTimeMillis() + avatorFile.getOriginalFilename();
        String filePath = System.getProperty("user.dir") + System.getProperty("file.separator") + "img"
                + System.getProperty("file.separator") + "singerPic";
        File file1 = new File(filePath);
        if (!file1.exists()) {
            file1.mkdir();
        }

        File dest = new File(filePath + System.getProperty("file.separator") + fileName);
        String imgPath = "/img/singerPic/" + fileName;
        log.info("图片最后的存放路径:{}", imgPath);

        try {
            avatorFile.transferTo(dest);

            LambdaUpdateWrapper<Singer> lambdaUpdateWrapper = new LambdaUpdateWrapper<>();
            lambdaUpdateWrapper.eq(Singer::getId, id).set(Singer::getPic, imgPath);

            boolean res = update(lambdaUpdateWrapper);

            if (res) {
                return ServiceResult.success("上传成功");
            } else {
                return ServiceResult.failure(CommonErrorCode.UPDATE_SINGERPIC_ERROR);
            }
        } catch (IOException e) {
            log.error("上传图片出现错误", e);
            return ServiceResult.failure(CommonErrorCode.UPDATE_SINGERPIC_ERROR);
        }

    }


}
