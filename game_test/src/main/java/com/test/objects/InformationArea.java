package com.test.objects;

public abstract class InformationArea {
    protected Tile[] tiles;

    public InformationArea(Tile[] tiles) {
        this.tiles = tiles;
    }

    public abstract void update(Object value);  // Updates the information

    public Tile[] getTiles() { return tiles; }

    // Helper method for all sublasses
    protected void writeText(String text) {
        for (int i = 0; i < tiles.length && i < text.length(); i++) {
            tiles[i].setChar(text.charAt(i));
        }
    }
}
