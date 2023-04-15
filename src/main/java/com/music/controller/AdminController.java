package com.music.controller;

import com.music.entity.Admin;
import com.music.service.AdminService;
import com.music.common.api.ServiceResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @PostMapping("/login/status")
    public ServiceResult login( HttpServletRequest request){
        Admin admin = new Admin();
        admin.setName(request.getParameter("name"));
        admin.setPassword(request.getParameter("password"));
        return adminService.login(request, admin);
    }
}
