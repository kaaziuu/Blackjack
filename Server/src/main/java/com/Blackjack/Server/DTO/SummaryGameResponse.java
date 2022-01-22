package com.Blackjack.Server.DTO;

public class SummaryGameResponse {
    private int playerResult;
    private int enemyResult;
    private int result;

    public SummaryGameResponse() {
    }

    public SummaryGameResponse(int playerResult, int enemyResult, int result) {
        this.playerResult = playerResult;
        this.enemyResult = enemyResult;
        this.result = result;
    }

    public int getPlayerResult() {
        return playerResult;
    }

    public void setPlayerResult(int playerResult) {
        this.playerResult = playerResult;
    }

    public int getEnemyResult() {
        return enemyResult;
    }

    public void setEnemyResult(int enemyResult) {
        this.enemyResult = enemyResult;
    }

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }
}
