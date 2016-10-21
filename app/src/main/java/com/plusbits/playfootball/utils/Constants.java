package com.plusbits.playfootball.utils;

/**
 * Created by mberengueras on 04/10/2016.
 */

public class Constants {
    //Global constants
    public static final int NUM_PLAYERS = 11;
    public static final int PLAYER_ICON_SIZE = 64;

    //Default constants
    //Players postions in percent
    public static final double[] xPlayersPositions = new double[]{0.5,0.9,0.65,0.35,0.1,0.75,0.5,0.25,0.88,0.5,0.12};
    public static final double[] yPlayersPositions = new double[]{0.95,0.70,0.75,0.75,0.70,0.4,0.55,0.4,0.18,0.08,0.18};

    public static enum PLAYER_STATES {
        AVAILABLE,
        INJURED,
        SANCTIONED,
        NOT_CALLED
    }
}
