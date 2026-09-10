package com.test.logic;

import java.io.IOException;
import com.test.enums.GameState;
import com.googlecode.lanterna.screen.*;
import com.googlecode.lanterna.input.*;;

public class Input 
{
    private static Screen screen;
    private static GameState gameState = GameState.PLAYING;

    public static void init(Screen newScreen) {
        screen = newScreen;
    }

    public static void getInput() throws IOException {
        if (screen == null) {
            throw new IllegalStateException("Input.init() wurde nicht aufgerufen!");
        }
        KeyStroke key;
        KeyStroke latestMovement = null;

        while ((key = screen.pollInput()) != null) {
            if (isMovementKey(key)) {
                latestMovement = key;
            } else if (key.getKeyType() == KeyType.Character && key.getCharacter() == 'q') {
                System.out.println("You opened the quest menu");
            }
        }

        if (gameState == GameState.PLAYING && latestMovement != null) {
            move(latestMovement);
        }
    }

    private static boolean isMovementKey(KeyStroke key) {
        return key.getKeyType() == KeyType.ArrowUp ||
               key.getKeyType() == KeyType.ArrowLeft ||
               key.getKeyType() == KeyType.ArrowDown ||
               key.getKeyType() == KeyType.ArrowRight ||
               key.getKeyType() == KeyType.Character &&
               (key.getCharacter() == 'w' || key.getCharacter() == 'a' ||
                key.getCharacter() == 's' || key.getCharacter() == 'd');
    }

    private static void move(KeyStroke key) {
        Character character = key.getCharacter();
        if (key.getKeyType() == KeyType.ArrowUp || Character.valueOf('w').equals(character)) Movement.moveUp();
        if (key.getKeyType() == KeyType.ArrowLeft || Character.valueOf('a').equals(character)) Movement.moveLeft();
        if (key.getKeyType() == KeyType.ArrowDown || Character.valueOf('s').equals(character)) Movement.moveDown();
        if (key.getKeyType() == KeyType.ArrowRight || Character.valueOf('d').equals(character)) Movement.moveRight();
    }

    public void setGameState(GameState newGameState) {gameState = newGameState;}

    public GameState getGameState() {return gameState;}
}