package com.Blackjack.Server;

import com.Blackjack.Server.DTO.GameResponse;
import com.Blackjack.Server.DTO.MoveResponse;
import com.Blackjack.Server.DTO.SummaryGameResponse;

import java.io.IOException;
import java.util.Random;

public class Game implements Runnable {
    Player p1;
    Player p2;
    private boolean isP1Turn;
    private final Random random = new Random();

    public void setP1(Player p1) {
        this.p1 = p1;
    }

    public void setP2(Player p2) {
        this.p2 = p2;
    }

    public boolean gamesIfFull() {
        return p1 != null && p2 != null;
    }

    public void addPlayer(Player player) {
        if (p1 == null) {
            p1 = player;
        } else if (p2 == null) {
            p2 = player;
        }
    }

    @Override
    public void run() {
        try {
            initGame();
            Player waiting, playing;
            while (gameIsGoOn()) {
                if ((isP1Turn && p1.isPlaying()) || !p2.isPlaying()) {
                    playing = p1;
                    waiting = p2;
                } else {
                    playing = p2;
                    waiting = p1;
                }
                Communication.send(prepareGameResponse(playing, waiting, true), playing.getSocket());
                Communication.send(prepareGameResponse(waiting, playing, false), waiting.getSocket());

                var baseRequest = Communication.receiveBaseRequest(playing.getSocket());
                Communication.receiveBaseRequest(waiting.getSocket());

                if (baseRequest.getCode() == MoveEnum.takeCard.ordinal()) {
                    takeCard(playing, waiting);
                } else if (baseRequest.getCode() == MoveEnum.pass.ordinal()) {
                    pass(playing, waiting);
                }

                getReadyFromBothPlayer();
                isP1Turn = !isP1Turn;
            }
            sendSummaryAndCloseConnection();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private void initGame() throws IOException {
        p1.setResult(0);
        p1.setPlaying(true);
        p2.setResult(0);
        p2.setPlaying(true);
        isP1Turn = true;
        getReadyFromBothPlayer();
    }

    private void sendSummaryAndCloseConnection() throws IOException {
        Communication.send(prepareSummaryGameResult(p1, p2), p1.getSocket());
        Communication.send(prepareSummaryGameResult(p2, p1), p2.getSocket());
        p1.getSocket().close();
        p2.getSocket().close();
    }

    private void takeCard(Player playing, Player waiting) throws IOException {
        var valueOfCard = randCard();
        playing.setResult(playing.getResult() + valueOfCard);
        if (playing.getResult() == 21) {
            p1.setPlaying(false);
            p2.setPlaying(false);
        } else if (playing.getResult() > 21) {
            playing.setPlaying(false);
        }
        Communication.send(prepareMoveResponse(playing, waiting, valueOfCard, MoveEnum.takeCard.ordinal()), playing.getSocket());
        Communication.send(prepareMoveResponse(waiting, playing, valueOfCard, MoveEnum.takeCard.ordinal()), waiting.getSocket());

    }

    private void pass(Player playing, Player waiting) {
        playing.setPlaying(false);
        Communication.send(prepareMoveResponse(playing, waiting, -1, MoveEnum.pass.ordinal()), playing.getSocket());
        Communication.send(prepareMoveResponse(waiting, playing, -1, MoveEnum.pass.ordinal()), waiting.getSocket());
    }

    private int randCard() {
        return switch (random.nextInt((6 - 1) + 1) + 1) {
            case 2 -> 10;
            case 3 -> 2;
            case 4 -> 3;
            case 5 -> 4;
            case 6 -> 11;
            default -> 0;
        };
    }

    private void getReadyFromBothPlayer() throws IOException {
        Communication.receiveBaseRequest(p1.getSocket());
        Communication.receiveBaseRequest(p2.getSocket());
    }

    private GameResponse prepareGameResponse(Player main, Player enemy, boolean isMainPlayerTurn) {
        return new GameResponse(
                isMainPlayerTurn ? BaseCode.yourTurn.ordinal() : BaseCode.enemyTurn.ordinal(),
                enemy.isPlaying(),
                main.getResult(),
                enemy.getResult()
        );
    }

    private MoveResponse prepareMoveResponse(Player main, Player enemy, int points, int move) {
        return new MoveResponse(
                (main.isPlaying() || enemy.isPlaying()) ? BaseCode.gameContinue.ordinal() : BaseCode.endGame.ordinal(),
                move,
                points,
                main.getResult(),
                enemy.getResult()
        );
    }

    private SummaryGameResponse prepareSummaryGameResult(Player player, Player enemy) {
        return new SummaryGameResponse(
                player.getResult(),
                enemy.getResult(),
                getGameResult(player, enemy)
        );
    }

    private int getGameResult(Player player, Player enemy) {
        var p1Result = player.getResult();
        var p2Result = enemy.getResult();
        if (p1Result > 21 && p2Result > 21) {
            return 3;
        }
        var p1Help = 21 - p1Result;
        var p2Help = 21 - p2Result;
        if (p1Help == p2Help) {
            return 2;
        }
        if ((p1Help < p2Help && p1Help >= 0) || p2Help < 0) {
            return 0;
        }
        return 1;
    }

    private boolean gameIsGoOn() {
        return p1.isPlaying() || p2.isPlaying();
    }

}
