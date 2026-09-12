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
        Renderer renderer = new Renderer();
        Camera playerCamera = new Camera();
        playerCamera.setFovX(120);
        playerCamera.setFovY(50);
        renderer.setCamera(playerCamera);

        List<Board> boards = new ArrayList<>();
        Board backgroundBoard = new Board(50, 120, 0, 0, false, false); 
        boards.add(backgroundBoard);
        Board entityBoard = new Board(48, 118, 1, 1, true, false);     
        boards.add(entityBoard);
        Board foregroundBoard = new Board(48, 118, 1, 1, true, false); 
        boards.add(foregroundBoard);
        Board uiBoard = new Board(50, 120, 0, 0, false, true); 
        boards.add(uiBoard);
        
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
        Player player = new Player("player", entityBoard, 2, 20, 100);
        gameObjects.add(player);
        entities.add(player);
        playerCamera.follow(player);
        GameObject bomb = new GameObject("test-bomb", foregroundBoard, 30, 37);
        gameObjects.add(bomb);
        GameObject house = new GameObject("house", foregroundBoard, 20, 20);
        gameObjects.add(house);
        GameObject water2 = new GameObject("water2", foregroundBoard, 60, 40);
        gameObjects.add(water2);

        Movement.init(player, boards);
        Animation.init();

        try {
            DrawComponent.drawBorder(boards, uiBoard, "test_ui");

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