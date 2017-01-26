package com.frostox.livechess.entities;

import chesspresso.game.Game;

/**
 * Created by roger on 10/27/2016.
 */
public class GameEntity {
    private Game game;

    public GameEntity(Game game) {
        this.game = game;
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }
}
