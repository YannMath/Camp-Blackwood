package com.test.objects;

import com.googlecode.lanterna.TextColor;

public class Tile {
    char c = ' ';
    TextColor background = TextColor.ANSI.DEFAULT;
    TextColor foreground = TextColor.ANSI.DEFAULT;
    int x = 0;
    int y = 0;
    boolean isEditable = true;
    boolean occupied = false;
    GameObject parent;

    public Tile(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void setChar(char c) {if (isEditable) this.c = c;}
    public void setBackground(TextColor background) {if (isEditable) this.background = background;}
    public void setForeground(TextColor foreground) {if (isEditable) this.foreground = foreground;}
    public void setX(int x) {this.x = x;}
    public void setY(int y) {this.y = y;}
    public void setEditable(boolean b) {this.isEditable = b;}
    public void setOccupied(boolean occupied) {this.occupied = occupied;}
    public void setParent(GameObject parent) {this.parent = parent;}
    
    public char getChar() {return c;}
    public TextColor getBackgroundColor() {return background;}
    public TextColor getForegroundColor() {return foreground;}
    public int getX() {return x;}
    public int getY() {return y;}
    public boolean isEditable() {return isEditable;}
    public boolean isOccupied() {return occupied;}
    public GameObject getParent() {return parent;}
}