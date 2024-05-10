package com.ql.www.domain.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author chocoh
 */
@Data
@TableName("t_user_group")
public class UserGroup {
    private Long id;
    private Long userId;
    private Long groupId;
    private Integer role;
    private LocalDateTime createTime;
}