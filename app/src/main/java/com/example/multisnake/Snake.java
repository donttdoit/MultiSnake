package com.example.multisnake;

import android.graphics.Bitmap;
import android.graphics.Canvas;

import java.util.ArrayList;

public class Snake {
    private Bitmap bm;
    private int x, y;
    private int length;
    private ArrayList<Map> arrSnake;

    boolean moving_left, moving_right, moving_top, moving_bottom;

    public Snake(Bitmap bm, int x, int y, int length) {
        this.bm = bm;
        this.length = length;

        this.bm = Bitmap.createBitmap(bm, 0, 0, GameView.sizeOfMap, GameView.sizeOfMap);
        arrSnake = new ArrayList<>();
        for (int i = 0; i < length; ++i){
            arrSnake.add(new Map(x - i * GameView.sizeOfMap, y, GameView.sizeOfMap, GameView.sizeOfMap, 0, length - 1 - i));
        }

        setMoving_right(true);
    }

    static class PartSnake{
        private Bitmap bm;
        private int x, y;

        public PartSnake(Bitmap bm, int x, int y) {
            this.bm = bm;
            this.x = x;
            this.y = y;
        }

        public Bitmap getBm() {
            return bm;
        }

        public void setBm(Bitmap bm) {
            this.bm = bm;
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
    }

    public void update(int mapWidth, int mapHeight, ArrayList<Map> arrMap){
        for (int i = length - 1; i > 0; --i){
            arrSnake.get(i).setX(arrSnake.get(i - 1).getX());
            arrSnake.get(i).setY(arrSnake.get(i - 1).getY());
            arrSnake.get(i).setI(arrSnake.get(i - 1).getI());
            arrSnake.get(i).setJ(arrSnake.get(i - 1).getJ());
        }

//        Map topLeft = arrMap.get(0);
//        if (isMoving_right()){
//            if (arrSnake.get(0).getX() + GameView.sizeOfMap >= topLeft.getX() + x_count * GameView.sizeOfMap){
//                arrSnake.get(0).setX(topLeft.getX());
//            }
//            else{
//                arrSnake.get(0).setX(arrSnake.get(0).getX() + GameView.sizeOfMap);
//            }
//        } else if (isMoving_left()){
//            if (arrSnake.get(0).getX() - GameView.sizeOfMap < topLeft.getX()){
//                arrSnake.get(0).setX(topLeft.getX() + (x_count - 1) * GameView.sizeOfMap);
//            }
//            else{
//                arrSnake.get(0).setX(arrSnake.get(0).getX() - GameView.sizeOfMap);
//            }
//        } else if (isMoving_top()) {
//            if (arrSnake.get(0).getY() - GameView.sizeOfMap < topLeft.getY()){
//                arrSnake.get(0).setY(topLeft.getY() + (y_count - 1) * GameView.sizeOfMap);
//            }
//            else{
//                arrSnake.get(0).setY(arrSnake.get(0).getY() - GameView.sizeOfMap);
//            }
//        } else if (isMoving_bottom()) {
//            if (arrSnake.get(0).getY() + GameView.sizeOfMap > topLeft.getY() + (y_count - 1) * GameView.sizeOfMap){
//                arrSnake.get(0).setY(topLeft.getY());
//            }
//            else{
//                arrSnake.get(0).setY(arrSnake.get(0).getY() + GameView.sizeOfMap);
//            }
//        }
        Map topLeft = arrMap.get(0);

        if (isMoving_right()){
            if (arrSnake.get(0).getJ() + 1 >= mapWidth){
                arrSnake.get(0).setX(topLeft.getX());
                arrSnake.get(0).setJ(0);
            }
            else{
                arrSnake.get(0).setX(arrSnake.get(0).getX() + GameView.sizeOfMap);
                arrSnake.get(0).setJ(arrSnake.get(0).getJ() + 1);
            }
        } else if (isMoving_left()){
            if (arrSnake.get(0).getJ() - 1 < 0){
                arrSnake.get(0).setX(topLeft.getX() + (mapWidth - 1) * GameView.sizeOfMap);
                arrSnake.get(0).setJ(mapWidth - 1);
            }
            else{
                arrSnake.get(0).setX(arrSnake.get(0).getX() - GameView.sizeOfMap);
                arrSnake.get(0).setJ(arrSnake.get(0).getJ() - 1);
            }
        } else if (isMoving_top()) {
            if (arrSnake.get(0).getI() - 1 < 0){
                arrSnake.get(0).setY(topLeft.getY() + (mapHeight - 1) * GameView.sizeOfMap);
                arrSnake.get(0).setI(mapHeight - 1);
            }
            else{
                arrSnake.get(0).setY(arrSnake.get(0).getY() - GameView.sizeOfMap);
                arrSnake.get(0).setI(arrSnake.get(0).getI() - 1);
            }
        } else if (isMoving_bottom()) {
            if (arrSnake.get(0).getI() + 1 >= mapHeight){
                arrSnake.get(0).setY(topLeft.getY());
                arrSnake.get(0).setI(0);
            }
            else{
                arrSnake.get(0).setY(arrSnake.get(0).getY() + GameView.sizeOfMap);
                arrSnake.get(0).setI(arrSnake.get(0).getI() + 1);
            }
        }
    }

    public void draw(Canvas canvas){
        for (int i = 0; i < length; ++i){
            canvas.drawBitmap(getBm(), arrSnake.get(i).getX(), arrSnake.get(i).getY(), null);
        }
    }

    public Bitmap getBm() {
        return bm;
    }

    public void setBm(Bitmap bm) {
        this.bm = bm;
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

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public ArrayList<Map> getArrSnake() {
        return arrSnake;
    }

    public void setArrSnake(ArrayList<Map> arrSnake) {
        this.arrSnake = arrSnake;
    }

    public boolean isMoving_left() {
        return moving_left;
    }

    public void setMoving_left(boolean moving_left) {
        reset_moving();
        this.moving_left = moving_left;
    }

    public boolean isMoving_right() {
        return moving_right;
    }

    public void setMoving_right(boolean moving_right) {
        reset_moving();
        this.moving_right = moving_right;
    }

    public boolean isMoving_top() {
        return moving_top;
    }

    public void setMoving_top(boolean moving_top) {
        reset_moving();
        this.moving_top = moving_top;
    }

    public boolean isMoving_bottom() {
        return moving_bottom;
    }

    public void setMoving_bottom(boolean moving_bottom) {
        reset_moving();
        this.moving_bottom = moving_bottom;
    }

    public void reset_moving(){
        moving_left = false;
        moving_right = false;
        moving_top = false;
        moving_bottom = false;
    }

    public void addPart(){
        Map tail = this.arrSnake.get(length - 1);
        this.length++;
        arrSnake.add(new Map(tail));
    }
}
