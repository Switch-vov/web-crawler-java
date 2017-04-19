package com.pc.pojo;

/**
 * Created by Switch on 2017/4/19.
 */
public class HttpResult {
    // 响应码
    private Integer code;
    // 响应体
    private String body;

    public HttpResult() {

    }

    public HttpResult(Integer code, String body) {
        this.code = code;
        this.body = body;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
}
