package com.music.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.music.dao.AdminMapper;
import com.music.entity.Admin;
import com.music.service.AdminService;
import com.music.common.api.ServiceResult;
import com.music.common.enums.CommonErrorCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
@Slf4j
@Service
public class AdminServiceImpl extends ServiceImpl<AdminMapper, Admin> implements AdminService {
    private static final String SESSION_KEY = "name";
    @Override
    public ServiceResult login(HttpServletRequest request, Admin admin) {
        log.info("this is imformation want {}", admin);
        String name = admin.getName();
        String password = admin.getPassword();

        LambdaQueryWrapper<Admin> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(Admin::getName, name);
        Admin one = getOne(lambdaQueryWrapper);

        if(one == null) return ServiceResult.failure(CommonErrorCode.NOT_LOGIN);

        if(one.getPassword().equals(admin.getPassword())){
            request.getSession().setAttribute(SESSION_KEY, name);
            return ServiceResult.success("登录成功");
        }else{
            return ServiceResult.failure(CommonErrorCode.NOT_LOGIN);
        }

    }
}
