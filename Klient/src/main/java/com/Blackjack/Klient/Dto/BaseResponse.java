package com.Blackjack.Klient.Dto;

public class BaseResponse {
    protected int code;

    public BaseResponse() {
    }

    public BaseResponse(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
