package com.test.objects;

import java.io.IOException;

import com.test.enums.AnimationType;

public class Entity extends GameObject {
    private AnimationType entityState;
    private int maxHealth;
    private int health;

    public Entity(String filename, Board board, int x, int y, int maxHealth) throws IOException {
        super(filename, board, x, y);
        this.maxHealth = maxHealth;
        this.health = maxHealth;
    }

    public void setEntityState(AnimationType entityState) {this.entityState = entityState;}
    public void addHealth(int addedHealth) {this.health += addedHealth; if (health > maxHealth) health = maxHealth;}

    public AnimationType getEntityState() {return entityState;}
    public boolean isDead() {return health <= 0;}
}
