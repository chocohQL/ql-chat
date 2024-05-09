package com.ql.www.exception;

import cn.dev33.satoken.exception.NotLoginException;
import com.ql.www.common.HttpStatus;
import com.ql.www.domain.model.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author chocoh
 */
@Slf4j
@RestControllerAdvice
public class CustomExceptionHandler {
    @ExceptionHandler(GlobalException.class)
    public Response handlerGlobalException(GlobalException e) {
        return Response.error(e.getMessage());
    }

    @ExceptionHandler(NotLoginException.class)
    public Response handlerNotLoginException(NotLoginException nle) {
        String message;
        if(nle.getType().equals(NotLoginException.NOT_TOKEN)) {
            message = "未能读取到有效 token";
        } else if(nle.getType().equals(NotLoginException.INVALID_TOKEN)) {
            message = "token 无效";
        } else if(nle.getType().equals(NotLoginException.TOKEN_TIMEOUT)) {
            message = "token 已过期";
        } else if(nle.getType().equals(NotLoginException.NO_PREFIX)) {
            message = "未按照指定前缀提交 token";
        } else {
            message = "当前会话未登录";
        }
        return new Response(HttpStatus.UNAUTHORIZED, message, null);
    }

    /**
     * 处理@RequestBody接口参数校验异常
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Response handleValidationException(MethodArgumentNotValidException e) {
        return Response.error(e.getBindingResult().getAllErrors().get(0).getDefaultMessage());
    }
}
