package com.hfad.twentyonegame.ui.dialogs;
import androidx.fragment.app.FragmentManager;

import javax.inject.Inject;
import javax.inject.Singleton;


public class DialogManager {

    private final FragmentManager fragmentManager;

    @Inject
    public DialogManager(FragmentManager fragmentManager) {
        this.fragmentManager = fragmentManager;
    }

    public void showNumberPickerDialog(OnDialogClickListener listener){
        NumberPickerDialog dialog = new NumberPickerDialog(listener);
        dialog.show(fragmentManager, "NumberPickerDialog");
    }

    public interface OnDialogClickListener {
        void onSaveClicked(String input);
    }
}


