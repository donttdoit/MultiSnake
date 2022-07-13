package com.example.multisnake;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

public class ShopActivity extends AppCompatActivity {

    ImageView iv_back;
    TextView tv_total_coins;
    RadioButton btn_snake_red, btn_snake_blue, btn_snake_green, btn_snake_teal;
    LinearLayout ll_blue, ll_green, ll_teal;

    AlertDialog ad;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        iv_back = findViewById(R.id.iv_back);
        iv_back.setOnClickListener(this::onClick);

        tv_total_coins = findViewById(R.id.tv_total_coins);

        btn_snake_red = findViewById(R.id.snake_red);
        btn_snake_blue = findViewById(R.id.snake_blue);
        btn_snake_green = findViewById(R.id.snake_green);
        btn_snake_teal = findViewById(R.id.snake_teal);

        btn_snake_red.setOnClickListener(this::onClick);
        btn_snake_blue.setOnClickListener(this::onClick);
        btn_snake_green.setOnClickListener(this::onClick);
        btn_snake_teal.setOnClickListener(this::onClick);

        ll_blue = findViewById(R.id.ll_blue);
        ll_green = findViewById(R.id.ll_green);
        ll_teal = findViewById(R.id.ll_teal);

//        saveStartSharedPreferences();
//        Constants.total_coins = 210;
        initShop();
    }

    public void onClick(View v){
        switch (v.getId()){
            case R.id.iv_back:{
                Intent intent = new Intent(ShopActivity.this, MainActivity.class);
                startActivity(intent);
                break;
            }
            case R.id.snake_red:{
                Constants.cur_snake = R.drawable.snake_red;
                saveSharedPreferences();
                break;
            }
            case R.id.snake_blue:{
                if (Constants.blue_open){
                    Constants.cur_snake = R.drawable.snake_blue;
                } else{
                    // Alert Dialog
                    AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            switch(i){
                                case DialogInterface.BUTTON_POSITIVE:{
                                    if (Constants.total_coins >= 200){
                                        Constants.total_coins -= 200;
                                        Constants.blue_open = true;
                                        Constants.cur_snake = R.drawable.snake_blue;
                                        saveSharedPreferences();
                                    } else{
                                        Toast.makeText(getApplicationContext(), "Недостаточно монет", Toast.LENGTH_SHORT).show();
                                    }
                                    initShop();
                                    ad.dismiss();
                                    break;
                                }
                                case DialogInterface.BUTTON_NEGATIVE:{
                                    initShop();
                                    ad.dismiss();
                                    break;
                                }
                            }
                        }
                    };
                    builder.setMessage("Купить за 200?").setPositiveButton("Да", dialogClickListener).
                            setNegativeButton("Нет", dialogClickListener);
                    builder.setCancelable(false);
                    ad = builder.show();
                }
                break;
            }
            case R.id.snake_green:{
                if (Constants.green_open){
                    Constants.cur_snake = R.drawable.snake_green;
                    saveSharedPreferences();
                }else{
                    // Alert Dialog
                    AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            switch(i){
                                case DialogInterface.BUTTON_POSITIVE:{
                                    if (Constants.total_coins >= 200){
                                        Constants.total_coins -= 200;
                                        Constants.green_open = true;
                                        Constants.cur_snake = R.drawable.snake_green;
                                        saveSharedPreferences();
                                    } else{
                                        Toast.makeText(getApplicationContext(), "Недостаточно монет", Toast.LENGTH_SHORT).show();
                                    }
                                    initShop();
                                    ad.dismiss();
                                    break;
                                }
                                case DialogInterface.BUTTON_NEGATIVE:{
                                    initShop();
                                    ad.dismiss();
                                    break;
                                }
                            }
                        }
                    };
                    builder.setMessage("Купить за 200?").setPositiveButton("Да", dialogClickListener).
                            setNegativeButton("Нет", dialogClickListener);
                    builder.setCancelable(false);
                    ad = builder.show();
                }
                break;
            }
            case R.id.snake_teal:{
                if (Constants.teal_open){
                    Constants.cur_snake = R.drawable.snake_teal;
                    saveSharedPreferences();
                }else{
                    // Alert Dialog
                    AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            switch(i){
                                case DialogInterface.BUTTON_POSITIVE:{
                                    if (Constants.total_coins >= 200){
                                        Constants.total_coins -= 200;
                                        Constants.teal_open = true;
                                        Constants.cur_snake = R.drawable.snake_teal;
                                        saveSharedPreferences();
                                    } else{
                                        Toast.makeText(getApplicationContext(), "Недостаточно монет", Toast.LENGTH_SHORT).show();
                                    }
                                    initShop();
                                    ad.dismiss();
                                    break;
                                }
                                case DialogInterface.BUTTON_NEGATIVE:{
                                    initShop();
                                    ad.dismiss();
                                    break;
                                }
                            }
                        }
                    };
                    builder.setMessage("Купить за 200?").setPositiveButton("Да", dialogClickListener).
                            setNegativeButton("Нет", dialogClickListener);
                    builder.setCancelable(false);
                    ad = builder.show();
                }
                break;
            }
        }
    }

    private void saveSharedPreferences() {
        SharedPreferences sPref = this.getSharedPreferences("game_setting", MainActivity.MODE_PRIVATE);
        SharedPreferences.Editor editor = sPref.edit();
        editor.putInt("cur_snake", Constants.cur_snake);
        editor.putInt("total_coins", Constants.total_coins);
        editor.putBoolean("blue_open", Constants.blue_open);
        editor.putBoolean("green_open", Constants.green_open);
        editor.putBoolean("teal_open", Constants.teal_open);
        editor.apply();
    }

    @SuppressLint("SetTextI18n")
    private void initShop(){
        if (Constants.blue_open){
            ll_blue.setVisibility(View.GONE);
        }
        if (Constants.green_open){
            ll_green.setVisibility(View.GONE);
        }
        if (Constants.teal_open){
            ll_teal.setVisibility(View.GONE);
        }

        if (Constants.cur_snake == R.drawable.snake_blue){
            btn_snake_blue.setChecked(true);
        } else if (Constants.cur_snake == R.drawable.snake_green){
            btn_snake_green.setChecked(true);
        } else if (Constants.cur_snake == R.drawable.snake_teal){
            btn_snake_teal.setChecked(true);
        } else{
            btn_snake_red.setChecked(true);
        }

        tv_total_coins.setText(Constants.total_coins + "");
    }

    private void saveStartSharedPreferences(){
        Constants.cur_snake = R.drawable.snake_red;
        Constants.total_coins = 0;
        Constants.blue_open = false;
        Constants.green_open = false;
        Constants.teal_open = false;

        SharedPreferences sPref = this.getSharedPreferences("game_setting", MainActivity.MODE_PRIVATE);
        SharedPreferences.Editor editor = sPref.edit();
        editor.putInt("cur_snake", Constants.cur_snake);
        editor.putInt("total_coins", Constants.total_coins);
        editor.putBoolean("blue_open", Constants.blue_open);
        editor.putBoolean("green_open", Constants.green_open);
        editor.putBoolean("teal_open", Constants.teal_open);
        editor.apply();
    }
}