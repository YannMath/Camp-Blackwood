package com.test.objects;

public class Board {
    private int rows = 0;
    private int cols = 0;
    private final int y_offset;
    private final int x_offset;
    private Tile[][] board;
    private final boolean collision;

    public Board(int rows, int cols, int y_offset, int x_offset, boolean collision) {
        this.rows = rows;
        this.cols = cols;
        this.y_offset = y_offset;
        this.x_offset = x_offset;
        this.collision = collision;

        board = new Tile[rows][cols];

        for (int j = 0; j < rows; j++) {
            for (int k = 0; k < cols; k++) {
                board[j][k] = new Tile(k, j);
            }
        }
    }

    public int height() {return rows;}
    public int width() {return cols;}
    public Tile getTile(int x, int y) {return board[y][x];}
    public int getY_offset() {return y_offset;}
    public int getX_offset() {return x_offset;}
    public boolean hasCollision() {return collision;}
}