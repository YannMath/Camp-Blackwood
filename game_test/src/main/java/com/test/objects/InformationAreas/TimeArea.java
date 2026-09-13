package com.test.objects.InformationAreas;

import com.test.objects.InformationArea;
import com.test.objects.Tile;
import com.test.objects.GameState;

public class TimeArea extends InformationArea {
    private int currentMinute;
    private int currentHour;
    
    public TimeArea(Tile[] tiles) {
        super(tiles);
    }

    @Override
    public void update(Object object) {
        if (!(object instanceof GameState)) return;
        GameState gs = (GameState) object;
        currentMinute = gs.getMinute();
        currentHour = gs.getHour();
        int filledTiles = 8;
        if (filledTiles > tiles.length) {System.out.println("Not enough tiles to display time"); return;}

        String minute = String.valueOf(currentMinute);
        String hour = String.valueOf(currentHour);
        if (currentMinute < 10) minute = "0" + minute;
        if (currentHour < 10) hour = "0" + hour;

        for (int i = 0; i < 2; i++) {
            tiles[i].setChar(hour.charAt(i));
            tiles[i + 3].setChar(minute.charAt(i));
        }

        tiles[2].setChar(':');

        if (currentHour < 24 && currentHour > 11) {
            tiles[6].setChar('P');
            tiles[7].setChar('M');
        } else {
            tiles[6].setChar('A');
            tiles[7].setChar('M');
        }
    }
}
