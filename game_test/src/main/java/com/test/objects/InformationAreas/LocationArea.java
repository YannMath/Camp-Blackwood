package com.test.objects.InformationAreas;

import com.test.objects.InformationArea;
import com.test.objects.Tile;
import com.test.objects.GameState;

public class LocationArea extends InformationArea {
    private String currentLocation;

    public LocationArea(Tile[] tiles) {
        super(tiles);
    }

    @Override
    public void update(Object object) {
        if (!(object instanceof GameState)) return;
        GameState gs = (GameState) object;
        currentLocation = gs.getLocation();
        int difference = tiles.length - currentLocation.length();
        for (int i = 0; i < currentLocation.length(); i++) {
            if (difference >= 0) {
                for (int c = 0; c < currentLocation.length(); c++) {
                    tiles[c].setChar(currentLocation.charAt(c)); 
                    tiles[c].setForeground(tiles[0].getForegroundColor());
                }
                for (int s = currentLocation.length(); s < tiles.length; s++) {
                    tiles[s].setChar(' '); 
                    tiles[s].setForeground(tiles[0].getForegroundColor());
                }

            }
            else {
                for (int c = 0; c < tiles.length; c++) {
                    tiles[c].setChar(currentLocation.charAt(c)); 
                    tiles[c].setForeground(tiles[0].getForegroundColor());
                }
                tiles[tiles.length - 1].setChar('.');
                tiles[tiles.length - 2].setChar('.');
                tiles[tiles.length - 3].setChar('.');
                return;
            }
        }
    }
}
