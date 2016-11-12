package com.example.gerardo.miestacionamiento.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Gerardo on 11/11/2016.
 */
public class RegistroFullUsuario {

    @SerializedName("usuario")
    Usuario usuario;
    @SerializedName("estacionamiento")
    Estacionamiento estacionamiento;
    @SerializedName("vehiculo")
    Vehiculo vehiculo;
    @SerializedName("tarjeta")
    Tarjeta tarjeta;


    public RegistroFullUsuario() {
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Estacionamiento getEstacionamiento() {
        return estacionamiento;
    }

    public void setEstacionamiento(Estacionamiento estacionamiento) {
        this.estacionamiento = estacionamiento;
    }

    public Vehiculo getVehiculo() {
        return vehiculo;
    }

    public void setVehiculo(Vehiculo vehiculo) {
        this.vehiculo = vehiculo;
    }

    public Tarjeta getTarjeta() {
        return tarjeta;
    }

    public void setTarjeta(Tarjeta tarjeta) {
        this.tarjeta = tarjeta;
    }

    public class ResponseRegistroFull{

    }


}
