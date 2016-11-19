package com.example.gerardo.miestacionamiento.model;

import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Gerardo on 05/11/2016.
 */
public class Vehiculo extends RealmObject {

    @SerializedName("patente")
    @PrimaryKey
    private String patente;
    @SerializedName("idMarca")
    private String marca;
    private String color;
    @SerializedName("idTipoVehiculo")
    private int tipoVehiculo;
    @SerializedName("rutUsuario")
    private String rutUsuario;
    @SerializedName("rutPropietario")
    private String rutPropietario;


    public Vehiculo() {
    }

    public String getPatente() {
        return patente;
    }

    public void setPatente(String patente) {
        this.patente = patente;
    }

    public String getIdMarca() {
        return marca;
    }

    public void setIdMarca(String marca) {
        this.marca = marca;
    }


    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public int getTipoVehiculo() {
        return tipoVehiculo;
    }

    public void setTipoVehiculo(int tipoVehiculo) {
        this.tipoVehiculo = tipoVehiculo;
    }

    public String getRutUsuario() {
        return rutUsuario;
    }

    public void setRutUsuario(String rutUsuario) {
        this.rutUsuario = rutUsuario;
    }

    public String getRutPropietario() {
        return rutPropietario;
    }

    public void setRutPropietario(String rutPropietario) {
        this.rutPropietario = rutPropietario;
    }
}
