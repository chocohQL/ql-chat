package com.ql.www.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.crypto.digest.DigestUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ql.www.common.ResponseMsg;
import com.ql.www.domain.dto.UserForm;
import com.ql.www.domain.entity.User;
import com.ql.www.domain.vo.UserInfo;
import com.ql.www.exception.GlobalException;
import com.ql.www.mapper.UserMapper;
import com.ql.www.service.FileService;
import com.ql.www.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author chocoh
 */
@Service
@Slf4j
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private FileService fileService;

    @Override
    public String login(String username, String password) {
        User user = getUserByUsername(username);
        if (user == null) {
            throw new GlobalException(ResponseMsg.USERNAME_NOT_FOUNT);
        }
        if (DigestUtil.bcryptCheck(password, user.getPassword())) {
            StpUtil.login(user.getId());
            return StpUtil.getTokenInfo().getTokenValue();
        } else {
            throw new GlobalException(ResponseMsg.PASSWORD_ERROR);
        }
    }

    @Override
    public UserInfo modifyUserInfo(UserForm user) {
        User me = me();
        if (StringUtils.isNotEmpty(user.getAvatar())) {
            fileService.deleteImg(me.getAvatar());
            me.setAvatar(user.getAvatar());
        }
        if (StringUtils.isNotEmpty(user.getUsername()) && !user.getUsername().equals(me.getUsername())) {
            if (getUserByUsername(user.getUsername()) == null) {
                me.setUsername(user.getUsername());
            } else {
                throw new GlobalException(ResponseMsg.USERNAME_EXIST);
            }
        }
        userMapper.updateById(me);
        return getUserInfo(me);
    }

    @Override
    public UserInfo getUserInfo(User user) {
        UserInfo userInfo = new UserInfo();
        BeanUtils.copyProperties(user, userInfo);
        return userInfo;
    }

    @Override
    public User me() {
        return userMapper.selectById(StpUtil.getLoginIdAsLong());
    }

    private User getUserByUsername(String username) {
        return userMapper.selectOne(new LambdaQueryWrapper<User>().eq(User::getUsername, username));
    }

    public boolean isLogin(String id) {
        return StpUtil.isLogin(id);
    }
}
