package com.example.cardsgame;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class WinnerActivity extends AppCompatActivity {

    TextView winner_LBL_score;
    TextView winnerMsg;

    int leftScore = 0;
    int rightScore = 0;

    Button restart;

    //=====================================================

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_winner2);

        Intent intent = getIntent();

        leftScore = intent.getIntExtra("leftScore", 0);
        rightScore = intent.getIntExtra("rightScore", 0);

        winner_LBL_score = findViewById(R.id.winner_LBL_finalScore);
        winner_LBL_score.setText("Left: " + leftScore + " | " + "Right: " + rightScore);

        winnerMsg = findViewById(R.id.winner_LBL_theWinnerIs);
        winnerMsg.setText(getGameWinner(leftScore, rightScore));

        restart = findViewById(R.id.winner_BTN_restart);
        restart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(WinnerActivity.this,MainActivity.class);
                startActivity(myIntent);
                finish();
            }
        });
    }

    //===================================================

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