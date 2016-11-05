package com.example.gerardo.miestacionamiento.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Gerardo on 02/10/2016.
 */
public class ResponseLogin {

    @SerializedName("msg")
    private String mensaje;

    @SerializedName("result")
    private Usuario usuario;

    @SerializedName("listaEstacionamientos")
    private List<Estacionamiento> estacionamientos;

    @SerializedName("listaVehiculos")
    private List<Vehiculo> vehiculos;

    @SerializedName("listaTarjetas")
    private List<Tarjeta> tarjetas;

    public ResponseLogin() {
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public List<Estacionamiento> getEstacionamientos() {
        return estacionamientos;
    }

    public void setEstacionamientos(List<Estacionamiento> estacionamientos) {
        this.estacionamientos = estacionamientos;
    }

    public List<Vehiculo> getVehiculos() {
        return vehiculos;
    }

    public void setVehiculos(List<Vehiculo> vehiculos) {
        this.vehiculos = vehiculos;
    }

    public List<Tarjeta> getTarjetas() {
        return tarjetas;
    }

    public void setTarjetas(List<Tarjeta> tarjetas) {
        this.tarjetas = tarjetas;
    }
}
