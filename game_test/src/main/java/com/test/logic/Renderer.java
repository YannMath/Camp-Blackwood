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
    private Interface ui;
    private TerminalSize terminalSize;

    public Renderer() {
    }

    public void renderGame(List<Board> boards) {
        TextGraphics tg = screen.newTextGraphics();

        boolean defaultBackground = true;

        camera.update();
        
        for (Board b : boards) {
            for (int j = 0; j < b.height(); j++) {
                for (int k = 0; k < b.width(); k++) {
                    if (!Validation.isInTerminalWindow(terminalSize, k + b.getX_offset() + camera.getX(), j + b.getY_offset() + camera.getY())) continue;
                    Tile t = b.getTile(k, j);
                        drawTile(tg, t, k + b.getX_offset() + camera.getX(), j + b.getY_offset() + camera.getY(), defaultBackground);
                }
            }
            defaultBackground = false;
        }

        for (int j = 0; j < ui.height(); j++) {
            for (int k = 0; k < ui.width(); k++) {
                if (!Validation.isInTerminalWindow(terminalSize, k + ui.getX_offset(), j + ui.getY_offset())) continue;
                Tile t = ui.getTile(k, j);
                    drawTile(tg, t, k + ui.getX_offset(), j + ui.getY_offset(), false);
            }
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

    public void setScreen(Screen screen) {this.screen = screen; terminalSize = screen.getTerminalSize();}
    public void setCamera(Camera camera) {this.camera = camera;}
    public void setDefaultInterface(Interface defaultInterface) {this.ui = defaultInterface;}
}