package com.Blackjack.Server;

import com.Blackjack.Server.DTO.BaseResponse;

import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        var gameManager = new GameManager();
        List<Thread> threads = new ArrayList<>();
        try {
            var server = new ServerSocket(3030);
            while (true) {
                var client = server.accept();
                var joinRequest = Communication.receiveJoinRequest(client);
                var player = new Player(joinRequest.getName(), client);
                var game = gameManager.findEmptyRoom();
                if (game == null) {
                    game = new Game();
                    gameManager.addGame(game);
                }
                game.addPlayer(player);
                var gameIsFull = game.gamesIfFull();
                var codeStatus = gameIsFull ? BaseCode.gameStart.ordinal() : BaseCode.wait.ordinal();
                Communication.send(new BaseResponse(codeStatus), client);
                if (gameIsFull) {
                    Thread thread = new Thread(game);
                    thread.start();
                    threads.add(thread);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
