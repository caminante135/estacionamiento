package com.example.gerardo.miestacionamiento.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Gerardo on 06/11/2016.
 */
public class ResponseAllEstacionamientos {

    @SerializedName("dueno")
    private Usuario usuario;

    @SerializedName("listaEstacionamientos")
    private List<Estacionamiento> estacionamientos;

    public ResponseAllEstacionamientos() {
    }

    public List<Estacionamiento> getEstacionamientos() {
        return estacionamientos;
    }

    public void setEstacionamientos(List<Estacionamiento> estacionamientos) {
        this.estacionamientos = estacionamientos;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
}
