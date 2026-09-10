package com.test.objects;

import java.io.IOException;

import com.test.enums.PlayerState;

public class Player extends GameObject {
    PlayerState playerState = PlayerState.STILL;

    public Player(String filename, Board board, int x, int y) throws IOException {
        super(filename, board, x, y);
    }

    public void setPlayerState(PlayerState playerState) {this.playerState = playerState;}

    public PlayerState getPlayerState() {return playerState;}
}
