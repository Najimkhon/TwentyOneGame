package com.hfad.twentyonegame.ui.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

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
            dialogManager.showNumberPickerDialog(new DialogManager.OnDialogClickListener() {
                @Override
                public void onSaveClicked(String input) {
                    binding.tvPlayerCount.setText(input);
                }
            });
        });
    }
}