package com.music.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.music.entity.Admin;
import com.music.common.api.ServiceResult;

import javax.servlet.http.HttpServletRequest;

public interface AdminService extends IService<Admin> {

    ServiceResult login(HttpServletRequest request, Admin admin);
}
