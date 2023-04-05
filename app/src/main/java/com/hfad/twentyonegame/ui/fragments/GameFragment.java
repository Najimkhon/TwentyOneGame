package com.hfad.twentyonegame.ui.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.hfad.twentyonegame.R;
import com.hfad.twentyonegame.databinding.FragmentGameBinding;

public class GameFragment extends Fragment {

    private FragmentGameBinding binding;
    private int playerCount;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentGameBinding.inflate(inflater, container, false);
        playerCount = GameFragmentArgs.fromBundle(getArguments()).getPlayerCount();
        binding.btnStart.setOnClickListener(view -> {
            Navigation.findNavController(view).navigate(R.id.action_gameFragment_to_resultFragment);
        });

        return binding.getRoot();
    }
}