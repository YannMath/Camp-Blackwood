package com.test.logic;

import com.test.objects.Board;
import com.test.objects.Tile;
import java.util.List;
import com.googlecode.lanterna.screen.*;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.*;

public class Renderer {
    private Screen screen;

    public Renderer() {
    }

    public void renderGame(List<Board> boards) {
        TextGraphics tg = screen.newTextGraphics();

        boolean defaultBackground = true;
        
        for (Board b : boards) {
            for (int j = 0; j < b.height(); j++) {
                for (int k = 0; k < b.width(); k++) {
                    Tile t = b.getTile(k, j);
                    drawTile(tg, t, k + b.getX_offset(), j + b.getY_offset(), defaultBackground);
                }
            }
            defaultBackground = false;
        }
    }

    private void drawTile(TextGraphics tg, Tile tile, int x, int y, boolean defaultBackground) {
        if (tile.getChar() == ' ' && TextColor.ANSI.DEFAULT.equals(tile.getBackgroundColor())) {
            if (defaultBackground) {
                tg.setBackgroundColor(TextColor.ANSI.BLUE);
                tg.setForegroundColor(TextColor.ANSI.CYAN);
                tg.setCharacter(x, y, '.');
            }
            return;
        }

        tg.setBackgroundColor(tile.getBackgroundColor());
        tg.setForegroundColor(tile.getForegroundColor());
        tg.setCharacter(x, y, tile.getChar());
    }

    public void setScreen(Screen screen) {this.screen = screen;}
}