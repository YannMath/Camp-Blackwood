package com.test.objects;

import com.googlecode.lanterna.TextColor;

public class Tilemap {
    private char[][] sprite;
    private TextColor[][] background;
    private TextColor[][] foreground;

    public Tilemap(char[][] sprite, TextColor[][] background, TextColor[][] foreground) {
        this.sprite = sprite;
        this.background = background;
        this.foreground = foreground;
    }

    public char[][] getSprite() {return sprite;}
    public TextColor[][] getBackground() {return background;}
    public TextColor[][] getForeground() {return foreground;}
} 
