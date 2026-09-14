package com.test.logic;

import java.util.List;

import com.googlecode.lanterna.TerminalSize;
import com.test.objects.*;

public class Validation {
    // basic check that can be applied everywhere
    public static boolean isValidPos(int x, int y, Board board) {
        return x >= 0 &&
               y >= 0 &&
               x < board.width() &&
               y < board.height();
    }

    public static boolean isInTerminalWindow(TerminalSize ts, int x, int y) {
        if (x < 0 || y < 0 || x >= ts.getColumns() || y >= ts.getRows()) {
            return false;
        }
        return true;
    }

    public static boolean isValidPosMovement(int changeX, int changeY, GameObject object, List<Board> boards) {
        int currentBoardIdx = boards.indexOf(object.getBoard());
        if (currentBoardIdx == -1) return false;
        
        Tile[] lastRowArray = object.getTiles()[object.getTiles().length - 1];   // only checks last row when moving vertically
        Tile[][] lastRow = {lastRowArray};       
        List<Board> relevantBoardsBackground = boards.subList(0, currentBoardIdx)   ;
        List<Board> relevantBoardsForeground = boards.subList(currentBoardIdx, boards.size());   

        boolean isOccupied = postionValidation(false, relevantBoardsForeground, object.getTiles(), object, changeX, changeY)
            || postionValidation(false, relevantBoardsBackground, lastRow, object, changeX, changeY);

        return !isOccupied;
    }

    private static boolean postionValidation(boolean isOccupied, List<Board> checkedBoards, Tile[][] checkedTiles, GameObject object, int changeX, int changeY) {
        for (Tile[] tiles : checkedTiles)  
            for (Tile t : tiles) {
                int x = t.getX();
                int y = t.getY();
                if (!isValidPos(x + changeX, y + changeY, object.getBoard())) return true;
                for (Board b : checkedBoards) {
                    if (!b.hasCollision()) continue;
                    int checkedX = x + changeX + (object.getBoard().getX_offset() - b.getX_offset());
                    int checkedY = y + changeY + (object.getBoard().getY_offset() - b.getY_offset());
                    if (!isValidPos(checkedX, checkedY, b)) return true;
                    Tile checkedTile = b.getTile(checkedX, checkedY);
                    if (checkedTile.getParent() == object) continue;
                    if (checkedTile.isOccupied()) isOccupied = true;
                }
            }
        return isOccupied;
    }

    public static boolean checkInteraction(Entity e, int changeX, int changeY, List<Board> boards) {
        // horizontal check
        if (changeX != 0) {
            int targetX = e.getX() + (changeX > 0 ? e.getTiles()[0].length : -1);
            for (int yOffset = 0; yOffset < e.getTiles().length; yOffset++) {
                int targetY = e.getY() + yOffset;

                if (isValidPos(targetX, targetY, e.getBoard())) {
                    Tile tile = e.getBoard().getTile(targetX, targetY);
                    if (triggerIfInteraction(tile, e)) return true;
                }
            }
        }

        // vertical check
        else if (changeY != 0) {
            int currentBoardIdx = boards.indexOf(e.getBoard());
            if (currentBoardIdx == -1) return false;

            List<Board> relevantBoards = boards.subList(0, currentBoardIdx);

            Tile[] bottomRow = e.getTiles()[e.getTiles().length - 1]; // only checks last row if player moves vertically

            for (Board board : relevantBoards) {
                for (Tile tile : bottomRow) {
                    int targetX = tile.getX();
                    int targetY = tile.getY() + changeY;

                    if (isValidPos(targetX, targetY, board)) {
                        Tile targetTile = board.getTile(targetX, targetY);
                        if (triggerIfInteraction(targetTile, e)) return true;
                    }
                }
            }
        }
        return false;
    }
    
    private static boolean triggerIfInteraction(Tile tile, Entity e) {
        if (tile != null && tile.getParent() != null && tile.getParent() != e) {
            GameObject go = tile.getParent();
            if (go.hasInteraction()) {
                go.triggerAnimation(e);
                return true;
            }
        }
        return false;
    }
}