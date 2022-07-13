package com.example.multisnake;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private Button btn_play, btn_shop;
    private SharedPreferences sPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        sPref = this.getSharedPreferences("game_setting", MainActivity.MODE_PRIVATE);
        if(sPref != null){
            Constants.cur_snake = sPref.getInt("cur_snake", R.drawable.snake_red);
            Constants.total_coins = sPref.getInt("total_coins", 0);
            Constants.blue_open = sPref.getBoolean("blue_open", false);
            Constants.green_open = sPref.getBoolean("green_open", false);
            Constants.teal_open = sPref.getBoolean("teal_open", false);
        }else{
            Constants.cur_snake = R.drawable.snake_red;
            Constants.total_coins = 0;
            Constants.blue_open = false;
            Constants.green_open = false;
            Constants.teal_open = false;

            SharedPreferences.Editor editor = sPref.edit();
            editor.putInt("cur_snake", Constants.cur_snake);
            editor.putInt("total_coins", Constants.total_coins);
            editor.putBoolean("blue_open", Constants.blue_open);
            editor.putBoolean("green_open", Constants.green_open);
            editor.putBoolean("teal_open", Constants.teal_open);
            editor.apply();
        }

//        int id = this.getResources().getIdentifier("<String id>","id", getPackageName());
//        Constants.cur_snake = R.drawable.snake_teal;
        btn_play = findViewById(R.id.btn_play);
        btn_shop = findViewById(R.id.btn_shop);

        btn_play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, GameActivity.class);
                startActivity(intent);
            }
        });

        btn_shop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ShopActivity.class);
                startActivity(intent);
            }
        });
    }

    public void saveSharedPreferences(){
        SharedPreferences.Editor editor = sPref.edit();
        editor.putInt("cur_snake", Constants.cur_snake);
        editor.putInt("total_coins", Constants.total_coins);
        editor.putBoolean("blue_open", Constants.blue_open);
        editor.putBoolean("green_open", Constants.green_open);
        editor.putBoolean("teal_open", Constants.teal_open);
        editor.apply();
    }
}