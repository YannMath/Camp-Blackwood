package com.test.objects;

import java.io.IOException;
import com.test.logic.ConvertTileMaps;
import com.test.logic.InteractionRegistry;

public class GameObject {
    private Tile[][] tiles; 
    private Tilemap tilemap;
    private final Board board;
    private String interaction;
    private int x = 0;
    private int y = 0;

    public GameObject(String filename, Board board, int x, int y) throws IOException {
        tilemap = ConvertTileMaps.convertFile("tilemaps/" + filename + ".txt");
        this.board = board;
        this.x = x;
        this.y = y;
    }

    public void triggerAnimation(Entity e) {
        if (this.interaction != null)
            InteractionRegistry.trigger(interaction, e, this);
    }

    public void setTiles(Tile[][] tiles) {this.tiles = tiles;}
    public void setTilemap(Tilemap tilemap) {this.tilemap = tilemap;}
    public void setTile(Tile t, int x, int y) {tiles[y][x] = t;}
    public void setInteraction(String interaction) {this.interaction = interaction;}
    public void setX(int x) {this.x = x;}
    public void setY(int y) {this.y = y;}

    public Tile[][] getTiles() {return tiles;}
    public Tilemap getTilemap() {return tilemap;}
    public Board getBoard() {return board;}
    public String getInteraction() {return interaction;}
    public boolean hasInteraction() {return interaction != "";}
    public int getX() {return x;} // returns the start x
    public int getY() {return y;} // returns the start y
}
