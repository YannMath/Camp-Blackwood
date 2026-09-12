package com.test.objects;

public class Camera {
    Board[] boards;
    Entity followedEntity;
    int fovX;
    int fovY;
    int posX;
    int posY;

    public Camera() {
        
    }

    public void follow(Entity e) {
        followedEntity = e;
    }

    public void update() {
        if (followedEntity == null) return;
        posX = fovX / 2 - followedEntity.getX() - followedEntity.getBoard().getX_offset();
        posY = fovY / 2 - followedEntity.getY() - followedEntity.getBoard().getY_offset();
    }

    public void setFovX(int fovX) {this.fovX = fovX;}
    public void setFovY(int fovY) {this.fovY = fovY;}

    public int getX() {return posX;}
    public int getY() {return posY;}
}
