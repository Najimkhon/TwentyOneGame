package com.hfad.twentyonegame.ui.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.google.android.gms.ads.identifier.AdvertisingIdClient;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.hfad.twentyonegame.databinding.FragmentHomeBinding;
import com.hfad.twentyonegame.ui.dialogs.DialogManager;

import java.io.IOException;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;

    @Inject
    DialogManager dialogManager;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        setListeners();
        getAdvertisingId();

        return binding.getRoot();
    }

    private void setListeners() {
        binding.btnSelectNumber.setOnClickListener(view -> dialogManager.showNumberPickerDialog(input -> binding.tvPlayerCount.setText(input)));

        binding.btnStart.setOnClickListener(view -> {
            int playerCount = (int) Integer.parseInt(binding.tvPlayerCount.getText().toString());
            HomeFragmentDirections.ActionHomeFragmentToGameFragment action = HomeFragmentDirections.actionHomeFragmentToGameFragment(playerCount);
            Navigation.findNavController(view).navigate(action);
        });

    }

    private void getAdvertisingId(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                AdvertisingIdClient.Info idInfo = null;
                try {
                    idInfo = AdvertisingIdClient.getAdvertisingIdInfo(requireContext());
                } catch (GooglePlayServicesNotAvailableException e) {
                    e.printStackTrace();
                } catch (GooglePlayServicesRepairableException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                String advertId = null;
                try{
                    advertId = idInfo.getId();
                }catch (NullPointerException e){
                    e.printStackTrace();
                }

                try {
                    Thread.sleep(10000); // sleep for 10 seconds
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                final String finalAdvertId = advertId;
                getActivity().runOnUiThread(() -> Toast.makeText(requireContext(), "Your ad Id: "+ finalAdvertId, Toast.LENGTH_LONG).show());
            }
        }).start();
    }
}