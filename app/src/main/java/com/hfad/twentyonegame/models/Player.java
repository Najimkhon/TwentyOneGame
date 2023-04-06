package com.hfad.twentyonegame.models;

import com.hfad.twentyonegame.R;

public class Player {
    private String id;
    private int score;
    private int avatarRes;

    public Player(String id, int score) {
        this.id = id;
        this.score = score;
    }

    public String getName() {
        return "Player "+ id;
    }

    public int getScore() {
        return score;
    }
    public void setScore(int score) {
        this.score = score;
    }

    public int getAvatarRes() {
        switch(id) {
            case "1":
                avatarRes = R.drawable.player_1;
                break;
            case "2":
                avatarRes = R.drawable.player_2;
                break;
            case "3":
                avatarRes = R.drawable.player_3;
                break;
            case "4":
                avatarRes = R.drawable.player_4;
                break;
            case "5":
                avatarRes = R.drawable.player_5;
                break;
            case "6":
                avatarRes = R.drawable.player_6;
                break;
            case "7":
                avatarRes = R.drawable.player_7;
                break;
            case "8":
                avatarRes = R.drawable.player_8;
                break;
            case "9":
                avatarRes = R.drawable.player_9;
                break;
            case "10":
                avatarRes = R.drawable.player_10;
                break;
        }
        return avatarRes;
    }
}
