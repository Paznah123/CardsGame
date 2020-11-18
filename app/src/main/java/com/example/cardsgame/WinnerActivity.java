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


    TextView winner_TXT_leftScore;
    TextView winner_TXT_rightScore;
    TextView winnerMsg;

    Button restart;

    MediaPlayer mp;

    int leftScore = 0;
    int rightScore = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

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

        playSound(R.raw.victory_sound);

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

    private void playSound(int rawSound) {
        mp = MediaPlayer.create(this,rawSound);
        mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                mp.reset();
                mp.release();
                mp = null;
            }
        });
        mp.start();
    }
}