package com.example.cardsgame;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class WinnerActivity extends AppCompatActivity {


    TextView winner_TXT_leftScore;
    TextView winner_TXT_rightScore;
    TextView winnerMsg;

    int leftScore = 0;
    int rightScore = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_IMMERSIVE       // Set the content to appear under the system bars so that the
                | View.SYSTEM_UI_FLAG_LAYOUT_STABLE // content doesn't resize when the system bars hide and show.
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION // Hide the nav bar and status bar
                | View.SYSTEM_UI_FLAG_FULLSCREEN
        );

        setContentView(R.layout.activity_winner2);

        Intent intent = getIntent();

        int leftScore = intent.getIntExtra("leftScore",0);
        int rightScore = intent.getIntExtra("rightScore",0);


        winner_TXT_rightScore = findViewById(R.id.winner_TXT_rightFinalScore);
        winner_TXT_leftScore = findViewById(R.id.winner_TXT_leftFinalScore);

        winner_TXT_rightScore.setText("Right: "+ rightScore);
        winner_TXT_leftScore.setText("Left: "+ leftScore);

        winnerMsg = findViewById(R.id.winner_TXT_theWinnerIs);
        winnerMsg.setText(getGameWinner(leftScore, rightScore));
    }

    String getGameWinner(int leftScore, int rightScore){
        String winnerMsg;

        if(leftScore > rightScore)
            winnerMsg = "Left Player won!";
        else if(leftScore < rightScore)
            winnerMsg = "Right Player won!";
        else {
            winnerMsg = "It's A Tie!";
        }
        return winnerMsg;
    }
}