package com.test.logic;

import com.test.objects.*;
import java.util.List;
import com.googlecode.lanterna.screen.*;
import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.*;

public class Renderer {
    private Screen screen;
    private Camera camera;

    public Renderer() {
    }

    public void renderGame(List<Board> boards) {
        TextGraphics tg = screen.newTextGraphics();

        boolean defaultBackground = true;

        camera.update();
        
        for (Board b : boards) {
            for (int j = 0; j < b.height(); j++) {
                for (int k = 0; k < b.width(); k++) {
                    Tile t = b.getTile(k, j);
                    if (b.isUi())
                        drawTile(tg, t, k + b.getX_offset(), j + b.getY_offset(), defaultBackground);
                    else 
                        drawTile(tg, t, k + b.getX_offset() + camera.getX(), j + b.getY_offset() + camera.getY(), defaultBackground);
                }
            }
            defaultBackground = false;
        }
    }

    private void drawTile(TextGraphics tg, Tile tile, int x, int y, boolean defaultBackground) {
        TerminalSize terminalSize = screen.getTerminalSize();
        if (x < 0 || y < 0 || x >= terminalSize.getColumns() || y >= terminalSize.getRows()) {
            return;
        }

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
    public void setCamera(Camera camera) {this.camera = camera;}
    }