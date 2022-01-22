package com.Blackjack.Klient;

import com.Blackjack.Klient.Dto.BaseRequest;
import com.Blackjack.Klient.Dto.JoinRequest;
import com.Blackjack.Klient.Dto.MoveResponse;

import java.io.*;
import java.net.Socket;

public class Game {
    private final Socket socket;
    private String name;
    private final BufferedReader console;

    public Game(Socket socket) {

        this.socket = socket;
        console = new BufferedReader(new InputStreamReader(System.in));
    }

    public void initGame(String name) throws IOException {
        this.name = name;
        Communication.send(new JoinRequest(this.name), socket);
        var codeStatus = Communication.receiveBaseResponse(socket);
        if (codeStatus.getCode() == BaseCode.wait.ordinal()) {
            System.out.println("waiting for other player");
        }
    }

    public void game() throws IOException {
        Communication.send(new BaseRequest(BaseCode.playerReady.ordinal()), socket);
        boolean gameIsContinues = true;
        while (gameIsContinues) {
            var gameResponse = Communication.receiveGameResponse(socket);

            printResult(gameResponse.getYourResult(), gameResponse.getEnemyResult());

            printEnemyStatus(gameResponse.isEnemyPlaying());

            if (isPlayerMove(gameResponse.getCode())) {
                move();
            } else {
                Communication.send(new BaseRequest(BaseCode.playerReady.ordinal()), socket);
            }
            var moveResponse = Communication.receiveMoveResponse(socket);

            printMove(moveResponse, isPlayerMove(gameResponse.getCode()));

            gameIsContinues = moveResponse.getCode() == BaseCode.gameContinue.ordinal();

            Communication.send(new BaseRequest(BaseCode.playerReady.ordinal()), socket);
        }
        printSummary();
    }

    private void printEnemyStatus(boolean isEnemyPlaying) {
        var help = isEnemyPlaying ? "still" : "not";
        System.out.println("Enemy is " + help + " playing");
    }

    private void move() throws IOException {
        System.out.println(MoveEnum.takeCard.ordinal() + "- Take a Card");
        System.out.println(MoveEnum.pass.ordinal() + "- Pass");
        System.out.print("Enter the move: ");
        boolean isCorrectEnter = false;
        int move = -1;
        while (!isCorrectEnter) {
            try {
                move = Integer.parseInt(console.readLine());
                isCorrectEnter = true;
            } catch (Exception e) {
                System.out.println("Bad enter, try again ");
            }
        }
        Communication.send(new BaseRequest(move), socket);
    }

    private boolean isPlayerMove(int code) {
        return code == BaseCode.yourTurn.ordinal();
    }

    private void printSummary() throws IOException {
        var summary = Communication.receiveSummaryGameResponse(socket);

        System.out.println("\n__________________________\n");

        var resultOfGame = switch (summary.getResult()) {
            case 0 -> "You win";
            case 1 -> "You lose";
            case 2 -> "Draw";
            case 3 -> "nobody win";
            default -> throw new IllegalStateException("Unexpected value: " + summary.getResult());
        };

        System.out.println(resultOfGame);
        printResult(summary.getPlayerResult(), summary.getEnemyResult());
    }

    private void printResult(int playerResult, int enemyResult) {
        System.out.println("Your current result: " + playerResult);
        System.out.println("Enemy current result: " + enemyResult);
    }

    private void printMove(MoveResponse response, boolean isPlayerMove) {
        var help = isPlayerMove ? "You" : "Enemy";
        if (response.getMove() == MoveEnum.takeCard.ordinal()) {
            System.out.println(help + " take " + response.getPoints());
        } else {
            System.out.println(help + " pass");
        }
    }
}
