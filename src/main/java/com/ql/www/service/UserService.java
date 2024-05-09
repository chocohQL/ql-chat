package com.ql.www.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ql.www.domain.dto.UserForm;
import com.ql.www.domain.entity.User;
import com.ql.www.domain.vo.UserInfo;

/**
 * @author chocoh
 */
public interface UserService extends IService<User> {
    String login(String username, String password);

    User modifyUserInfo(UserForm user);

    User me();

    UserInfo getUserInfo(User user);
}
