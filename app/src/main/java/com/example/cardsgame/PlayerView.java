package com.example.cardsgame;

import android.app.Activity;
import android.content.res.TypedArray;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class PlayerView {

    private ImageView playerImg;
    private ImageView cardImg;
    private TextView playerScore;

    private int cardValue;
    private int gameScore;

    private int playerImgArrIndex = 0;
    private boolean gameRunning = false;

    //===========================================

    public PlayerView(Activity activity, int player_IMG_ID, int card_IMG_ID, int score_LBL_ID) {
        this.playerImg = activity.findViewById(player_IMG_ID);
        this.cardImg = activity.findViewById(card_IMG_ID);
        this.playerScore = activity.findViewById(score_LBL_ID);

        this.gameScore = 0;
        this.cardValue = 0;

        this.setBtnListener(activity);
    }

    //===========================================

    // change player img listener
    void setBtnListener(Activity activity){
        playerImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!gameRunning) {
                    setNewPlayerImg(activity);
                }
            }
        });
    }

    void setNewPlayerImg(Activity activity){
        TypedArray images = activity.getResources().obtainTypedArray(R.array.playerImages);
        playerImg.setImageResource(images.getResourceId(playerImgArrIndex,-1));

        if(playerImgArrIndex < images.length()-1)
            playerImgArrIndex++;
        else
            playerImgArrIndex = 0;
    }

    //===========================================

    public void setCardValue(int cardValue) { this.cardValue = cardValue; }

    public void setGameRunning(boolean gameRunning) { this.gameRunning = gameRunning; }

    //===========================================

    public int increaseReturnGameScore() { return ++this.gameScore; }

    public int getGameScore() { return gameScore; }

    public boolean isGameRunning() { return gameRunning; }

    //===========================================

    public ImageView getPlayerCardView() { return cardImg; }

    public TextView getPlayerScoreView() { return playerScore; }

}
