package com.ql.www.service;

import com.ql.www.domain.entity.Application;
import com.ql.www.domain.vo.ApplicationUserInfo;
import com.ql.www.domain.vo.UserInfo;

import java.util.List;

/**
 * @author chocoh
 */
public interface FriendService {
    /**
     * 获取好友列表
     */
    List<UserInfo> friendList();

    /**
     * 创建好友申请
     * @param application 申请表
     * @return 是否创建成功
     */
    int createFriendApplication(Application application);

    /**
     * 获取所有好友申请列表
     */
    List<ApplicationUserInfo> listApplicationUserInfo();

    /**
     * 同意好友申请
     * @param applicationId 申请表id
     */
    void agreeToFriendApplication(Long applicationId);

    /**
     * 拒绝好友申请
     * @param applicationId 申请表id
     */
    void refuseApplication(Long applicationId);
}
