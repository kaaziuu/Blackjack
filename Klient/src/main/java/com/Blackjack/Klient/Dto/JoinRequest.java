package com.Blackjack.Klient.Dto;

public class JoinRequest {
    private String name;

    public JoinRequest(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
