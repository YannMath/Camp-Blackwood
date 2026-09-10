package com.test.objects;

import java.io.IOException;

import com.test.enums.AnimationType;

public class Entity extends GameObject {
    private AnimationType entityState;

    public Entity(String filename, Board board, int x, int y) throws IOException {
        super(filename, board, x, y);
    }

    public void setEntityState(AnimationType entityState) {this.entityState = entityState;}

    public AnimationType getEntityState() {return entityState;}
}
