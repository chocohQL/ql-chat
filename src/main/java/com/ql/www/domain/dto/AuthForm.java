package com.ql.www.domain.dto;

import com.ql.www.common.Constants;
import com.ql.www.common.ResponseMsg;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/**
 * @author chocoh
 */
@Data
public class AuthForm {
    @NotNull
    @Size(min = 1, max = 16, message = ResponseMsg.VALIDATE_USERNAME)
    private String username;
    @NotNull
    @Pattern(regexp = Constants.PATTERN_PASSWORD, message = ResponseMsg.VALIDATE_PASSWORD)
    private String password;
}
