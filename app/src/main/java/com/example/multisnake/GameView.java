package com.example.multisnake;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.Random;

public class GameView extends View {

    public static int sizeOfMap = 75 * Constants.GAMEVIEW_WIDTH / 1000;

    public static int score = 0, coins = 0;

    private boolean play = false;
    private boolean fail_block1 = false;
    private boolean fail_block2 = false;
    private boolean isPlaying1 = false;
    private boolean isPlaying2 = false;


    private Bitmap bmMap1, bmMap2;
    private Bitmap bmMapBlock;
    private Bitmap bmFailBlock;
    private Bitmap bmSnake1, bmSnake2;
    private Bitmap bmPrey;
    private Bitmap bmCoin;

    private ArrayList<Map> arrMap1, arrMap2;
    private ArrayList<Map> arrMapBlocks1, arrMapBlocks2;
    private float[] borderMap1, borderMap2;

    private Snake snake1, snake2;
    private Prey prey1, prey2;
    private Coin coin;

    private ArrayList<Coin> arrCoins;

    private boolean moving = false;
    private boolean wait_moving = false;
    private float mx, my;

    private Handler handler;
    private Runnable runnable;

    private ArrayList<Map> freeMap1Cells;
    private ArrayList<Map> freeMap2Cells;


    public GameView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        Log.d("MyLog", "Start--------------------------------");
        score = 0;
        coins = 0;
        play = false;
        fail_block1 = false;
        fail_block2 = false;
        isPlaying1 = false;
        isPlaying2 = false;
        bmMap1 = BitmapFactory.decodeResource(this.getResources(), R.drawable.map_square);
        bmMap1 = Bitmap.createScaledBitmap(bmMap1, sizeOfMap, sizeOfMap, true);

        bmMap2 = BitmapFactory.decodeResource(this.getResources(), R.drawable.map_square);
        bmMap2 = Bitmap.createScaledBitmap(bmMap2, sizeOfMap, sizeOfMap, true);

        bmMapBlock = BitmapFactory.decodeResource(this.getResources(), R.drawable.map_block);
        bmMapBlock = Bitmap.createScaledBitmap(bmMapBlock, sizeOfMap, sizeOfMap, true);

        bmFailBlock = BitmapFactory.decodeResource(this.getResources(), R.drawable.fail_block);
        bmFailBlock = Bitmap.createScaledBitmap(bmFailBlock, sizeOfMap, sizeOfMap, true);

        arrMapBlocks1 = new ArrayList<>();
        arrMapBlocks2 = new ArrayList<>();

        arrMap1 = createMap(bmMap1, Constants.GAMEMAP1_WIDTH, Constants.GAMEMAP1_HEIGHT, 0, 50, sizeOfMap);
        borderMap1 = createBorder(Constants.GAMEVIEW_WIDTH / 2 - (Constants.GAMEMAP1_WIDTH / 2) * sizeOfMap, 50, Constants.GAMEMAP1_WIDTH, Constants.GAMEMAP1_HEIGHT, sizeOfMap);

        arrMap2 = createMap(bmMap2, Constants.GAMEMAP2_WIDTH, Constants.GAMEMAP2_HEIGHT, 0, 25 + (Constants.GAMEVIEW_HEIGHT / 2), sizeOfMap);
        borderMap2 = createBorder(Constants.GAMEVIEW_WIDTH / 2 - (Constants.GAMEMAP2_WIDTH / 2) * sizeOfMap, 25 + (Constants.GAMEVIEW_HEIGHT / 2), Constants.GAMEMAP2_WIDTH, Constants.GAMEMAP2_HEIGHT, sizeOfMap);

        bmSnake1 = BitmapFactory.decodeResource(this.getResources(), Constants.cur_snake);
        bmSnake1 = Bitmap.createScaledBitmap(bmSnake1, sizeOfMap, sizeOfMap, true);
        snake1 = new Snake(bmSnake1, arrMap1.get(3).getX(), arrMap1.get(3).getY(), 4);

