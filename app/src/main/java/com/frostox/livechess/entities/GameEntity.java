package com.frostox.livechess.entities;

/**
 * Created by roger on 10/27/2016.
 */
public class GameEntity {
    private String pgn;

    public GameEntity(String pgn) {

        this.pgn = pgn;
    }

    public String getPgn() {
        return pgn;
    }

    public void setPgn(String pgn) {
        this.pgn = pgn;
    }

}
