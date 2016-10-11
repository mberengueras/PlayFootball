package com.plusbits.playfootball.models;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.android.databinding.library.baseAdapters.BR;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

/**
 * Created by mberengueras on 06/10/2016.
 */
@Entity
public class Player extends BaseObservable {
    @Id(autoincrement = true)
    private Long id;

    public String name;
    public String photoPath;
    public int dorsal;

    public Player() {

    }

    @Generated(hash = 182076930)
    public Player(Long id, String name, String photoPath, int dorsal) {
        this.id = id;
        this.name = name;
        this.photoPath = photoPath;
        this.dorsal = dorsal;
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
}
