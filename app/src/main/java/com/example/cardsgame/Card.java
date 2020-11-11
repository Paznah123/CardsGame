package com.example.cardsgame;

import android.widget.ImageView;

public class Card {

    int number;
    String type;

    public Card(int number, String type) {
        this.number = number;
        this.type = type;
    }

    public int getNumber() {
        return number;
    }

    public String getType() {
        return type;
    }

}
