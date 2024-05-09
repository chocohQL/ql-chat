package com.ql.www.domain.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author chocoh
 */
@Data
@TableName("t_group")
public class Group {
    private Long id;
    private String groupName;
    private String avatar;
    private LocalDateTime createTime;
}
