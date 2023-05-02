package com.music.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.music.common.api.ServiceResult;
import com.music.common.enums.CommonErrorCode;
import com.music.dto.ConsumerDto;
import com.music.entity.Consumer;
import com.music.entity.Song;
import com.music.service.ConsumerService;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
@Slf4j
@RestController
@RequestMapping("/user")
public class ConsumerController {

    @Autowired
    private ConsumerService consumerService;

    /**
     * 用户注册
     *
     * @param consumer 用户注册时发过来的信息
     * @return ServiceResult
     */
    @PostMapping("/add")
    public ServiceResult addUser(@RequestBody Consumer consumer) {
        return consumerService.add(consumer);
    }

    /**
     * 用户登录功能
     *
     * @param consumer           包含username , password
     * @param httpServletResponse session
     * @return
     */
    @PostMapping("/login/status")
    public ServiceResult loginStatus(@RequestBody Consumer consumer, HttpServletResponse httpServletResponse) {
        return consumerService.loginStatus(consumer, httpServletResponse);
    }

    /**
     * 返回所有用户
     *
     * @return
     */
    @GetMapping
    @RequiresRoles(value = {"all", "administrator"}, logical= Logical.OR)
    public ServiceResult allUser() {
        return ServiceResult.success(null, consumerService.list());
    }

    /**
     * 返回指定 ID 的用户
     *
     * @param id 指定 ID
     * @return
     */
    @GetMapping("/detail")
    @RequiresRoles(value = {"all", "administrator"}, logical= Logical.OR)
    public ServiceResult userOfId(@RequestParam("id") Long id) {
        return consumerService.userOfId(id);

    }

    /**
     * 删除用户
     *
     * @param id 主键id
     * @return ServiceResult
     */
    @GetMapping("/delete")
    @RequiresRoles(value = {"all", "administrator"}, logical= Logical.OR)
    public ServiceResult deleteUser(@RequestParam("id") Long id) {
        boolean flag = consumerService.removeById(id);
        if (flag) {
            return ServiceResult.success("删除成功");
        } else {
            return ServiceResult.failure(CommonErrorCode.DELETE_CONSUME_ERROR);
        }
    }

    /**
     * 用户更新信息
     *
     * @param consumer 更改的信息
     * @return
     */
    @PostMapping("/update")
    @RequiresRoles(value = {"all", "administrator"}, logical= Logical.OR)
    public ServiceResult updateUserMsg(@RequestBody Consumer consumer) {
        return consumerService.updateUserMsg(consumer);
    }

    /**
     * 更新用户密码
     * @param consumer
     * @return
     */
    @PostMapping("/updatePassword")
    @RequiresRoles(value = {"all", "administrator"}, logical= Logical.OR)
    public ServiceResult updatePassword(@RequestBody ConsumerDto consumer){
        return consumerService.updatePassword(consumer);
    }

    /**
     *
     * @param avatorFile
     * @param id
     * @return
     */
    @PostMapping("/avatar/update")
    public ServiceResult updateUserPic(@RequestParam("file") MultipartFile avatorFile, @RequestParam("id") Long id){
        return consumerService.updateUserPic(avatorFile, id);
    }


}
