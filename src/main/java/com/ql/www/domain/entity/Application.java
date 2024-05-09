package com.ql.www.domain.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author chocoh
 */
@Data
@TableName("t_application")
public class Application {
    private Long id;
    private Long userId;
    private Long targetId;
    private Integer type;
    private String applicationContent;
    private Integer status;
    private LocalDateTime createTime;
}
