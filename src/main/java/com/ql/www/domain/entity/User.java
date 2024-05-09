package com.ql.www.domain.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author chocoh
 */
@Data
@TableName("t_user")
public class User {
    private Long id;
    private String username;
    private String password;
    private String avatar;
    private LocalDateTime createTime;
}
