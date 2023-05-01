package com.music.controller;

import com.music.entity.Admin;
import com.music.service.AdminService;
import com.music.common.api.ServiceResult;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @PostMapping(value = "/login/status")
    public ServiceResult login(HttpServletResponse response, @RequestBody Admin admin){
//        Admin admin = new Admin();
//        admin.setName(request.getParameter("name"));
//        admin.setPassword(request.getParameter("password"));
        return adminService.login(response, admin);
    }
}
