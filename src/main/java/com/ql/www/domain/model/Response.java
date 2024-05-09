package com.ql.www.domain.model;
import com.ql.www.common.HttpStatus;
import java.util.HashMap;

/**
 * @author chocoh
 */
public class Response extends HashMap<String, Object> {
    public static final String CODE = "code";
    public static final String MSG = "msg";
    public static final String DATA = "data";

    public static final String SUCCESS_MSG = "操作成功";
    public static final String ERROR_MSG = "操作失败";

    public Response() {
    }

    public Response(int code, String msg, Object data) {
        super.put(CODE, code);
        super.put(MSG, msg);
        super.put(DATA, data);
    }

    @Override
    public Response put(String key, Object value) {
        super.put(key, value);
        return this;
    }

    public static Response success() {
        return new Response(HttpStatus.SUCCESS, SUCCESS_MSG, null);
    }

    public static Response success(Object data) {
        return new Response(HttpStatus.SUCCESS, SUCCESS_MSG, data);
    }

    public static Response success(String msg, Object data) {
        return new Response(HttpStatus.SUCCESS, msg, data);
    }

    public static Response error() {
        return new Response(HttpStatus.ERROR, ERROR_MSG, null);
    }

    public static Response error(String msg) {
        return new Response(HttpStatus.ERROR, msg, null);
    }

    public static Response error(String msg, Object data) {
        return new Response(HttpStatus.ERROR, msg, null);
    }
}
