package com.hfad.twentyonegame.ui.viewmodels;


import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import androidx.annotation.NonNull;
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
    public MutableLiveData<Boolean> isScoreCalculated = new MutableLiveData<>();

    private Random random = new Random();
    private int currentPlayerIndex = 0;
    public MutableLiveData<Integer> roundLiveData = new MutableLiveData<>();
    private boolean isGameOver = false;
    public MutableLiveData<Boolean> isLastTurn = new MutableLiveData<>();

    private int randomAnimationCounter = 0;
    private final Handler handler2 = new Handler(Looper.getMainLooper(), new Handler.Callback() {
        @Override
        public boolean handleMessage(@NonNull Message message) {
            randomAnimationCounter++;
            if (randomAnimationCounter == 10){
                randomAnimationCounter=0;
                currentPlayer.setScore(calculateScore());
                isScoreCalculated.setValue(true);
                currentPlayerIndex++;
                if (calculateScore()>=21){
                    isGameOver = true;
                }
            } else {
                randomizeDice();
                handler2.sendEmptyMessageDelayed(0, 100);
            }
            return false;
        }
    });

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
        if (isGameOver && currentPlayerIndex == playerList.size() -1){
            isLastTurn.setValue(true);
        }else{
            if (currentPlayerIndex <= playerList.size() - 1) {
                currentPlayer = playerList.get(currentPlayerIndex);
            } else {
                int currentRound = roundLiveData.getValue();
                roundLiveData.setValue(currentRound + 1);
                currentPlayerIndex = 0;
                currentPlayer = playerList.get(currentPlayerIndex);
            }
        }
    }

    public void takeTurn() {
        isScoreCalculated.setValue(false);
        handler2.sendEmptyMessageDelayed(0, 100);
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
