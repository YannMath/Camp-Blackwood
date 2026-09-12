package com.test.objects.InformationAreas;

import com.test.objects.InformationArea;
import com.test.objects.Tile;
import com.test.objects.Player;

public class HealthArea extends InformationArea{
    int currentHealth;
    
    public HealthArea(Tile[] tiles) {
        super(tiles);
    }

    @Override
    public void update(Object object) {
        Player player = (Player) object;
        currentHealth = player.getHealth();
        int filledTiles = currentHealth * tiles.length / player.getMaxHealth();
        for (int i = 0; i < tiles.length; i++) {
            if (i < filledTiles)
                tiles[i].setChar('▲');
            else 
                tiles[i].setChar(' ');
        }
    }
}
