package com.plusbits.playfootball.utils;

import android.app.Activity;
import android.database.sqlite.SQLiteDatabase;

import com.plusbits.playfootball.models.DaoMaster;
import com.plusbits.playfootball.models.DaoSession;
import com.plusbits.playfootball.models.Player;
import com.plusbits.playfootball.models.PlayerDao;

import java.util.ArrayList;

/**
 * Created by mberengueras on 06/10/2016.
 */

public class StorageUtils {
    private final String DB_NAME_PLAYERS = "players_db";
    private static StorageUtils instance = null;
    private DaoMaster.DevOpenHelper helper;
    private SQLiteDatabase db;
    private DaoMaster daoMaster;
    private DaoSession daoSession;
    private PlayerDao playerDao;

    public StorageUtils(){

    }

    public static StorageUtils getInstance(Activity activity){
        if(instance == null){
            instance = new StorageUtils();
            instance.initDB(activity);
        }

        return instance;
    }

    public void initDB(Activity activity){
        helper = new DaoMaster.DevOpenHelper(activity, DB_NAME_PLAYERS, null);
        db = helper.getWritableDatabase();
        daoMaster = new DaoMaster(db);
        daoSession = daoMaster.newSession();
        playerDao = daoSession.getPlayerDao();
    }

    public void updatePlayer(Player player){
        playerDao.update(player);
    }

    public void savePlayer(Player player){
        playerDao.insert(player);
    }

    public void removePlayer(Player player){
        playerDao.deleteByKey(player.getId());
    }

    public Player getPlayer(Long id){
        return playerDao.load(id);
    }

    public ArrayList<Player> getAllPlayers(){
        return new ArrayList<>(playerDao.loadAll());
    }

    public ArrayList<Player> getAllNotStarterPlayers(){
        return new ArrayList<>(playerDao.queryBuilder()
                .where(PlayerDao.Properties.IsStarter.eq(false))
                .orderAsc(PlayerDao.Properties.Dorsal)
                .list());
    }
}
