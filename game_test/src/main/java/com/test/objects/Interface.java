package com.test.objects;

import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;
import com.test.objects.InformationAreas.InformationAreaBuilder;

public class Interface extends Board {
    HashMap<String, InformationArea> infoMap = new HashMap<>();
    List<String> currentInfoAreas = new ArrayList<>();
    private Player player;

    public Interface(int rows, int cols, int y_offset, int x_offset) {
        super(rows, cols, y_offset, x_offset, false);
    }

    public void setInformationArea(String name, Tile[] tiles) {
        infoMap.put(name, InformationAreaBuilder.build(name, tiles));
        currentInfoAreas.add(name);
    }
    public InformationArea[] getCurrentInfoFields() {
        InformationArea[] infoAreas = new InformationArea[currentInfoAreas.size()];
        for (int i = 0; i < currentInfoAreas.size(); i++) infoAreas[i] = infoMap.get(currentInfoAreas.get(i));
        return infoAreas;
    }

    public void setPlayer(Player player) {this.player = player;}

    public Player getPlayer() {return player;}
}
