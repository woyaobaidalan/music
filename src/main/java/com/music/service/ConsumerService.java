package com.music.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.music.common.api.ServiceResult;
import com.music.dto.ConsumerDto;
import com.music.entity.Consumer;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;

public interface ConsumerService extends IService<Consumer> {
    ServiceResult add(Consumer consumer);

    ServiceResult loginStatus(Consumer consumer, HttpServletRequest httpServletRequest);

    ServiceResult updateUserMsg(Consumer consumer);

    ServiceResult updatePassword(ConsumerDto consumer);

    ServiceResult updateUserPic(MultipartFile avatorFile, Long id);
}
