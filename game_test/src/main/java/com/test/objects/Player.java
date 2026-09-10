package com.test.objects;

import java.io.IOException;

public class Player extends Entity {
    public Player(String filename, Board board, int x, int y) throws IOException {
        super(filename, board, x, y);
    }
}
