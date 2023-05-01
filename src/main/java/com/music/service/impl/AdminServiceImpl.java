package com.music.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.music.common.util.JwtUtils;
import com.music.dao.AdminMapper;
import com.music.entity.Admin;
import com.music.service.AdminService;
import com.music.common.api.ServiceResult;
import com.music.common.enums.CommonErrorCode;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
@Service
public class AdminServiceImpl extends ServiceImpl<AdminMapper, Admin> implements AdminService {
    private static final String SESSION_KEY = "name";
//    @Override
//    public ServiceResult login(HttpServletRequest request, Admin admin) {
//        log.debug("admin的信息是: {}", admin);
//        String name = admin.getName();
//        String password = admin.getPassword();
//
//        LambdaQueryWrapper<Admin> lambdaQueryWrapper = new LambdaQueryWrapper<>();
//        lambdaQueryWrapper.eq(Admin::getName, name);
//        Admin one = getOne(lambdaQueryWrapper);
//
//        if(one == null) return ServiceResult.failure(CommonErrorCode.NOT_LOGIN);
//
//        if(one.getPassword().equals(password)){
//            request.getSession().setAttribute(SESSION_KEY, name);
//            return ServiceResult.success("登录成功");
//        }else{
//            return ServiceResult.failure(CommonErrorCode.NOT_LOGIN);
//        }
//
//    }

    @Override
    public ServiceResult login(HttpServletResponse response, Admin admin) {
        log.debug("admin的信息是: {}", admin);
        String name = admin.getName();
        String password = admin.getPassword();

        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(name, password);

        try {
            subject.login(token);
            String jwtToken = JwtUtils.sign(name, JwtUtils.SECRET);
            response.setHeader(JwtUtils.AUTH_HEADER, jwtToken);
            response.setHeader("Access-Control-Expose-Headers", JwtUtils.AUTH_HEADER);
            return  ServiceResult.success("登录成功");

        } catch (UnknownAccountException uae) { // 账号不存在
            return ServiceResult.failure(CommonErrorCode.NOT_LOGIN);
        } catch (IncorrectCredentialsException ice) { // 账号与密码不匹配
            return ServiceResult.failure(CommonErrorCode.NOT_LOGIN);
        } catch (AuthenticationException ae) { // 其他身份验证异常
            return ServiceResult.failure(CommonErrorCode.NOT_LOGIN);
        }




    }
}
