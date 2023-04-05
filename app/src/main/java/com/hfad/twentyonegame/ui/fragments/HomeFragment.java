package com.hfad.twentyonegame.ui.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.google.android.material.snackbar.Snackbar;
import com.hfad.twentyonegame.R;
import com.hfad.twentyonegame.databinding.FragmentHomeBinding;
import com.hfad.twentyonegame.ui.dialogs.DialogManager;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;

    @Inject
    DialogManager dialogManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        setListeners();


        return binding.getRoot();

    }

    private void setListeners(){
        binding.btnSelectNumber.setOnClickListener(view -> {
            dialogManager.showNumberPickerDialog(input -> binding.tvPlayerCount.setText(input));
        });




        binding.btnStart.setOnClickListener(view -> {
            String playerCount = (String) binding.tvPlayerCount.getText();
            if (Integer.parseInt(playerCount) == 0){
                Toast.makeText(requireContext(), "Please, select number of players! " + playerCount, Toast.LENGTH_SHORT).show();
            }else {
                Navigation.findNavController(view).navigate(R.id.action_homeFragment_to_gameFragment);
            }
        });

    }
}