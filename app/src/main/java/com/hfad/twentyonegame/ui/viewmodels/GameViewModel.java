package com.hfad.twentyonegame.ui.viewmodels;


import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.hfad.twentyonegame.models.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class GameViewModel extends ViewModel {

    private static final int ANIMATION_INTERVAL_DURATION = 100;
    private static final int ANIMATION_STEP_COUNT = 5;
    private static final int DREAM_SCORE = 21;
    private static final int DICE_SIDE_COUNT = 6;

    @Inject
    public GameViewModel() {}

    public MutableLiveData<Integer> diceOneLiveData = new MutableLiveData<>();
    public MutableLiveData<Integer> diceTwoLiveData = new MutableLiveData<>();
    public MutableLiveData<Integer> diceThreeLiveData = new MutableLiveData<>();
    public MutableLiveData<Integer> diceFourLiveData = new MutableLiveData<>();
    public MutableLiveData<Boolean> isScoreCalculatedLiveData = new MutableLiveData<>();
    public MutableLiveData<Integer> roundLiveData = new MutableLiveData<>();
    public MutableLiveData<Boolean> isLastTurnLiveData = new MutableLiveData<>();

    public Player currentPlayer;
    public Player winner;

    private final Random mRandom = new Random();
    private List<Player> mPlayerList;
    private int mPlayerCount;
    private int mCurrentPlayerIndex = 0;
    private int mRandomAnimationCounter = 0;
    private boolean mIsGameOver = false;

    public void startGame(int playerCount) {
        mPlayerCount = playerCount;
        mIsGameOver = false;
        roundLiveData.setValue(1);
        isLastTurnLiveData.setValue(false);
        resetPlayerList();
        createPlayers();
        getPlayer();
    }

    public void getPlayer() {
        if (mCurrentPlayerIndex <= mPlayerList.size() - 1) {
            currentPlayer = mPlayerList.get(mCurrentPlayerIndex);
        } else {
            finishRound();
        }
    }

    public void takeTurn() {
        isScoreCalculatedLiveData.setValue(false);
        mHandler.sendEmptyMessageDelayed(0, ANIMATION_INTERVAL_DURATION);
    }

    public Player findWinner(List<Player> players) {
        Player winner = null;
        int closestScore = -1;
        for (Player player : players) {
            int score = player.getScore();
            if (score <= DREAM_SCORE && score > closestScore) {
                winner = player;
                closestScore = score;
            }
        }
        return winner;
    }

    public void resetPlayerList() {
        mCurrentPlayerIndex = 0;
        mPlayerList = null;
    }

    private void randomizeDice() {
        diceOneLiveData.setValue(getRandomDiceSide());
        diceTwoLiveData.setValue(getRandomDiceSide());
        diceThreeLiveData.setValue(getRandomDiceSide());
        diceFourLiveData.setValue(getRandomDiceSide());
    }

    private int getRandomDiceSide() {
        return mRandom.nextInt(DICE_SIDE_COUNT) + 1;
    }

    private void finishRound() {
        if (mIsGameOver) {
            winner = findWinner(mPlayerList);
            isLastTurnLiveData.setValue(true);
        } else {
            int currentRound = roundLiveData.getValue();
            roundLiveData.setValue(currentRound + 1);
            mCurrentPlayerIndex = 0;
            changeStarterPlayerToNext();
            currentPlayer = mPlayerList.get(mCurrentPlayerIndex);
        }
    }

    private int calculateScore() {
        return diceOneLiveData.getValue() + diceTwoLiveData.getValue() + diceThreeLiveData.getValue() + diceFourLiveData.getValue();
    }

    private void createPlayers() {
        mPlayerList = new ArrayList<>();
        for (int i = 1; i <= mPlayerCount; i++) {
            mPlayerList.add(new Player(i + "", 0));
        }
    }

    private void changeStarterPlayerToNext() {
        Player starterPlayer = mPlayerList.get(0);
        mPlayerList.remove(0);
        mPlayerList.add(starterPlayer);
    }

    private final Handler mHandler = new Handler(Looper.getMainLooper(), new Handler.Callback() {
        @Override
        public boolean handleMessage(@NonNull Message message) {
            mRandomAnimationCounter++;
            if (mRandomAnimationCounter == ANIMATION_STEP_COUNT) {
                mRandomAnimationCounter = 0;
                currentPlayer.setScore(calculateScore());
                isScoreCalculatedLiveData.setValue(true);
                mCurrentPlayerIndex++;
                if (calculateScore() >= DREAM_SCORE) {
                    mIsGameOver = true;
                }
            } else {
                randomizeDice();
                mHandler.sendEmptyMessageDelayed(0, ANIMATION_INTERVAL_DURATION);
            }
            return false;
        }
    });
}
