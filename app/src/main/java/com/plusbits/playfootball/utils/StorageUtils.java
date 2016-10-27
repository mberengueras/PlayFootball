package com.plusbits.playfootball.utils;

import android.app.Activity;
import android.database.sqlite.SQLiteDatabase;

import com.plusbits.playfootball.models.DaoMaster;
import com.plusbits.playfootball.models.DaoSession;
import com.plusbits.playfootball.models.Formation;
import com.plusbits.playfootball.models.FormationDao;
import com.plusbits.playfootball.models.Player;
import com.plusbits.playfootball.models.PlayerDao;

import org.greenrobot.greendao.query.QueryBuilder;

import java.util.ArrayList;
import java.util.List;

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
    private FormationDao formationDao;

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
        formationDao = daoSession.getFormationDao();
    }

    /**
     * Methods to manage player's db
     * */
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

    public void savePlayers(List<Player> players){
        playerDao.updateInTx(players);
    }

    public ArrayList<Player> getAllPlayers(){
        return new ArrayList<>(playerDao.loadAll());
    }

    public ArrayList<Player> getAllNotStarterPlayers(){
        return new ArrayList<>(playerDao.queryBuilder()
                .where(PlayerDao.Properties.IsStarter.eq(false))
                .orderAsc(PlayerDao.Properties.Number)
                .list());
    }

    public ArrayList<Player> getAllAvailablePlayers(){
        QueryBuilder qb = playerDao.queryBuilder();
        qb.where(PlayerDao.Properties.State.eq(Constants.PLAYER_STATES.AVAILABLE.name()), PlayerDao.Properties.IsStarter.eq(false));
        List players = qb.list();
        return new ArrayList<>(players);
    }

    public String[] getAllAvailableNumbers(){
        List<Player> players = playerDao.loadAll();
        ArrayList<String> numbers = new ArrayList<String>();
        for (int i = 0; i < Constants.MAX_PLAYER_NUMBER; ++i) numbers.add(String.valueOf(i));
        for (Player player: players) numbers.remove(String.valueOf(player.getNumber()));
        String[] arrRet = new String[numbers.size()];
        for (int i = 0; i < numbers.size(); ++i) arrRet[i] = numbers.get(i);
        return arrRet;
    }

    /**
     * Methods to manage formation's db
     * */
    public void saveFormation(Formation formation){
        formationDao.save(formation);
    }

    public void removeFormation(Formation formation){
        formationDao.deleteByKey(formation.getId());
    }

    public void updateFormation(Formation formation){
        formationDao.update(formation);
    }

    public Formation getFormation(Long id) {
        return formationDao.load(id);
    }

    public ArrayList<Formation> getAllFormations(){
        return new ArrayList<>(formationDao.loadAll());
    }
}
