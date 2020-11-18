package com.example.cardsgame;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcelable;
import android.util.Log;
import android.view.Surface;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {

    ImageView main_BTN_play;

    ImageView main_IMG_rightPlayer;
    ImageView main_IMG_leftPlayer;

    ImageView main_IMG_leftCard;
    ImageView main_IMG_rightCard;

    TextView main_TXT_leftScore;
    TextView main_TXT_rightScore;

    int leftScore = 0;
    int rightScore = 0;

    int leftCardVal = 0;
    int rightCardVal = 0;

    int cardsDealt = 0;
    int currImg = 0;

    boolean playClicked = false;

    ArrayList<Card> cardStack;

    private Handler handler = new Handler();

    MediaPlayer mp;

    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            handler.postDelayed(runnable, 1000);

            if(cardsDealt < 52) {
                playSound(R.raw.card_dealing);
                clickPlayButton(cardStack);
            } else
                openWinnerActivity(leftScore, rightScore);
        }
    };

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

        setContentView(R.layout.activity_main);

        cardStack = initCardStack();

        main_IMG_leftPlayer = findViewById(R.id.main_IMG_leftPlayer);
        main_IMG_rightPlayer = findViewById(R.id.main_IMG_rightPlayer);

        main_IMG_leftCard = findViewById(R.id.main_IMG_leftCard);
        main_IMG_rightCard = findViewById(R.id.main_IMG_rightCard);

        main_TXT_leftScore = findViewById(R.id.main_TXT_leftScore);
        main_TXT_rightScore = findViewById(R.id.main_TXT_rightScore);

        main_TXT_leftScore.setText("" + leftScore);
        main_TXT_rightScore.setText("" + rightScore);

        setBtnListener(main_IMG_leftPlayer);
        setBtnListener(main_IMG_rightPlayer);

        main_BTN_play = findViewById(R.id.main_BTN_play);

        main_BTN_play.setOnClickListener(v -> {
            if (!playClicked) {
                handler.postDelayed(runnable, 1000);
                playClicked = true;
            }else {
                handler.removeCallbacks(runnable);
                playClicked = false;
            }

        });
    }

    @Override
    protected void onStop() {
        super.onStop();
        handler.removeCallbacks(runnable);
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

    void openWinnerActivity(int leftScore, int rightScore) {
        Intent myIntent = new Intent(this, WinnerActivity.class);
        myIntent.putExtra("leftScore", leftScore);
        myIntent.putExtra("rightScore", rightScore);
        startActivity(myIntent);
        finish();
    }

    // change player img listener
    void setBtnListener(ImageView imgView){
        imgView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!playClicked) {
                    switch (currImg) {
                        case 0:
                            setNewPlayerImg(imgView, R.drawable.ic_bull, 1);
                            break;
                        case 1:
                            setNewPlayerImg(imgView, R.drawable.ic_cat, 1);
                            break;
                        case 2:
                            setNewPlayerImg(imgView, R.drawable.ic_donkey, 1);
                            break;
                        case 3:
                            setNewPlayerImg(imgView, R.drawable.ic_duck, 1);
                            break;
                        case 4:
                            setNewPlayerImg(imgView, R.drawable.ic_jiraffe, 1);
                            break;
                        case 5:
                            setNewPlayerImg(imgView, R.drawable.ic_snake, 1);
                            break;
                        case 6:
                            setNewPlayerImg(imgView, R.drawable.ic_tiger, 1);
                            break;
                        case 7:
                            setNewPlayerImg(imgView, R.drawable.ic_unicorn, 1);
                            break;
                        case 8:
                            setNewPlayerImg(imgView, R.drawable.ic_zebra, 0);
                            break;
                    }
                }
            }
        });
    }

    void setNewPlayerImg(ImageView imgView, int id, int counter){
        imgView.setImageResource(id);
        if(counter == 1)
            currImg++;
        if(counter == 0)
            currImg = 0;
    }

    // called on click of play btn
    // changes card img on screen , changes and increments score
    void clickPlayButton(ArrayList<Card> cardStack){
        String leftCardName = dealCard(cardStack, "left");
        String rightCardName = dealCard(cardStack, "right");

        if(leftCardVal > rightCardVal) {
            main_TXT_leftScore.setText(""+ ++leftScore);
        }
        if(leftCardVal < rightCardVal) {
            main_TXT_rightScore.setText(""+ ++rightScore);
        }

        main_IMG_leftCard.setImageResource(getResources().getIdentifier(leftCardName, "drawable", getPackageName()));
        main_IMG_rightCard.setImageResource(getResources().getIdentifier(rightCardName, "drawable", getPackageName()));
        cardsDealt += 2;
    }

    // gets random card from deck and returns imageName
    String dealCard(ArrayList<Card> cardStack, String leftOrRight) {
        Random rand = new Random();
        Card randCard = cardStack.get(rand.nextInt(cardStack.size()));
        String imageName = randCard.getType() + randCard.getNumber();
        cardStack.remove(randCard);

        if(leftOrRight.equals("left"))
            leftCardVal = randCard.getNumber();
        if(leftOrRight.equals("right"))
            rightCardVal = randCard.getNumber();

        return imageName;
    }

    // initializes the deck
    ArrayList<Card> initCardStack() {
        ArrayList<Card> cardStack = new ArrayList<>();
        for (int i = 1 ; i <= 4 ; i++) {
            String type = typeGenerator(i);
            for (int j = 1 ; j <= 13 ; j++) {
                cardStack.add(new Card(j+1,type));
            }
        }
        return cardStack;
    }

    String typeGenerator(int i){
        switch (i) {
            case 1:
                return "club";
            case 2:
                return "diamond";
            case 3:
                return "heart";
            case 4:
                return "spade";
        }
        return null;
    }

}