        bmSnake2 = BitmapFactory.decodeResource(this.getResources(), Constants.cur_snake);
        bmSnake2 = Bitmap.createScaledBitmap(bmSnake2, sizeOfMap, sizeOfMap, true);
        snake2 = new Snake(bmSnake2, arrMap2.get(3).getX(), arrMap2.get(3).getY(), 4);

        bmCoin = BitmapFactory.decodeResource(this.getResources(), R.drawable.snake_coin);
        bmCoin = Bitmap.createScaledBitmap(bmCoin, sizeOfMap, sizeOfMap, true);

//        bmCoin2 = BitmapFactory.decodeResource(this.getResources(), R.drawable.snake_coin);
//        bmCoin2 = Bitmap.createScaledBitmap(bmCoin2, sizeOfMap, sizeOfMap, true);

        coin = new Coin(0, 0, -1, -1);
        arrCoins = new ArrayList<>();

        bmPrey = BitmapFactory.decodeResource(this.getResources(), R.drawable.gold_square);
        bmPrey = Bitmap.createScaledBitmap(bmPrey, sizeOfMap, sizeOfMap, true);

//        bmPrey2 = BitmapFactory.decodeResource(this.getResources(), R.drawable.gold_square);
//        bmPrey2 = Bitmap.createScaledBitmap(bmPrey2, sizeOfMap, sizeOfMap, true);

        prey1 = new Prey(0, 0, -1, -1);
        prey2 = new Prey(0, 0, -1, -1);

        freeMap1Cells = createFreeMapCells(arrMap1);
        freeMap2Cells = createFreeMapCells(arrMap2);

//        printArray(freeMap1Cells);
//        printArray(freeMap2Cells);
//        printArray(snake1.getArrSnake());

        Map randMapPos = getRandomPosMap(freeMap1Cells);
        prey1.setX(randMapPos.getX());
        prey1.setY(randMapPos.getY());
        prey1.setI(randMapPos.getI());
        prey1.setJ(randMapPos.getJ());

        randMapPos = getRandomPosMap(freeMap2Cells);
        prey2.setX(randMapPos.getX());
        prey2.setY(randMapPos.getY());
        prey2.setI(randMapPos.getI());
        prey2.setJ(randMapPos.getJ());



        arrMapBlocks1 = createBlockCells(Constants.GAMEMAP1_WIDTH, Constants.GAMEMAP1_HEIGHT, freeMap1Cells);
        arrMapBlocks2 = createBlockCells(Constants.GAMEMAP2_WIDTH, Constants.GAMEMAP2_HEIGHT, freeMap2Cells);


//        freeMap1Cells = createFreeMapCells(arrMap1);
//        freeMap2Cells = createFreeMapCells(arrMap2);

        handler = new Handler();
        runnable = new Runnable() {
            @Override
            public void run() {
                invalidate();
            }
        };

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getActionMasked();
        switch (action){
            case MotionEvent.ACTION_MOVE:
            {
                if (!moving){
                    Log.d("MyLog", "moving");
                    mx = event.getX();
                    my = event.getY();
                    moving = true;
                    if (!play){
                        play = true;
                        isPlaying1 = true;
                        isPlaying2 = true;
//                        fail_block1 = false;
//                        fail_block2 = false;
                    }
                }
                else{
                    if (mx - event.getX() > 100  && !snake1.isMoving_right()){
                        mx = event.getX();
                        my = event.getY();
                        snake1.setMoving_left(true);
                        snake2.setMoving_left(true);
                    } else if (event.getX() - mx > 100 && !snake1.isMoving_left()){
                        mx = event.getX();
                        my = event.getY();
                        snake1.setMoving_right(true);
                        snake2.setMoving_right(true);
                    } else if (my - event.getY() > 100 && !snake1.isMoving_bottom()){
                        mx = event.getX();
                        my = event.getY();
                        snake1.setMoving_top(true);
                        snake2.setMoving_top(true);
                    } else if (event.getY() - my > 100 && !snake1.isMoving_top()){
                        mx = event.getX();
                        my = event.getY();
                        snake1.setMoving_bottom(true);
                        snake2.setMoving_bottom(true);
                    }
                }
                break;
            }

            case MotionEvent.ACTION_UP:{
                wait_moving = true;
                mx = 0;
                my = 0;
                moving = false;
                break;
            }

        }
        return true;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        Paint paint = new Paint();
        paint.setStrokeWidth(15);

