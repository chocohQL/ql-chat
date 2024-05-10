package com.ql.www.domain.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author chocoh
 */
@Data
@TableName("t_user_friend")
public class UserFriend {
    private Long id;
    private Long userId;
    private Long friendId;
    private LocalDateTime createTime;
}
