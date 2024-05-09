package com.ql.www.domain.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author chocoh
 */
@Data
@TableName("t_chat_history")
public class ChatHistory {
    private Long id;
    private Long userId;
    private Long targetId;
    private String content;
    private Integer type;
    private LocalDateTime createTime;
}
