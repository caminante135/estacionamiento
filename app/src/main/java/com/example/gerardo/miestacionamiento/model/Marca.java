package com.example.gerardo.miestacionamiento.model;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Gerardo on 18/11/2016.
 */
public class Marca extends RealmObject {

    @PrimaryKey
    Integer idMarca;

    String nombre;
    Integer idModelo;

    public Marca() {
    }

    public Integer getIdMarca() {
        return idMarca;
    }

    public void setIdMarca(Integer idMarca) {
        this.idMarca = idMarca;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Integer getIdModelo() {
        return idModelo;
    }

    public void setIdModelo(Integer idModelo) {
        this.idModelo = idModelo;
    }
}
