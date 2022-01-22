package com.Blackjack.Server.DTO;

public class MoveResponse extends BaseResponse {
    int move;
    int points = 0;
    int yourResult;
    int enemyResult;

    public MoveResponse() {
    }

    public MoveResponse(int code, int move, int points, int yourResult, int enemyResult) {
        super(code);
        this.move = move;
        this.points = points;
        this.yourResult = yourResult;
        this.enemyResult = enemyResult;
    }

    public int getMove() {
        return move;
    }

    public void setMove(int move) {
        this.move = move;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
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
