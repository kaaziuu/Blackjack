package com.Blackjack.Server.DTO;

public class GameResponse extends  BaseResponse{
    private boolean isEnemyPlaying;
    private int yourResult;
    private int enemyResult;

    public GameResponse(){}

    public GameResponse(int code, boolean isEnemyPlaying, int yourResult, int enemyResult) {
        super(code);
        this.isEnemyPlaying = isEnemyPlaying;
        this.yourResult = yourResult;
        this.enemyResult = enemyResult;
    }

    public boolean isEnemyPlaying() {
        return isEnemyPlaying;
    }

    public void setEnemyPlaying(boolean enemyPlaying) {
        isEnemyPlaying = enemyPlaying;
    }

    public int getYourResult() {
        return yourResult;
    }

    public void setYourResult(int yourResult) {
        this.yourResult = yourResult;
    }

    public int getEnemyResult() {
        return enemyResult;
    }

    public void setEnemyResult(int enemyResult) {
        this.enemyResult = enemyResult;
    }
}
