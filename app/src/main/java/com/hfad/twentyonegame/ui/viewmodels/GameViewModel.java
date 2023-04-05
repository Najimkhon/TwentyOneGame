package com.hfad.twentyonegame.ui.viewmodels;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

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

    Random random = new Random();
    public int randomNum = random.nextInt(6) + 1;

    public void randomizeDice() {
        diceOneLiveData.setValue(getRandomDiceSide());
        diceTwoLiveData.setValue(getRandomDiceSide());
        diceThreeLiveData.setValue(getRandomDiceSide());
        diceFourLiveData.setValue(getRandomDiceSide());
    }

    private int getRandomDiceSide() {
        return random.nextInt(6) + 1;
    }
}
