package com.test.logic;

import java.io.IOException;
import java.util.List;
import com.googlecode.lanterna.TextColor;
import com.test.objects.*;

public class DrawComponent {
    public static void clearBoard(Board board) {
        for (int y = 0; y < board.height(); y++) {
            for (int x = 0; x < board.width(); x++) {
                Tile tile = board.getTile(x, y);
                tile.setChar(' ');
                tile.setBackground(TextColor.ANSI.DEFAULT);
                tile.setForeground(TextColor.ANSI.DEFAULT);
                tile.setOccupied(false);
                tile.setParent(null);
            }
        }
    }

    public static void drawBorder(List<Board> boards, Interface ui) throws IOException {
        Tilemap tm = ui.getTilemap();

        char[][] characterMap = tm.getSprite();
        TextColor[][] background = tm.getBackground();
        TextColor[][] foreground = tm.getForeground();

        for (int y = 0; y < characterMap.length; y++) {
            for (int x = 0; x < characterMap[y].length; x++) {
                int uiX = x;
                int uiY = y;

                if (Validation.isValidPos(uiX, uiY, ui)) {
                    Tile tile = ui.getTile(uiX, uiY);

                    tile.setChar(characterMap[y][x]);
                    tile.setBackground(background[y][x]);
                    tile.setForeground(foreground[y][x]); 
                }
            }
        }

        for (InformationArea infoArea : ui.getCurrentInfoFields()) {
            infoArea.update(ui.getPlayer());
        }
    }

    public static void drawObject(Board board, GameObject object, int start_x, int start_y) throws IOException {
        Tilemap tm = object.getTilemap();
        char[][] characterMap = tm.getSprite();
        TextColor[][] background = tm.getBackground();
        TextColor[][] foreground = tm.getForeground();
        
        object.setTiles(new Tile[characterMap.length][characterMap[0].length]);
        for (Tile[] tArray : object.getTiles()) {
            for (Tile t : tArray) {
                if (t == null) break;
                t.setChar(' ');
                t.setBackground(TextColor.ANSI.DEFAULT);
                t.setForeground(TextColor.ANSI.DEFAULT);
            }
        }

        for (int y = 0; y < characterMap.length; y++) {
            for (int x = 0; x < characterMap[y].length; x++) {
                int boardX = x + start_x;
                int boardY = y + start_y;

                if (Validation.isValidPos(boardX, boardY, board)) {
                    Tile tile = board.getTile(boardX, boardY);

                    tile.setChar(characterMap[y][x]);
                    tile.setBackground(background[y][x]);
                    tile.setForeground(foreground[y][x]);

                    object.setTile(tile, x, y);
                    tile.setParent(object);
                    tile.setOccupied(true);
                }
            }
        }
    }
}