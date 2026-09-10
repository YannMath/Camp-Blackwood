package com.test.objects;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import com.test.logic.ConvertTileMaps;

public class GameObject {
    private List<Tile> tiles = new ArrayList<>(); // TODO: Make the tileList into a matrix
    private Tilemap tilemap;
    private final Board board;
    private int x = 0;
    private int y = 0;

    public GameObject(String filename, Board board, int x, int y) throws IOException {
        tilemap = ConvertTileMaps.convertFile("tilemaps/" + filename + ".txt");
        this.board = board;
        this.x = x;
        this.y = y;
    }

    public void addTile(Tile tile) {tiles.add(tile);}
    public void setX(int x) {this.x = x;}
    public void setY(int y) {this.y = y;}

    public List<Tile> getTiles() {return tiles;}
    public Tilemap getTilemap() {return tilemap;}
    public Board getBoard() {return board;}
    public int getX() {return x;} // returns the start x
    public int getY() {return y;} // returns the start y
}
