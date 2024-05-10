package com.ql.www.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.ql.www.domain.entity.Application;
import com.ql.www.domain.entity.UserFriend;
import com.ql.www.domain.vo.ApplicationUserInfo;
import com.ql.www.domain.vo.ConversationInfo;
import com.ql.www.domain.vo.UserInfo;
import com.ql.www.mapper.ApplicationMapper;
import com.ql.www.mapper.UserFriendMapper;
import com.ql.www.service.ChatService;
import com.ql.www.service.FriendService;
import com.ql.www.service.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author chocoh
 */
@Service
public class FriendServiceImpl implements FriendService {
    @Autowired
    private ApplicationMapper applicationMapper;
    @Autowired
    private UserFriendMapper userFriendMapper;
    @Autowired
    private UserService userService;
    @Autowired
    private ChatService chatService;

    @Override
    public List<UserInfo> friendList() {
        List<UserInfo> friendList = new ArrayList<>();
        userFriendMapper.selectList(new LambdaQueryWrapper<UserFriend>()
                        .eq(UserFriend::getUserId, StpUtil.getLoginIdAsLong()))
                .forEach(userFriend ->
                        friendList.add(
                                userService.getUserInfo(userService.getById(userFriend.getFriendId()))));
        return friendList;
    }

    @Override
    public int createFriendApplication(Application application) {
        // 判断是否已存在未同意的好友申请
        Application oldApplication = applicationMapper.selectOne(new LambdaQueryWrapper<Application>()
                .eq(Application::getUserId, StpUtil.getLoginIdAsLong())
                .eq(Application::getTargetId, application.getTargetId())
                .eq(Application::getStatus, 0)
                .eq(Application::getType, 0));
        if (oldApplication != null) {
            return 0;
        }
        return application.getType() == 0 ? applicationMapper.insert(application) : 0;
    }

    @Override
    public List<ApplicationUserInfo> listApplicationUserInfo() {
        List<ApplicationUserInfo> applicationUserInfos = new ArrayList<>();
        // 获取用户的所有好友申请
        applicationMapper.selectList(new LambdaQueryWrapper<Application>()
                        .eq(Application::getTargetId, StpUtil.getLoginIdAsLong())
                        .eq(Application::getType, 0))
                // 封装对应的ApplicationUserInfo
                .forEach(application -> {
                    ApplicationUserInfo applicationUserInfo = new ApplicationUserInfo();
                    BeanUtils.copyProperties(application, applicationUserInfo);
                    UserInfo userInfo = userService.getUserInfo(userService.getById(application.getUserId()));
                    applicationUserInfo.setUserInfo(userInfo);
                    applicationUserInfos.add(applicationUserInfo);
                });
        // 根据时间排序
        return applicationUserInfos.stream()
                .sorted(Comparator.comparing(ApplicationUserInfo::getCreateTime).reversed())
                .collect(Collectors.toList());
    }

    @Override
    public void agreeToFriendApplication(Long applicationId) {
        Application application = applicationMapper.selectById(applicationId);
        // 判断类型和状态
        if (application.getStatus() == 0 && application.getType() == 1) {
            // 设置为为已添加
            application.setStatus(1);
            // 创建好友关系
            userFriendMapper.insert(getFriend(application.getUserId(), application.getTargetId()));
            userFriendMapper.insert(getFriend(application.getTargetId(), application.getUserId()));
            // 创建双方会话
            chatService.createConversation(application.getUserId(), application.getTargetId(), 0);
            chatService.createConversation(application.getTargetId(), application.getUserId(), 0);
        }
        // 更新申请表
        applicationMapper.updateById(application);
    }

    @Override
    public void refuseApplication(Long applicationId) {
        Application application = applicationMapper.selectById(applicationId);
        application.setStatus(2);
        applicationMapper.updateById(application);
    }

    private UserFriend getFriend(Long userId, Long targetId) {
        UserFriend userFriend = new UserFriend();
        userFriend.setFriendId(userId);
        userFriend.setFriendId(targetId);
        return userFriend;
    }
}
