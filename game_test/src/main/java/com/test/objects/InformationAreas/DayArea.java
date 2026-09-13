package com.test.objects.InformationAreas;

import com.test.objects.GameState;
import com.test.objects.InformationArea;
import com.test.objects.Tile;

public class DayArea extends InformationArea {
    int currentDay;
    
    public DayArea(Tile[] tiles) {
        super(tiles);
    }

    @Override
    public void update(Object object) {
        if (!(object instanceof GameState)) return;
        GameState gs = (GameState) object;
        currentDay = gs.getDay();
        int filledTiles = 2;
        String day = String.valueOf(currentDay);
        if (filledTiles > tiles.length) {System.out.println("Not enough tiles to display day"); return;}
        if (currentDay > 9) {
            tiles[0].setChar(day.charAt(0));
            tiles[1].setChar(day.charAt(1));
        }
        else {
            tiles[0].setChar('0');
            tiles[1].setChar(day.charAt(0));
        }
    }
}
