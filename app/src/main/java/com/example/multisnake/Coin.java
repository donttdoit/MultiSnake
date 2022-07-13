package com.example.multisnake;

import android.graphics.Bitmap;
import android.graphics.Canvas;

public class Coin {
    private int x, y;
    private int i, j;

    public Coin(int x, int y, int i, int j) {
        this.x = x;
        this.y = y;
        this.i = i;
        this.j = j;
    }

    public Coin(Coin coin) {
        this.x = coin.getX();
        this.y = coin.getY();
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

    public void draw(Canvas canvas, Bitmap bm){
        canvas.drawBitmap(bm, x, y, null);
    }
}
