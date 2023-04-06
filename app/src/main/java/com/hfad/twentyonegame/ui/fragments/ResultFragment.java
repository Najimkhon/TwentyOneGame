package com.hfad.twentyonegame.ui.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.hfad.twentyonegame.R;
import com.hfad.twentyonegame.databinding.FragmentResultBinding;
import com.hfad.twentyonegame.ui.viewmodels.GameViewModel;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class ResultFragment extends Fragment {

    private FragmentResultBinding binding;
    private GameViewModel viewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentResultBinding.inflate(inflater, container, false);
        viewModel = new ViewModelProvider(requireActivity()).get(GameViewModel.class);

        binding.winnerScore.setText("Score: " + viewModel.winner.getScore());
        binding.winnerName.setText(viewModel.winner.getName());
        binding.btnStart.setOnClickListener(view -> {
            Navigation.findNavController(view).navigate(R.id.action_resultFragment_to_homeFragment);
        });

        return binding.getRoot();
    }
}