package com.music.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.music.common.util.JwtUtils;
import com.music.dao.AdminMapper;
import com.music.entity.Admin;
import com.music.entity.Consumer;
import com.music.service.AdminService;
import com.music.common.api.ServiceResult;
import com.music.common.enums.CommonErrorCode;
import com.music.service.ConsumerService;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
public class AdminServiceImpl extends ServiceImpl<AdminMapper, Admin> implements AdminService {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private ConsumerService consumerService;

    @Override
    public ServiceResult login(HttpServletResponse response, Admin admin) {
        log.debug("admin的信息是: {}", admin);
        String name = admin.getName();
        String password = admin.getPassword();

        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(name, password);
        LambdaQueryWrapper<Consumer> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(Consumer::getUsername, name);
        //admin和consumer在同一个表里，需要判断是否是管理员，防止错误登录
        Consumer consumer = consumerService.getOne(lambdaQueryWrapper);

        if(consumer == null || !consumer.getRoles().equals("administrator"))
            return ServiceResult.failure(CommonErrorCode.NOT_LOGIN);

        try {
            subject.login(token);
            String jwtToken = JwtUtils.sign(name, JwtUtils.SECRET);
            //将token存入redis中，设置过期时间
            log.info("token的值: {}", jwtToken);
            stringRedisTemplate.opsForValue().set(JwtUtils.AUTH_HEADER, jwtToken, JwtUtils.EXPIRE_TIME, TimeUnit.MILLISECONDS);

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
