package com.ql.www.controller;

import cn.dev33.satoken.stp.StpUtil;
import com.ql.www.domain.dto.UserForm;
import com.ql.www.domain.dto.AuthForm;
import com.ql.www.domain.model.Response;
import com.ql.www.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @author chocoh
 */
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public Response login(@RequestBody @Validated AuthForm authForm) {
        return Response.success(userService.login(authForm.getUsername(), authForm.getPassword()));
    }

    @PostMapping("/logout")
    public Response logout() {
        StpUtil.logout();
        return Response.success();
    }

    @PostMapping("/modify/userInfo")
    public Response modifyUserInfo(@RequestBody @Validated UserForm user) {
        return Response.success(userService.modifyUserInfo(user));
    }

    @GetMapping("/me")
    public Response me() {
        return Response.success(userService.getUserInfo(userService.me()));
    }

    @GetMapping("/{id}")
    public Response getUserById(@PathVariable String id) {
        return Response.success(userService.getUserInfo(userService.getById(id)));
    }
}