        Log.d("MyLog", "StartDrawing");

        drawMap(canvas, arrMap1);
//        drawFreeMapCells(canvas, freeMap1Cells);
        drawMapBlocks(canvas, arrMapBlocks1);
        prey1.draw(canvas, bmPrey);

        drawMap(canvas, arrMap2);
//        drawFreeMapCells(canvas, freeMap2Cells);
        drawMapBlocks(canvas, arrMapBlocks2);
        prey2.draw(canvas, bmPrey);

        drawCoins(canvas, arrCoins);


        if (isPlaying1) {
            snake1.update(Constants.GAMEMAP1_WIDTH, Constants.GAMEMAP1_HEIGHT, arrMap1);

            if (checkIntersectionSnake(snake1) || checkIntersectionMapBlock(snake1, arrMapBlocks1)){
                isPlaying1 = false;
                wait_moving = false;
                fail_block1 = true;
            }
        }
        snake1.draw(canvas);


        if (checkIntersectionPrey(snake1, prey1)){
            freeMap1Cells = createFreeMapCells(arrMap1);
            if (!updatePrey(canvas, snake1, prey1, freeMap1Cells, arrMap1)){
                if (isPlaying2){
                    isPlaying1 = false;
                }
            }
            if (score % 5 == 0){
                updateCoin(canvas, freeMap1Cells, arrMap1);
            }
        }
        if (checkIntersectionCoin(snake1, arrCoins)){
            coins += 10;
            GameActivity.tv_coins.setText(" x " + coins);
        }




        if (isPlaying2){
            snake2.update(Constants.GAMEMAP2_WIDTH, Constants.GAMEMAP2_HEIGHT, arrMap2);

            if (checkIntersectionSnake(snake2) || checkIntersectionMapBlock(snake2, arrMapBlocks2)){
                isPlaying2 = false;
                wait_moving = false;
                fail_block2 = true;
            }
        }
        snake2.draw(canvas);



        if (checkIntersectionPrey(snake2, prey2)){
            freeMap2Cells = createFreeMapCells(arrMap2);
            if (!updatePrey(canvas, snake2, prey2, freeMap2Cells, arrMap2)){
                if (isPlaying1){
                    isPlaying2 = false;
                }
            }
            if (score % 5 == 0){
                updateCoin(canvas, freeMap2Cells, arrMap2);
            }
        }
        if (checkIntersectionCoin(snake2, arrCoins)){
            coins += 10;
            GameActivity.tv_coins.setText(" x " + coins);
        }


        if (fail_block1){
            drawFailBlock(canvas, snake1);
        }
        if (fail_block2){
            drawFailBlock(canvas, snake2);
        }


        drawBorder(canvas, borderMap1, paint);
        drawBorder(canvas, borderMap2, paint);


        handler.postDelayed(runnable, Constants.SNAKE_SPEED);

