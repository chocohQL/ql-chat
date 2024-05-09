package com.ql.www.domain.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author chocoh
 */
@Data
@TableName("t_conversation")
public class Conversation {
    private Long id;
    private Integer type;
    private String previewMessage;
    private Integer previewType;
    private LocalDateTime previewTime;
    private Long userId;
    private Long targetId;
    private LocalDateTime createTime;
}
