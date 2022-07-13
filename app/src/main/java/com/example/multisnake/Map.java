package com.example.multisnake;

import android.graphics.Bitmap;

import java.util.Objects;

public class Map {
    private int x, y, width, height;
    private int i, j;

    public Map(int x, int y, int width, int height, int i, int j) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.i = i;
        this.j = j;
    }

    public Map(Map map) {
        this.x = map.x;
        this.y = map.y;
        this.width = map.width;
        this.height = map.height;
        this.i = map.i;
        this.j = map.j;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getI() {
        return i;
    }

    public void setI(int i) {
        this.i = i;
    }

    public int getJ() {
        return j;
    }

    public void setJ(int j) {
        this.j = j;
    }

    //    @Override
//    public boolean equals(Object o) {
//        if (this == o) return true;
//        if (o == null || getClass() != o.getClass()) return false;
//        Map map = (Map) o;
//        return x == map.x && y == map.y;
//    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }
}
