package com.ql.www.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * @author chocoh
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChatHistoryInfo {
    private Long id;
    private Long userId;
    private Long targetId;
    private String content;
    private Integer type;
    private LocalDateTime createTime;

    private UserInfo userInfo;
}
