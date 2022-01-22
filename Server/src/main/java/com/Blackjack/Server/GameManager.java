package com.Blackjack.Server;

import java.util.ArrayList;
import java.util.List;

public class GameManager {
    public List<Game> games;

    public GameManager() {
        games = new ArrayList<>();
    }

    public Game findEmptyRoom() {
        for (var game : games) {
            if (!game.gamesIfFull()) {
                return game;
            }
        }
        return null;
    }

    public void addGame(Game game) {
        this.games.add(game);
    }
}
