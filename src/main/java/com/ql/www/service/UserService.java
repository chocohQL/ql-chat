package com.ql.www.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ql.www.domain.dto.UserForm;
import com.ql.www.domain.entity.User;
import com.ql.www.domain.vo.UserInfo;

/**
 * @author chocoh
 */
public interface UserService extends IService<User> {
    /**
     * 用户登录
     * @param username 用户名
     * @param password 密码
     * @return token
     */
    String login(String username, String password);

    /**
     * 修改用户信息
     * @param user 用户信息表单
     * @return 修改后的用户
     */
    UserInfo modifyUserInfo(UserForm user);

    /**
     * 获取用户信息
     * @param user 用户
     * @return 用户信息
     */
    UserInfo getUserInfo(User user);

    /**
     * 获取登录用户
     * @return 登录用户
     */
    User me();
}
