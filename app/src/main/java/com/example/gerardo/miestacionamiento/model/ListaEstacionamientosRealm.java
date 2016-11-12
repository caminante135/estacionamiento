package com.example.gerardo.miestacionamiento.model;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Gerardo on 07/11/2016.
 */
public class ListaEstacionamientosRealm extends RealmObject {

    @PrimaryKey
    private String idR;

    private Usuario usuario;
    private RealmList<Estacionamiento> estacionamientos;

    public ListaEstacionamientosRealm() {
    }

    public String getIdR() {
        return idR;
    }

    public void setIdR(String idR) {
        this.idR = idR;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public RealmList<Estacionamiento> getEstacionamientos() {
        return estacionamientos;
    }

    public void setEstacionamientos(RealmList<Estacionamiento> estacionamientos) {
        this.estacionamientos = estacionamientos;
    }
}
