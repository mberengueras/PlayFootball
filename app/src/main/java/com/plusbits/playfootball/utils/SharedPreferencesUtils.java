package com.plusbits.playfootball.utils;

import android.app.Activity;
import android.content.SharedPreferences;
import android.util.Pair;

import java.util.ArrayList;

/**
 * Created by mberengueras on 11/10/2016.
 */

public class SharedPreferencesUtils {
    public static final String PREFS_NAME = "MyPrefsFile";

    public static final String PLAYER_PIECE_ID_KEY = "player_piece_id_key";

    public static ArrayList<Pair> getPlayerPiecesPositions(){
        return null;
    }

    public static void savePlayerPieceId(Activity activity, int pieceTag, Long playerId){
        SharedPreferences settings = activity.getSharedPreferences(PREFS_NAME, 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putLong(PLAYER_PIECE_ID_KEY + "_" + pieceTag, playerId);
        editor.commit();
    }

    public static Long getPlayerPieceId(Activity activity, int pieceTag){
        SharedPreferences settings = activity.getSharedPreferences(PREFS_NAME, 0);
        Long playerId = settings.getLong(PLAYER_PIECE_ID_KEY + "_" + pieceTag, -1);
        return playerId;
    }
}
