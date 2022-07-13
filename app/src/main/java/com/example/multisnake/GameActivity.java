package com.example.multisnake;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;
import android.app.Dialog;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;


public class GameActivity extends AppCompatActivity {
    public static ImageView img_swipe;
    public static Dialog dialogScore;
    private static GameView gv;
    public static TextView tv_score, tv_coins, tv_dialog_score, tv_dialog_coins;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        DisplayMetrics dw = new DisplayMetrics();
        this.getWindowManager().getDefaultDisplay().getMetrics(dw);
        Constants.GAMEVIEW_WIDTH = dw.widthPixels;
        Constants.GAMEVIEW_HEIGHT = dw.heightPixels - dpToPx(75);

        setContentView(R.layout.activity_game);
        gv = findViewById(R.id.gv);
        tv_score = findViewById(R.id.tv_score);
        tv_coins = findViewById(R.id.tv_coins);
        dialogScore = new Dialog(this);
    }

    private int dpToPx(int dp){
        return (int) (dp * Resources.getSystem().getDisplayMetrics().density);
    }

    @SuppressLint("SetTextI18n")
    public static void showDialogScore() {

        dialogScore.setContentView(R.layout.activity_dialog);
        tv_dialog_score = dialogScore.findViewById(R.id.tv_dialog_score);
        tv_dialog_score.setText(GameView.score + "");
        tv_dialog_coins = dialogScore.findViewById(R.id.tv_dialog_coins);
        tv_dialog_coins.setText(GameView.coins + "");
        dialogScore.setCanceledOnTouchOutside(false);



        RelativeLayout rl_start = dialogScore.findViewById(R.id.rl_replay);
        rl_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(dialogScore.getContext(), GameActivity.class);
                dialogScore.getContext().startActivity(intent);
            }
        });

        RelativeLayout rl_menu = dialogScore.findViewById(R.id.rl_menu);
        rl_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(dialogScore.getContext(), MainActivity.class);
                dialogScore.getContext().startActivity(intent);
            }
        });

        dialogScore.show();
    }

}