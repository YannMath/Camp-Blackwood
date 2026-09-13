package com.test;

import com.googlecode.lanterna.terminal.Terminal;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.screen.TerminalScreen;
import com.test.logic.*;
import com.test.objects.*;
import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) throws Exception {
        GameState gameSate = new GameState();
        Renderer renderer = new Renderer();
        Camera playerCamera = new Camera();
        playerCamera.setFovX(120);
        playerCamera.setFovY(50);
        renderer.setCamera(playerCamera);

        List<Board> boards = new ArrayList<>();
        Board backgroundBoard = new Board(50, 120, 0, 0, false); 
        boards.add(backgroundBoard);
        Board entityBoard = new Board(50, 120, 0, 0, true);     
        boards.add(entityBoard);
        Board foregroundBoard = new Board(50, 120, 0, 0, true); 
        boards.add(foregroundBoard);
        Interface uiBoard = ConvertUI.convertUI("cabin_test");
        uiBoard.setGameState(gameSate);
        renderer.setDefaultInterface(uiBoard);
        
        Terminal terminal = new DefaultTerminalFactory()
            .setPreferTerminalEmulator(true)
            .setInitialTerminalSize(new TerminalSize(backgroundBoard.width(), backgroundBoard.height()))
            .createTerminal();

        Screen screen = new TerminalScreen(terminal);
        screen.startScreen();
        screen.setCursorPosition(null);
        renderer.setScreen(screen);
        Input.init(screen);   // Initialize the static Input class

        List<GameObject> gameObjects = new ArrayList<>();
        List<Entity> entities = new ArrayList<>(); 
        Player player = new Player("player", entityBoard, 2, 20, 100, "Chris");
        gameObjects.add(player);
        entities.add(player);
        playerCamera.follow(player);
        uiBoard.setPlayer(player);
        GameObject bomb = new GameObject("test-bomb", foregroundBoard, 30, 37);
        gameObjects.add(bomb);
        GameObject house = new GameObject("house", foregroundBoard, 20, 20);
        gameObjects.add(house);

        Movement.init(player, boards);
        Animation.init();

        try {
            boolean running = true;

            while (running) {
                Input.getInput();
                for (Entity e : entities) {
                    Animation.update(e);
                }
                DrawComponent.clearBoard(entityBoard);
                DrawComponent.clearBoard(foregroundBoard);
                for (GameObject object : gameObjects) {
                    DrawComponent.drawObject(object.getBoard(), object, object.getX(), object.getY());
                }   
                DrawComponent.drawBorder(boards, uiBoard);
                screen.clear();          // deletes the BUFFER (not the Terminal!)
                renderer.renderGame(boards);
                screen.refresh();    

                Thread.sleep(50); // tick-rate
            }
        } finally {
            screen.close(); // Close the screen, even if the loop throws an exception
        }
    }
}