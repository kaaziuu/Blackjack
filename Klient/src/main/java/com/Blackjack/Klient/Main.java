package com.Blackjack.Klient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class Main {
    public static void main(String[] args) {
        try {
            var console = new BufferedReader(new InputStreamReader(System.in));
            System.out.print("Enter yours nick: ");
            var name = console.readLine();
            Socket socket = new Socket("localhost", 3030);
            var game = new Game(socket);
            game.initGame(name);
            game.game();

        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Lost connection with server..., Sorry");
        }
    }
}
