package com.example.gerardo.miestacionamiento.model;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Gerardo on 12/11/2016.
 */
public class Comuna extends RealmObject {

    @PrimaryKey
    int idComuna;

    String nombreComuna;

    public Comuna() {
    }

    public int getIdComuna() {
        return idComuna;
    }

    public void setIdComuna(int idComuna) {
        this.idComuna = idComuna;
    }

    public String getNombreComuna() {
        return nombreComuna;
    }

    public void setNombreComuna(String nombreComuna) {
        this.nombreComuna = nombreComuna;
    }
}
