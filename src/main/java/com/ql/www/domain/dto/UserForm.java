package com.ql.www.domain.dto;

import lombok.Data;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import static com.ql.www.common.Constants.PATTERN_PASSWORD;
import static com.ql.www.common.ResponseMsg.VALIDATE_PASSWORD;
import static com.ql.www.common.ResponseMsg.VALIDATE_USERNAME;

/**
 * @author chocoh
 */
@Data
public class UserForm {
    @Size(min = 1, max = 16, message = VALIDATE_USERNAME)
    private String username;
    @Pattern(regexp = PATTERN_PASSWORD, message = VALIDATE_PASSWORD)
    private String password;
    private String avatar;
}
