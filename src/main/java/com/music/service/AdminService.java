package com.music.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.music.entity.Admin;
import com.music.common.api.ServiceResult;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface AdminService extends IService<Admin> {

    ServiceResult login(HttpServletResponse response, Admin admin);
}
