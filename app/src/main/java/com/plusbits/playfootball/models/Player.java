package com.plusbits.playfootball.models;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.android.databinding.library.baseAdapters.BR;
import com.plusbits.playfootball.utils.Constants;

import org.greenrobot.greendao.annotation.Convert;
import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.converter.PropertyConverter;

/**
 * Created by mberengueras on 06/10/2016.
 */
@Entity
public class Player extends BaseObservable {
    @Id(autoincrement = true)
    private Long id;

    private String name;
    private String photoPath;
    private int dorsal;
    private boolean isStarter;

    @Convert(converter = PlayerStateConverter.class, columnType = String.class)
    private Constants.PLAYER_STATES state;

    public Player() {

    }

    @Generated(hash = 343664862)
    public Player(Long id, String name, String photoPath, int dorsal, boolean isStarter,
            Constants.PLAYER_STATES state) {
        this.id = id;
        this.name = name;
        this.photoPath = photoPath;
        this.dorsal = dorsal;
        this.isStarter = isStarter;
        this.state = state;
    }

    @Bindable
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
        notifyPropertyChanged(BR.name);
    }

    @Bindable
    public String getPhotoPath() {
        return photoPath;
    }

    public void setPhotoPath(String photoPath) {
        this.photoPath = photoPath;
    }

    @Bindable
    public int getDorsal() {
        return dorsal;
    }

    public void setDorsal(int dorsal) {
        this.dorsal = dorsal;
        notifyPropertyChanged(BR.dorsal);
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public boolean isStarter() {
        return isStarter;
    }

    public void setStarter(boolean starter) {
        isStarter = starter;
    }

    public boolean getIsStarter() {
        return this.isStarter;
    }

    public void setIsStarter(boolean isStarter) {
        this.isStarter = isStarter;
    }

    public Constants.PLAYER_STATES getState() {
        if (this.state == null) this.state = Constants.PLAYER_STATES.AVAILABLE;
        return state;
    }

    public void setState(Constants.PLAYER_STATES state) {
        this.state = state;
    }

    static class PlayerStateConverter implements PropertyConverter<Constants.PLAYER_STATES, String> {
        @Override
        public Constants.PLAYER_STATES convertToEntityProperty(String databaseValue) {
            return Constants.PLAYER_STATES.valueOf(databaseValue);
        }

        @Override
        public String convertToDatabaseValue(Constants.PLAYER_STATES entityProperty) {
            return entityProperty.name();
        }
    }
}
