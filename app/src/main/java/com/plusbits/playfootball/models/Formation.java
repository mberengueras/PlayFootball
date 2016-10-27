package com.plusbits.playfootball.models;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

/**
 * Created by mberengueras on 24/10/2016.
 */
@Entity
public class Formation {
    @Id(autoincrement = true)
    private Long id;

    private String name;
    private String xPlayersPositions;
    private String yPlayersPositions;

    public Formation(String name) {
        this.name = name;
    }

    @Generated(hash = 702402653)
    public Formation(Long id, String name, String xPlayersPositions, String yPlayersPositions) {
        this.id = id;
        this.name = name;
        this.xPlayersPositions = xPlayersPositions;
        this.yPlayersPositions = yPlayersPositions;
    }

    @Generated(hash = 2022618329)
    public Formation() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double[] getxPlayersPositions() {
        return this.string2Array(xPlayersPositions);
    }

    public void setxPlayersPositions(double[] xPlayersPositions) {
        this.xPlayersPositions = this.array2String(xPlayersPositions);
    }

    public double[] getyPlayersPositions() {
        return this.string2Array(yPlayersPositions);
    }

    public void setyPlayersPositions(double[] yPlayersPositions) {
        this.yPlayersPositions = this.array2String(yPlayersPositions);
    }

    private String array2String (double[] positions){
        String strPos = "";
        for (double pos : positions) strPos += pos + ",";
        if(strPos.isEmpty()) return "";
        return strPos.substring(0, strPos.length()-2);
    }

    private double[] string2Array (String strPos){
        String[] arrStrPos = strPos.split(",");
        double[] arrDoublePos = new double[arrStrPos.length];
        for (int i = 0; i < arrStrPos.length; ++i) {
            arrDoublePos[i] = Double.parseDouble(arrStrPos[i]);
        }

        return arrDoublePos;
    }

    public void setxPlayersPositions(String xPlayersPositions) {
        this.xPlayersPositions = xPlayersPositions;
    }

    public void setyPlayersPositions(String yPlayersPositions) {
        this.yPlayersPositions = yPlayersPositions;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getXPlayersPositions() {
        return this.xPlayersPositions;
    }

    public void setXPlayersPositions(String xPlayersPositions) {
        this.xPlayersPositions = xPlayersPositions;
    }

    public String getYPlayersPositions() {
        return this.yPlayersPositions;
    }

    public void setYPlayersPositions(String yPlayersPositions) {
        this.yPlayersPositions = yPlayersPositions;
    }
}
