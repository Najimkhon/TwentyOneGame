package com.hfad.twentyonegame.models;

import androidx.annotation.DrawableRes;

public class Player {
    private String name;
    private int score;
    private int avatarRes;

    public Player(String name, int score, int avatarRes) {
        this.name = name;
        this.score = score;
        this.avatarRes = avatarRes;
    }

    public String getName() {
        return name;
    }

    public int getScore() {
        return score;
    }
    public void setScore(int score) {
        this.score = score;
    }

    public int getAvatarRes() {
        return avatarRes;
    }
}
