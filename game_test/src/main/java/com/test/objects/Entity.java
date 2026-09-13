package com.test.objects;

import java.io.IOException;

import com.test.enums.AnimationType;

public class Entity extends GameObject {
    private AnimationType entityState;
    private int maxHealth;
    private int health;
    private final String name;

    public Entity(String filename, Board board, int x, int y, int maxHealth, String name) throws IOException {
        super(filename, board, x, y);
        this.maxHealth = maxHealth;
        this.health = maxHealth;
        this.name = name;
    }

    public void setEntityState(AnimationType entityState) {this.entityState = entityState;}
    public void addHealth(int addedHealth) {this.health += addedHealth; if (health > maxHealth) health = maxHealth;}
    public void receiveDamage(int damage) {this.health -= damage; if (health < 0) health = 0;}

    public AnimationType getEntityState() {return entityState;}
    public int getHealth() {return health;}
    public int getMaxHealth() {return maxHealth;}
    public boolean isDead() {return health <= 0;}
    public String getName() {return name;}
}
