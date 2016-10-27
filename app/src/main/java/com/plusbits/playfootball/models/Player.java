package com.plusbits.playfootball.models;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.android.databinding.library.baseAdapters.BR;
import com.plusbits.playfootball.utils.Constants;

import org.greenrobot.greendao.annotation.Convert;
import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Unique;
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
    private String observations;
    @Unique
    private int number;
    private boolean isStarter;

    @Convert(converter = PlayerStateConverter.class, columnType = String.class)
    private Constants.PLAYER_STATES state;

    public Player() {

    }

    @Generated(hash = 376343751)
    public Player(Long id, String name, String photoPath, String observations, int number, boolean isStarter,
            Constants.PLAYER_STATES state) {
        this.id = id;
        this.name = name;
        this.photoPath = photoPath;
        this.observations = observations;
        this.number = number;
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
    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
        notifyPropertyChanged(BR.number);
    }

    public String getObservations() {
        return observations;
    }

    public void setObservations(String observations) {
        this.observations = observations;
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
