package com.test.utility;

public class Response {

    public Integer statusCode;
    public String message;
    public Object data;

    public Response() {}

    public Response(Integer statusCode, String message, Object data) {
        this.statusCode = statusCode;
        this.message = message;
        this.data = data;
    }
}
