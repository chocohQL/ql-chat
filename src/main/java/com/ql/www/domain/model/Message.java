package com.ql.www.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author chocoh
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Message {
    private Integer type;
    private Object data;

    public static Message getInstance(Integer type, Object data) {
        return new Message(type, data);
    }
}
