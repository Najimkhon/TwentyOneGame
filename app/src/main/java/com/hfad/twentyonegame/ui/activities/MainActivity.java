package com.hfad.twentyonegame.ui.activities;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import com.hfad.twentyonegame.R;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}