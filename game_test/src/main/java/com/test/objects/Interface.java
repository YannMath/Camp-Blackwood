package com.test.objects;

import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;
import com.test.objects.InformationAreas.InformationAreaBuilder;

public class Interface extends Board {
    private HashMap<String, InformationArea> infoMap = new HashMap<>();
    private List<String> currentInfoAreas = new ArrayList<>();
    private Tilemap tm;
    private Player player;
    private GameState gameState;

    public Interface(int rows, int cols, int y_offset, int x_offset, Tilemap tm) {
        super(rows, cols, y_offset, x_offset, false);
        this.tm = tm;
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
    public void setTilemap(Tilemap tm) {this.tm = tm;}
    public void setGameState(GameState gs) {this.gameState = gs;}

    public Player getPlayer() {return player;}
    public Tilemap getTilemap() {return tm;}
    public GameState getGameState() {return gameState;}
}
