package com.test.logic;

import java.util.List;
import com.test.objects.*;

public class Validation {
    public static boolean isValidPos(int x, int y, Board board) {
        return x >= 0 &&
               y >= 0 &&
               x < board.width() &&
               y < board.height();
    }

    public static boolean isValidPosMovement(int changeX, int changeY, GameObject object, List<Board> boards) {
        boolean isOccupied = false;
        for (Tile t : object.getTiles()) {
            int x = t.getX();
            int y = t.getY();
            if (!isValidPos(x + changeX, y + changeY, object.getBoard())) return false;
            for (Board b : boards) {
                if (!b.hasCollision()) continue;
                int checkedX = x + changeX + (object.getBoard().getX_offset() - b.getX_offset());
                int checkedY = y + changeY + (object.getBoard().getY_offset() - b.getY_offset());
                if (!isValidPos(checkedX, checkedY, b)) return false;
                Tile checkedTile = b.getTile(checkedX, checkedY);
                if (checkedTile.getParent() == object) continue;
                if (checkedTile.isOccupied()) isOccupied = true;
            }
        }
        return !isOccupied;
    }
}
