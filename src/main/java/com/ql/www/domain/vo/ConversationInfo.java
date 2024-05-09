package com.ql.www.domain.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
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
public class ConversationInfo {
    private Long id;
    private Integer type;
    private String previewMessage;
    private Integer previewType;
    private LocalDateTime previewTime;
    private Long userId;
    private Long targetId;
    private LocalDateTime createTime;

    private String previewTimeFormat;
    private String name;
    private String avatar;
}
