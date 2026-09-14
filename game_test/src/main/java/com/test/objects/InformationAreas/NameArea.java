package com.test.objects.InformationAreas;

import com.test.objects.InformationArea;
import com.test.objects.Player;
import com.test.objects.Tile;

public class NameArea extends InformationArea {
    String playerName;
    
    public NameArea(Tile[] tiles) {
        super(tiles);
    }

    @Override
    public void update(Object object) {
        if (!(object instanceof Player)) return;
        Player player = (Player) object;
        playerName = player.getName();
        int difference = tiles.length - playerName.length();
        if (difference >= 0) {
            for (int c = 0; c < playerName.length(); c++) {
                tiles[c].setChar(playerName.charAt(c)); 
                tiles[c].setForeground(tiles[0].getForegroundColor());
            }
            for (int s = playerName.length(); s < tiles.length; s++) {
                tiles[s].setChar(' '); 
                tiles[s].setForeground(tiles[0].getForegroundColor());
            }
        
        }
        else {
            for (int c = 0; c < tiles.length; c++) {
                tiles[c].setChar(playerName.charAt(c)); 
                tiles[c].setForeground(tiles[0].getForegroundColor());
            }
            tiles[tiles.length - 1].setChar('.');
            tiles[tiles.length - 2].setChar('.');
            tiles[tiles.length - 3].setChar('.');
            return;
        }
    }
}