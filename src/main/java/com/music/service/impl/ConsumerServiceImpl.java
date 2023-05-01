package com.music.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.music.common.api.Constants;
import com.music.common.api.ServiceResult;
import com.music.common.enums.CommonErrorCode;
import com.music.common.util.JwtUtils;
import com.music.dao.ConsumerMapper;
import com.music.dto.ConsumerDto;
import com.music.entity.Consumer;
import com.music.service.ConsumerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;

@Slf4j
@Service
public class ConsumerServiceImpl extends ServiceImpl<ConsumerMapper, Consumer> implements ConsumerService {
    @Override
    public ServiceResult add(Consumer consumer) {
        LambdaQueryWrapper<Consumer> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(Consumer::getUsername, consumer.getUsername());
        Consumer one = getOne(lambdaQueryWrapper);

        if(one != null) {
            return ServiceResult.failure(CommonErrorCode.EXIST_USERNAME);
        }

        boolean flag = save(consumer);

        if(flag){
            return ServiceResult.success("注册成功");
        }else{
            return ServiceResult.failure(CommonErrorCode.EXIST_USERNAME);
        }

    }

    @Override
    public ServiceResult loginStatus(Consumer consumer, HttpServletResponse httpServletResponse) {
        log.info("登录信息是：{}", consumer);
        LambdaQueryWrapper<Consumer> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(Consumer::getUsername, consumer.getUsername());
        Consumer one = getOne(lambdaQueryWrapper);

        if(one == null){
            return ServiceResult.failure(CommonErrorCode.USERNAME_PASSWORD_ERROR);
        }

        boolean flag = one.getPassword().equals(consumer.getPassword());
        if(flag){


            return ServiceResult.success("登录成功", list(lambdaQueryWrapper));
        }else{
            return ServiceResult.failure(CommonErrorCode.USERNAME_PASSWORD_ERROR);
        }

    }

    @Override
    public ServiceResult updateUserMsg(Consumer consumer) {
        LambdaQueryWrapper<Consumer> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(Consumer::getUsername, consumer.getUsername());
        Consumer one = getOne(lambdaQueryWrapper);

        if(one != null) {
            return ServiceResult.failure(CommonErrorCode.EXIST_USERNAME);
        }

        boolean flag = updateById(consumer);
        if (flag) {
            return ServiceResult.success("修改成功");
        } else {
            return ServiceResult.failure(CommonErrorCode.UPDATE_CONSUME_ERROR);
        }
    }

    @Override
    public ServiceResult updatePassword(ConsumerDto consumerDto) {
        LambdaQueryWrapper<Consumer> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(Consumer::getId, consumerDto.getId());
        Consumer one = getOne(lambdaQueryWrapper);

        if(!one.getPassword().equals(consumerDto.getOld_password())){
            return ServiceResult.failure(CommonErrorCode.UPLOAD_PASSWORD_ERROR);
        }

        LambdaUpdateWrapper<Consumer> lambdaUpdateWrapper = new LambdaUpdateWrapper<>();
        lambdaUpdateWrapper.eq(Consumer::getId, consumerDto.getId())
                .set(Consumer::getPassword, consumerDto.getPassword());
        boolean flag = update(lambdaUpdateWrapper);

        if(flag){
            return ServiceResult.success("修改密码成功");
        }else{
            return ServiceResult.failure(CommonErrorCode.UPDATE_PASSWORD_ERROR);
        }

    }

    @Override
    public ServiceResult updateUserPic(MultipartFile avatorFile, Long id) {
        String fileName = System.currentTimeMillis() + avatorFile.getOriginalFilename();
        String filePath = Constants.PROJECT_PATH + System.getProperty("file.separator") + "img" + System.getProperty("file.separator") + "avatorImages";
        File file1 = new File(filePath);
        if (!file1.exists()) {
            file1.mkdir();
        }

        File dest = new File(filePath + System.getProperty("file.separator") + fileName);
        String imgPath = "/img/avatorImages/" + fileName;

        try {
            avatorFile.transferTo(dest);
            Consumer consumer = new Consumer();
            LambdaUpdateWrapper<Consumer> lambdaUpdateWrapper = new LambdaUpdateWrapper<>();
            lambdaUpdateWrapper.eq(Consumer::getId, id)
                    .set(Consumer::getAvator, imgPath);
            boolean flag = update(lambdaUpdateWrapper);

            if(flag){
                return ServiceResult.success("上传成功");
            }else{
                return ServiceResult.failure(CommonErrorCode.UPLOAD_PIC_ERROR);
            }

        } catch (IOException e) {
            log.error("添加头像失败", e);
        }

        return ServiceResult.failure(CommonErrorCode.UPLOAD_PIC_ERROR);
    }
}
