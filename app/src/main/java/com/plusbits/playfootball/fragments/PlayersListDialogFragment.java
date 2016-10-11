package com.plusbits.playfootball.fragments;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;

import com.plusbits.playfootball.R;
import com.plusbits.playfootball.interfaces.PlayersListDialogListener;
import com.plusbits.playfootball.models.Player;
import com.plusbits.playfootball.utils.StorageUtils;

import java.util.ArrayList;

/**
 * Created by mberengueras on 07/10/2016.
 */

public class PlayersListDialogFragment extends DialogFragment {
    private PlayersListDialogListener listener;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Do not create a new Fragment when the Activity is re-created such as orientation changes.
        setRetainInstance(true);
        setStyle(DialogFragment.STYLE_NORMAL, android.R.style.Theme_Material_Light_Dialog);
    }

    public PlayersListDialogListener getListener() {
        return listener;
    }

    public void setListener(PlayersListDialogListener listener) {
        this.listener = listener;
    }

    private CharSequence[] players2Items(ArrayList<Player> players){
        CharSequence[] items = new CharSequence[players.size()];

        int i = 0;
        for (Player player : players){
            items[i] = (player.getName() != null) ? player.getName() : "";
            ++i;
        }

        return items;
    }


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final ArrayList<Player> playersArr = StorageUtils.getInstance(this.getActivity()).getAllPlayers();
        CharSequence[] items = this.players2Items(playersArr);
        AlertDialog.Builder builder = new AlertDialog.Builder(this.getActivity());
        builder.setTitle(R.string.selectPlayerDialogTitle)
                .setItems(items, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // The 'which' argument contains the index position
                        // of the selected item
                        Player selectedPlayer = playersArr.get(which);
                        listener.onPlayerSelected(selectedPlayer);
                        dismiss();
                    }
                });
        return builder.create();
    }
}