        if (wait_moving && (fail_block1 || fail_block2)){
            gameOver();
            handler.removeCallbacks(runnable);
            Log.d("MyLog", "GameOver");
        }
    }

    private void drawMap(Canvas canvas, ArrayList<Map> arrMap){
        Bitmap bm;
        if (arrMap == arrMap1){
            bm = bmMap1;
        }
        else{
            bm = bmMap2;
        }
        for (int i = 0; i < arrMap.size(); ++i){
            canvas.drawBitmap(bm, arrMap.get(i).getX(), arrMap.get(i).getY(), null);
        }
    }

    private static float[] convertFloat(ArrayList<Float> floatArray)
    {
        float[] res = new float[floatArray.size()];
        for (int i = 0; i < res.length; ++i)
        {
            res[i] = floatArray.get(i);
        }
        return res;
    }

    private void drawBorder(Canvas canvas, float[] border, Paint paint){
        canvas.drawLines(border, paint);
    }

    private void drawFailBlock(Canvas canvas, Snake snake){
        isPlaying1 = false;
        isPlaying2 = false;
        canvas.drawBitmap(bmFailBlock, snake.getArrSnake().get(0).getX(), snake.getArrSnake().get(0).getY(), null);
    }

    private ArrayList<Map> createMap(Bitmap bmMap, int mapWidth, int mapHeight, int offset_x, int offset_y, int sizeOfMap){
        ArrayList<Map> arrMap = new ArrayList<>();
        for (int i = 0; i < mapHeight; ++i){
            for (int j = 0; j < mapWidth; ++j){
                arrMap.add(new Map(j * sizeOfMap + offset_x + Constants.GAMEVIEW_WIDTH / 2 - (mapWidth / 2) * sizeOfMap,
                        i * sizeOfMap + offset_y, sizeOfMap, sizeOfMap, i, j));
            }
        }

        return arrMap;
    }

    private void drawCoins(Canvas canvas, ArrayList<Coin> arrCoins){
        Bitmap bm;
        for (int i = 0; i < arrCoins.size(); ++i){
            arrCoins.get(i).draw(canvas, bmCoin);
        }
    }

    private void drawMapBlocks(Canvas canvas, ArrayList<Map> arrMapBlocks){
        for (int i = 0; i < arrMapBlocks.size(); ++i){
            canvas.drawBitmap(bmMapBlock, arrMapBlocks.get(i).getX(), arrMapBlocks.get(i).getY(), null);
        }
    }

    private void drawFreeMapCells(Canvas canvas, ArrayList<Map> freeMapCells){
        Bitmap bm = BitmapFactory.decodeResource(this.getResources(), R.drawable.snake_teal);
        bm = Bitmap.createScaledBitmap(bm, sizeOfMap, sizeOfMap, true);
        for (int i = 0; i < freeMapCells.size(); ++i){
            canvas.drawBitmap(bm, freeMapCells.get(i).getX(), freeMapCells.get(i).getY(), null);
        }
    }

    private float[] createBorder(int x, int y, int x_count, int y_count, int sizeOfMap){
        return new float[] {
                x, y,
                x + x_count * sizeOfMap, y,
                x + x_count * sizeOfMap, y,
                x + x_count * sizeOfMap, y + y_count * sizeOfMap,
                x + x_count * sizeOfMap, y + y_count * sizeOfMap,
                x, y + y_count * sizeOfMap,
                x, y + y_count * sizeOfMap,
                x, y
        };
    }


    private ArrayList<Map> createFreeMapCells(ArrayList<Map> arrMap){
        ArrayList<Map> freeMapCells = new ArrayList<>();
        Snake snake;
        Prey prey;
        if (arrMap == arrMap1){
//            arrMapBlocks = arrMapBlocks1;
            snake = snake1;
            prey = prey1;
        }else{
//            arrMapBlocks = arrMapBlocks2;
            snake = snake2;
            prey = prey2;
        }
        Map m;
        boolean contains = false;
        for (int i = 0; i < arrMap.size(); ++i){
            m = arrMap.get(i);
            contains = false;
            for (int j = 0; j < snake.getArrSnake().size(); ++j){
                if (snake.getArrSnake().get(j).getI() == m.getI() &&
                        snake.getArrSnake().get(j).getJ() == m.getJ()){
                    contains = true;
                    break;
                }
            }

            if (!contains && isPlaying1){
                for (int j = 0; j < arrMapBlocks1.size(); ++j){
                    if (arrMapBlocks1.get(j).getI() == m.getI() &&
                            arrMapBlocks1.get(j).getJ() == m.getJ()){
                        contains = true;
                        break;
                    }
                }
            }

            if (!contains && isPlaying2){
                for (int j = 0; j < arrMapBlocks2.size(); ++j){
                    if (arrMapBlocks2.get(j).getI() == m.getI() &&
                            arrMapBlocks2.get(j).getJ() == m.getJ()){
                        contains = true;
                        break;
                    }
                }
            }

            if (!contains){
                for (int j = 0; j < arrCoins.size(); ++j){
                    if (arrCoins.get(j).getI() == m.getI() &&
                            arrCoins.get(j).getJ() == m.getJ()){
                        contains = true;
                        break;
                    }
                }
            }

            if (!contains)
            {
                if (!(prey.getI() == m.getI() &&
                        prey.getJ() == m.getJ())){
                    freeMapCells.add(m);
                }
            }
        }

        return freeMapCells;
    }

    private ArrayList<Map> createBlockCells(int mapWidth, int mapHeight, ArrayList<Map> freeMapCells){
        ArrayList<Map> arrMapBlocks = new ArrayList<>();
        int percentage = (int) (mapWidth * mapHeight * 0.05);
        if (percentage == 0){
            percentage = 1;
        }
        for (int i = 0; i < percentage; ++i){
            Map m = getRandomPosMap(freeMapCells);
            Map block = new Map(m.getX(), m.getY(), sizeOfMap, sizeOfMap, m.getI(), m.getJ());
            arrMapBlocks.add(block);
        }

        return arrMapBlocks;
    }

    // Возвращает соответствующую позицию Map m в массиве "another"
    // bigArray >= smallArray
    private Map getAnotherMapPos(Map m, ArrayList<Map> bigArray, int bigWidth, int bigHeight,
                                 ArrayList<Map> smallArray, int smallWidth, int smallHeight){
        if (bigWidth < smallWidth ||
            bigHeight < smallHeight){
            return null;
        }

        Map posMap = bigArray.get(0);
        boolean found = false;
        for (int i = 0; i < bigHeight; ++i){
            for (int j = 0; j < smallWidth; ++j){
                   if (bigArray.get(i * bigWidth + j).getX() == m.getX() &&
                       bigArray.get(i * bigWidth + j).getY() == m.getY()){
                       posMap.setX(smallArray.get(0).getX() + m.getX());
                       posMap.setY(smallArray.get(0).getY() + m.getY());
                       found = true;
                       break;
                }
            }
            if (found){
                break;
            }
        }

        if (!found){
            return null;
        }

        return  posMap;
    }
    private Map getRandomPosMap(ArrayList<Map> arrMap){
        Random r = new Random();
        int index = r.nextInt(arrMap.size());
        Map m = arrMap.get(index);
        removeMap(m, freeMap1Cells);
        removeMap(m, freeMap2Cells);

        return m;
    }

    private void removeMap(Map m, ArrayList<Map> arrMap){
        for (int i = 0; i < arrMap.size(); ++i){
            if (arrMap.get(i).getI() == m.getI() &&
                arrMap.get(i).getJ() == m.getJ()){
                arrMap.remove(i);
                break;
            }
        }
    }

    private boolean checkIntersectionPrey(Snake snake, Prey prey){
        return snake.getArrSnake().get(0).getI() == prey.getI() &&
                snake.getArrSnake().get(0).getJ() == prey.getJ();
    }


    private boolean checkIntersectionCoin(Snake snake, ArrayList<Coin> arrCoins){
        boolean intersected = false;
        for (int i = 0; i < arrCoins.size(); ++i){
            if (snake.getArrSnake().get(0).getI() == arrCoins.get(i).getI() &&
                snake.getArrSnake().get(0).getJ() == arrCoins.get(i).getJ()){
                arrCoins.remove(i);
                intersected = true;
                break;
            }
        }
        return intersected;
    }

    private boolean checkIntersectionSnake(Snake snake){
        boolean intersected = false;
        for (int i = 1; i < snake.getArrSnake().size(); ++i){
            if (snake.getArrSnake().get(0).getI() == snake.getArrSnake().get(i).getI() &&
                snake.getArrSnake().get(0).getJ() == snake.getArrSnake().get(i).getJ()){
                intersected = true;
                if (snake == snake1)
                    Log.d("MyLog", "Intersected snake1");
                else
                    Log.d("MyLog", "Intersected snake2");
                break;
            }
        }
        return intersected;
    }

    private boolean checkIntersectionMapBlock(Snake snake, ArrayList<Map> arrMapBlocks){
        boolean intersected = false;
        for (int i = 0; i < arrMapBlocks.size(); ++i){
            if (snake.getArrSnake().get(0).getI() == arrMapBlocks.get(i).getI() &&
                    snake.getArrSnake().get(0).getJ() == arrMapBlocks.get(i).getJ()){
                intersected = true;
                if (snake == snake1)
                    Log.d("MyLog", "Intersected block1");
                else
                    Log.d("MyLog", "Intersected block2");
                break;
            }
        }
        return intersected;
    }

    private void gameOver(){
//        isPlaying1 = false;
//        isPlaying2 = false;

        Constants.total_coins += coins;
        saveSharedPreferences();
        GameActivity.showDialogScore();
    }


    @SuppressLint("SetTextI18n")
    private boolean updatePrey(Canvas canvas, Snake snake, Prey prey, ArrayList<Map> freeMapCells, ArrayList<Map> arrMap){
        snake.addPart();
        if (!freeMapCells.isEmpty()){
            Map randMapPos = getRandomPosMap(freeMapCells);
            prey.setX(randMapPos.getX());
            prey.setY(randMapPos.getY());
            prey.setI(randMapPos.getI());
            prey.setJ(randMapPos.getJ());
            prey.draw(canvas, bmPrey);
            score++;
            GameActivity.tv_score.setText(" x " + score);

            return true;
        }
        return false;
    }

    @SuppressLint("SetTextI18n")
    private boolean updateCoin(Canvas canvas, ArrayList<Map> freeMapCells, ArrayList<Map> arrMap) {
        Random rand = new Random();
        int chance = rand.nextInt(100);
        if (chance < 30){
            Log.d("MyLog", "newCoin");
            if (arrMap == arrMap1){
                freeMap1Cells = createFreeMapCells(arrMap);
            }else{
                freeMap2Cells = createFreeMapCells(arrMap);
            }

            if (!freeMapCells.isEmpty()){
                Map randMapPos = getRandomPosMap(freeMapCells);
                Coin c = new Coin(coin);
                c.setX(randMapPos.getX());
                c.setY(randMapPos.getY());
                c.setI(randMapPos.getI());
                c.setJ(randMapPos.getJ());
                c.draw(canvas, bmCoin);
                arrCoins.add(c);

                return true;
            }
        }

        return false;
    }


    public void saveSharedPreferences(){
        SharedPreferences sPref = getContext().getSharedPreferences("game_setting", MainActivity.MODE_PRIVATE);
        SharedPreferences.Editor editor = sPref.edit();
        editor.putInt("cur_snake", Constants.cur_snake);
        editor.putInt("total_coins", Constants.total_coins);
        editor.putBoolean("blue_open", Constants.blue_open);
        editor.putBoolean("green_open", Constants.green_open);
        editor.putBoolean("teal_open", Constants.teal_open);
        editor.apply();
    }

    public void printArray(ArrayList<Map> arrMap){
        Log.d("MyLog", "Array:");
        for (int i = 0; i < arrMap.size(); ++i){
            Log.d("MyLog", "------------------");
            Log.d("MyLog", "I: " + arrMap.get(i).getI());
            Log.d("MyLog", "J: " + arrMap.get(i).getJ());
        }
    }
}


