package com.Blackjack.Klient.Dto;

public class BaseRequest {
    public int code;

    public BaseRequest() {
    }

    public BaseRequest(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
