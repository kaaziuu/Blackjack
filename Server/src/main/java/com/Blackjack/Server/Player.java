package com.Blackjack.Server;

import java.net.Socket;

public class Player {
    private String nick;
    private Socket socket;
    private boolean isPlaying;
    private int result;

    public Player() {
    }

    public Player(String nick, Socket socket) {
        this.nick = nick;
        this.socket = socket;
    }

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public Socket getSocket() {
        return socket;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }

    public boolean isPlaying() {
        return isPlaying;
    }

    public void setPlaying(boolean playing) {
        isPlaying = playing;
    }

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }
}
