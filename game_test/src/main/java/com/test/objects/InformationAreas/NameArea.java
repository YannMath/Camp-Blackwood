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
        for (int i = 0; i < playerName.length(); i++) {
            boolean longEnough = isLongEnough(i);
            if (longEnough) {
                for (int c = 0; c < playerName.length(); c++) {
                    tiles[c].setChar(playerName.charAt(c)); tiles[c].setForeground(tiles[0].getForegroundColor());
                }
                for (int s = playerName.length(); s < tiles.length; s++) {
                    tiles[s].setChar(' '); tiles[s].setForeground(tiles[0].getForegroundColor());
                }

            }
            else {
                tiles[i - 1].setChar('.');
                tiles[i - 2].setChar('.');
                tiles[i - 3].setChar('.');
                return;
            }
        }
    }

    private boolean isLongEnough(int i) {
        return i < tiles.length;
    }
}
