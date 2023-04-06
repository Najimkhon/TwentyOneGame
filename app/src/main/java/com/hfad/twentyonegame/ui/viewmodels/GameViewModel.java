package com.hfad.twentyonegame.ui.viewmodels;


import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.hfad.twentyonegame.R;
import com.hfad.twentyonegame.models.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class GameViewModel extends ViewModel {
    @Inject
    public GameViewModel() {
    }


    public MutableLiveData<Integer> diceOneLiveData = new MutableLiveData<>();
    public MutableLiveData<Integer> diceTwoLiveData = new MutableLiveData<>();
    public MutableLiveData<Integer> diceThreeLiveData = new MutableLiveData<>();
    public MutableLiveData<Integer> diceFourLiveData = new MutableLiveData<>();
    private List<Player> playerList;
    private int playerCount;
    public Player currentPlayer;

    private Random random = new Random();
    private int currentPlayerIndex = 0;
    public MutableLiveData<Integer> roundLiveData = new MutableLiveData<>();
    private boolean isGameOver = false;


    private void randomizeDice() {
        diceOneLiveData.setValue(getRandomDiceSide());
        diceTwoLiveData.setValue(getRandomDiceSide());
        diceThreeLiveData.setValue(getRandomDiceSide());
        diceFourLiveData.setValue(getRandomDiceSide());
    }

    private int getRandomDiceSide() {
        return random.nextInt(6) + 1;
    }

    public void startGame(int playerCount) {
        this.playerCount = playerCount;
        roundLiveData.setValue(1);
        createPlayers();
        getPlayer();
    }

    public void getPlayer() {
        if (currentPlayerIndex <= playerList.size() - 1) {
            currentPlayer = playerList.get(currentPlayerIndex);
        } else {
            int currentRound = roundLiveData.getValue();
            roundLiveData.setValue(currentRound + 1);
            currentPlayerIndex = 0;
            currentPlayer = playerList.get(currentPlayerIndex);
        }
    }

    public void takeTurn() {
        randomizeDice();
        currentPlayer.setScore(calculateScore());
        currentPlayerIndex++;
    }

    private int calculateScore() {
        return diceOneLiveData.getValue() + diceTwoLiveData.getValue() + diceThreeLiveData.getValue() + diceFourLiveData.getValue();
    }

    private void createPlayers() {
        playerList = new ArrayList<>();
        for (int i = 1; i <= playerCount; i++) {
            Player player = new Player("Player " + i, 0, R.drawable.player_1);
            playerList.add(player);
        }
    }
}
