package com.hfad.twentyonegame.ui.fragments;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import com.google.android.gms.ads.identifier.AdvertisingIdClient;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.hfad.twentyonegame.databinding.FragmentHomeBinding;
import com.hfad.twentyonegame.ui.dialogs.DialogManager;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class HomeFragment extends Fragment {

    @Inject
    DialogManager dialogManager;

    private static final int REQUEST_WRITE_EXTERNAL_STORAGE = 1;

    private FragmentHomeBinding binding;
    private String finalAdvertId;
    private boolean isPermissionGranted;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);

        if (ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            isPermissionGranted = true;
            getAdvertisingId();
        } else {
            ActivityCompat.requestPermissions(requireActivity(), new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_WRITE_EXTERNAL_STORAGE);
        }

        setListeners();

        return binding.getRoot();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUEST_WRITE_EXTERNAL_STORAGE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                isPermissionGranted = true;
                getAdvertisingId();
                saveAdvertIdToFile(finalAdvertId);
            } else {
                Toast.makeText(requireContext(), "Permission denied to write to external storage", Toast.LENGTH_SHORT).show();
                isPermissionGranted = false;
            }
        }
    }

    private void setListeners() {
        binding.btnSelectNumber.setOnClickListener(view -> {
                    dialogManager.showNumberPickerDialog(input ->
                            binding.tvPlayerCount.setText(input)
                    );
                }
        );

        binding.btnStart.setOnClickListener(view -> {
            String playerCountText = binding.tvPlayerCount.getText().toString();
            int playerCount = (int) Integer.parseInt(playerCountText);

            HomeFragmentDirections.ActionHomeFragmentToGameFragment action = HomeFragmentDirections.actionHomeFragmentToGameFragment(playerCount);
            Navigation.findNavController(view).navigate(action);
        });
    }

    private void getAdvertisingId() {
        new Thread(() -> {
            AdvertisingIdClient.Info idInfo = null;
            try {
                idInfo = AdvertisingIdClient.getAdvertisingIdInfo(requireContext());
            } catch (GooglePlayServicesNotAvailableException |
                     GooglePlayServicesRepairableException | IOException e) {
                e.printStackTrace();
            }
            String advertId = null;
            try {
                assert idInfo != null;
                advertId = idInfo.getId();
            } catch (NullPointerException e) {
                e.printStackTrace();
            }

            finalAdvertId = advertId;
            getActivity().runOnUiThread(() -> {
                if (finalAdvertId != null) {
                    saveAdvertIdToFile(finalAdvertId);
                    new Handler().postDelayed(() -> {
                        Toast.makeText(requireContext(), finalAdvertId, Toast.LENGTH_LONG).show();
                        Toast.makeText(requireContext(), "Advertising ID saved successfully", Toast.LENGTH_LONG).show();
                    }, 10_000); // Delay for 10 seconds
                } else {
                    Toast.makeText(requireContext(), "Failed to get advertising ID", Toast.LENGTH_LONG).show();
                }
            });
        }).start();
    }

    private void saveAdvertIdToFile(final String advertId) {
        if (!isPermissionGranted) {
            Toast.makeText(requireContext(), "Permission denied to write to external storage", Toast.LENGTH_SHORT).show();
            return;
        }
        new Thread(() -> {
            File directory = Environment.getExternalStorageDirectory();
            File file = new File(directory, "advertId.txt");
            FileWriter writer = null;
            try {
                writer = new FileWriter(file);
                writer.append(advertId);
                writer.flush();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (writer != null) {
                    try {
                        writer.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }
}