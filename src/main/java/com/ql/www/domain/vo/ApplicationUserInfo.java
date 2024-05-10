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
public class ApplicationUserInfo {
    private Long id;
    private Long userId;
    private Long targetId;
    private Integer type;
    private String applicationContent;
    private Integer status;
    private LocalDateTime createTime;

    private UserInfo userInfo;
}
