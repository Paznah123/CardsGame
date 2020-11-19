package com.example.cardsgame;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.TypedArray;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    ImageView main_BTN_play;

    PlayerView leftPlayer;
    PlayerView rightPlayer;

    //========================================================

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

        leftPlayer = new PlayerView(this, R.id.main_IMG_leftPlayer, R.id.main_IMG_leftCard, R.id.main_LBL_leftScore);
        rightPlayer = new PlayerView(this, R.id.main_IMG_rightPlayer, R.id.main_IMG_rightCard,  R.id.main_LBL_rightScore);

        main_BTN_play = findViewById(R.id.main_BTN_play);

        main_BTN_play.setOnClickListener(v -> {
            if (!leftPlayer.isGameRunning()) {
                handler.postDelayed(runnable, 1000);
                lockPlayersImgListener(true);
            } else {
                handler.removeCallbacks(runnable);
                lockPlayersImgListener(false);
            }
        });
    }

    // ================================================================

    void lockPlayersImgListener(boolean key){
        leftPlayer.setGameRunning(key);
        rightPlayer.setGameRunning(key);
    }

    ArrayList<Card> cardStack;
    ArrayList<Card> initCardStack() {  // initializes the deck
        ArrayList<Card> cardStack = new ArrayList<>();
        for (int i =  1 ; i <= 4 ; i++) {
            int typeId = getResources().obtainTypedArray(R.array.names).getResourceId(i, 0);
            String type = getResources().getResourceEntryName(typeId);
            for (int j = 2 ; j <= 14 ; j++) {
                cardStack.add(new Card(j,type));
            }
        }
        return cardStack;
    }

    //======================================================

    private Handler handler = new Handler();
    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            handler.postDelayed(runnable, 1000);
            if(cardsDealt < 52) {
                playSound(R.raw.card_dealing);
                updateCardsAndScoreView();
            } else {
                openWinnerActivity();
                handler.removeCallbacks(runnable);
                playSound(R.raw.victory_sound);
            }
        }
    };

    MediaPlayer mp;
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

    // ================================================================

    // gets random card from deck and changes card view
    Random rand = new Random();
    int drawNewCard(PlayerView player) {
        Card randCard = cardStack.get(rand.nextInt(cardStack.size()));
        cardStack.remove(randCard);

        // sets new card in image
        String imageName = randCard.getType() + randCard.getNumber();
        player.getPlayerCardView().setImageResource(getResources().getIdentifier(imageName, "drawable", getPackageName()));

        int cardValue = randCard.getNumber();
        player.setCardValue(cardValue);

        return cardValue;
    }

    int cardsDealt = 0;
    void updateCardsAndScoreView(){
        int leftCardVal = drawNewCard(leftPlayer);
        int rightCardVal = drawNewCard(rightPlayer);

        if (leftCardVal > rightCardVal) {
            leftPlayer.getPlayerScoreView().setText(""+ leftPlayer.increaseReturnGameScore());
        }
        if (leftCardVal < rightCardVal) {
            rightPlayer.getPlayerScoreView().setText(""+ rightPlayer.increaseReturnGameScore());
        }

        cardsDealt += 2;
    }

    // ================================================================

    void openWinnerActivity() {
        Intent myIntent = new Intent(this, WinnerActivity.class);
        myIntent.putExtra("leftScore", leftPlayer.getGameScore());
        myIntent.putExtra("rightScore", rightPlayer.getGameScore());
        startActivity(myIntent);
        finish();
    }

    //======================================================

}