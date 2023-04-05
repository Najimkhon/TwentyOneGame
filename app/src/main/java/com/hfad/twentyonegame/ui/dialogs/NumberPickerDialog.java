package com.hfad.twentyonegame.ui.dialogs;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.NumberPicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.hfad.twentyonegame.databinding.NumberInputDialogBinding;

public class NumberPickerDialog extends DialogFragment {

    private final DialogManager.OnDialogClickListener listener;

    public NumberPickerDialog(DialogManager.OnDialogClickListener listener){
        this.listener = listener;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        NumberInputDialogBinding dialogBinding = NumberInputDialogBinding.inflate(inflater, container, false);

        NumberPicker numberPicker = dialogBinding.numberPicker;
        numberPicker.setMaxValue(10);
        numberPicker.setMinValue(1);

        setCancelable(true);
        getDialog().setCanceledOnTouchOutside(true);
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        dialogBinding.btnSave.setOnClickListener(v -> {
            listener.onSaveClicked(String.valueOf(numberPicker.getValue()));
            dismiss();
        });

        dialogBinding.btnCancel.setOnClickListener(v -> dismiss());


        return dialogBinding.getRoot();
    }
}
