package com.plusbits.playfootball.fragments;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;

import com.plusbits.playfootball.R;
import com.plusbits.playfootball.interfaces.FormationsListDialogListener;
import com.plusbits.playfootball.models.Formation;
import com.plusbits.playfootball.utils.StorageUtils;

import java.util.ArrayList;

/**
 * Created by mberengueras on 27/10/2016.
 */

public class FormationsListDialogFragment extends DialogFragment {
        private FormationsListDialogListener listener;

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);

            // Do not create a new Fragment when the Activity is re-created such as orientation changes.
            setRetainInstance(true);
            setStyle(DialogFragment.STYLE_NORMAL, android.R.style.Theme_Material_Light_Dialog);
        }

    public FormationsListDialogListener getListener() {
        return listener;
    }

    public void setListener(FormationsListDialogListener listener) {
        this.listener = listener;
    }

    private CharSequence[] formations2Items(ArrayList<Formation> formations){
        CharSequence[] items = new CharSequence[formations.size()];

        int i = 0;
        for (Formation formation : formations){
            items[i] = (formation.getName() != null) ? formation.getName() : "";
            ++i;
        }

        return items;
    }


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final ArrayList<Formation> formationsArr = StorageUtils.getInstance(this.getActivity()).getAllFormations();
        CharSequence[] items = this.formations2Items(formationsArr);
        AlertDialog.Builder builder = new AlertDialog.Builder(this.getActivity());
        builder.setTitle(R.string.selectPlayerDialogTitle)
                .setItems(items, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // The 'which' argument contains the index position
                        // of the selected item
                        Formation selectedFormation = formationsArr.get(which);
                        listener.onFormationSelected(selectedFormation);
                        dismiss();
                    }
                });
        return builder.create();
    }
}
