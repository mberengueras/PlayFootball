package com.plusbits.playfootball.utils;

import android.app.Activity;
import android.content.Context;

import com.plusbits.playfootball.R;
import com.plusbits.playfootball.models.Player;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by mberengueras on 18/10/2016.
 */

public class UIUtils {
    public static void setPlayerImageBorderColor(Context context, CircleImageView imageView, Player player) {
        UIUtils.setPlayerImageBorderColor(context, imageView, player, false);
    }

    public static void setPlayerImageBorderColor(Context context, CircleImageView imageView, Player player, boolean ignoreAvailable) {
        if (player == null) return;
        switch (player.getState()) {
            case AVAILABLE:
                if (!ignoreAvailable) imageView.setBorderColor(((Activity)context).getResources().getColor(R.color.playerStateAvailableColor));
                break;
            case INJURED:
                imageView.setBorderColor(((Activity)context).getResources().getColor(R.color.playerStateInjuredColor));
                break;
            case SANCTIONED:
                imageView.setBorderColor(((Activity)context).getResources().getColor(R.color.playerStateSanctionedColor));
                break;
            case NOT_CALLED:
                imageView.setBorderColor(((Activity)context).getResources().getColor(R.color.playerStateNotCalledColor));
                break;

        }
    }
}
