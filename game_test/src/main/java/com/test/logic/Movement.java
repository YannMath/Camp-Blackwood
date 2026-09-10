package com.test.logic;

import java.util.List;

import com.test.enums.*;
import com.test.objects.*;

public class Movement {
    private static Player player;
    private static List<Board> boards;
    
    public static void init(Player newPlayer, List<Board> boardsList) {
        player = newPlayer;
        boards = boardsList;
    }

    public static void moveUp() {moveUp(player);}
    public static void moveDown() {moveDown(player);}
    public static void moveLeft() {moveLeft(player);}
    public static void moveRight() {moveRight(player);}

    public static void moveUp(GameObject object) {
        changePosition(object, 0, -1);
    }

    public static void moveDown(GameObject object) {
        changePosition(object, 0, 1);
    }

    public static void moveLeft(GameObject object) {
        changePosition(object, -1, 0);
    }

    public static void moveRight(GameObject object) {
        changePosition(object, 1, 0);
    }

    public static void changePosition(GameObject object, int changeX, int changeY) {
        if (Validation.isValidPosMovement(changeX, changeY, object, boards)) {
            object.setX(object.getX() + changeX);
            object.setY(object.getY() + changeY);

            if (object instanceof Entity entity) {
                Animation.animate(entity, AnimationType.PLAYER_WALKING);
            }
        }
        else {
            new Sound().play("ouch.wav");
        }
    }
}