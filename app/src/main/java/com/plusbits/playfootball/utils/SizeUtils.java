package com.plusbits.playfootball.utils;

import android.util.DisplayMetrics;

/**
 * Created by mberengueras on 04/10/2016.
 */

public class SizeUtils {
    public static int getDPI(int size, DisplayMetrics metrics){
        return (size * metrics.densityDpi) / DisplayMetrics.DENSITY_DEFAULT;
    }
}
