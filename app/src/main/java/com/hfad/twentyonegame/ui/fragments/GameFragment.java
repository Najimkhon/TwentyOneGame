package com.hfad.twentyonegame.ui.fragments;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.hfad.twentyonegame.R;
import com.hfad.twentyonegame.databinding.FragmentGameBinding;
import com.hfad.twentyonegame.ui.viewmodels.GameViewModel;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class GameFragment extends Fragment {

    private FragmentGameBinding binding;
    private int playerCount;
    private GameViewModel viewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentGameBinding.inflate(inflater, container, false);

        viewModel = new ViewModelProvider(requireActivity()).get(GameViewModel.class);

        playerCount = GameFragmentArgs.fromBundle(getArguments()).getPlayerCount();


        viewModel.startGame(playerCount);

        setDiceObservers();



        binding.tvPlayerName.setText(viewModel.currentPlayer.getName());
        viewModel.roundLiveData.observe(getViewLifecycleOwner(), round->{
            binding.tvRound.setText(round + "");
        });


        binding.btnThrow.setOnClickListener(view -> {
            throwDice();
        });

        binding.btnNextPlayer.setOnClickListener(view -> {
            nextPlayer();
        });


        return binding.getRoot();
    }

    private void throwDice() {
        binding.tvScore.setVisibility(View.VISIBLE);
        binding.btnThrow.setVisibility(View.INVISIBLE);
        binding.btnNextPlayer.setVisibility(View.VISIBLE);
        viewModel.takeTurn();
        binding.tvScore.setText("Your score :"+ viewModel.currentPlayer.getScore());
    }

    private void nextPlayer() {
        binding.tvScore.setVisibility(View.INVISIBLE);
        binding.btnThrow.setVisibility(View.VISIBLE);
        binding.btnNextPlayer.setVisibility(View.INVISIBLE);
        viewModel.getPlayer();
        binding.tvPlayerName.setText(viewModel.currentPlayer.getName());
    }

    private void setDiceObservers() {
        viewModel.diceOneLiveData.observe(getViewLifecycleOwner(), diceSide -> {
            binding.dice1.setImageResource(getDiceSideImage(diceSide));
        });
        viewModel.diceTwoLiveData.observe(getViewLifecycleOwner(), diceSide -> {
            binding.dice2.setImageResource(getDiceSideImage(diceSide));
        });
        viewModel.diceThreeLiveData.observe(getViewLifecycleOwner(), diceSide -> {
            binding.dice3.setImageResource(getDiceSideImage(diceSide));
        });
        viewModel.diceFourLiveData.observe(getViewLifecycleOwner(), diceSide -> {
            binding.dice4.setImageResource(getDiceSideImage(diceSide));
        });
    }

    private int getDiceSideImage(int sideNum) {
        switch (sideNum) {
            case 1:
                return R.drawable.dice_1;
            case 2:
                return R.drawable.dice_2;
            case 3:
                return R.drawable.dice_3;
            case 4:
                return R.drawable.dice_4;
            case 5:
                return R.drawable.dice_5;
            case 6:
                return R.drawable.dice_6;
            default:
                return -1;
        }
    }
}