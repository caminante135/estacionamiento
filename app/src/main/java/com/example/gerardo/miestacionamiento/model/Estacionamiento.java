package com.example.gerardo.miestacionamiento.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Gerardo on 01/11/2016.
 */
public class Estacionamiento {

    @SerializedName("idEstacionamiento")
    public int idEstacionamiento;
    @SerializedName("altura")
    public Double altura;
    @SerializedName("largo")
    public Double largo;
    @SerializedName("ancho")
    public Double ancho;
    @SerializedName("pisoUbicacion")
    public int pisoUbicacion;
    @SerializedName("numeroEst")
    public int numeroEst;
    @SerializedName("idEstado")
    public int idEstado;
    @SerializedName("camaraVigilancia")
    public int camaraVigilancia;
    @SerializedName("idResidencia")
    public int idResidencia;
    @SerializedName("tipoEstacionamiento")
    public int tipoEstacionamiento;
    @SerializedName("direccionEstacionamiento")
    public String direccionEstacionamiento;
    @SerializedName("idComuna")
    public int idComuna;
    @SerializedName("costoHora")
    public int costoHora;
    @SerializedName("latitud")
    public String latitud;
    @SerializedName("longitud")
    public String longitud;

    public Estacionamiento() {
    }

    public int getIdEstacionamiento() {
        return idEstacionamiento;
    }

    public void setIdEstacionamiento(int idEstacionamiento) {
        this.idEstacionamiento = idEstacionamiento;
    }

    public Double getAltura() {
        return altura;
    }

    public void setAltura(Double altura) {
        this.altura = altura;
    }

    public Double getLargo() {
        return largo;
    }

    public void setLargo(Double largo) {
        this.largo = largo;
    }

    public Double getAncho() {
        return ancho;
    }

    public void setAncho(Double ancho) {
        this.ancho = ancho;
    }

    public int getPisoUbicacion() {
        return pisoUbicacion;
    }

    public void setPisoUbicacion(int pisoUbicacion) {
        this.pisoUbicacion = pisoUbicacion;
    }

    public int getNumeroEst() {
        return numeroEst;
    }

    public void setNumeroEst(int numeroEst) {
        this.numeroEst = numeroEst;
    }

    public int getIdEstado() {
        return idEstado;
    }

    public void setIdEstado(int idEstado) {
        this.idEstado = idEstado;
    }

    public int getCamaraVigilancia() {
        return camaraVigilancia;
    }

    public void setCamaraVigilancia(int camaraVigilancia) {
        this.camaraVigilancia = camaraVigilancia;
    }

    public int getIdResidencia() {
        return idResidencia;
    }

    public void setIdResidencia(int idResidencia) {
        this.idResidencia = idResidencia;
    }

    public int getTipoEstacionamiento() {
        return tipoEstacionamiento;
    }

    public void setTipoEstacionamiento(int tipoEstacionamiento) {
        this.tipoEstacionamiento = tipoEstacionamiento;
    }

    public String getDireccionEstacionamiento() {
        return direccionEstacionamiento;
    }

    public void setDireccionEstacionamiento(String direccionEstacionamiento) {
        this.direccionEstacionamiento = direccionEstacionamiento;
    }

    public int getIdComuna() {
        return idComuna;
    }

    public void setIdComuna(int idComuna) {
        this.idComuna = idComuna;
    }

    public int getCostoHora() {
        return costoHora;
    }

    public void setCostoHora(int costoHora) {
        this.costoHora = costoHora;
    }

    public String getLatitud() {
        return latitud;
    }

    public void setLatitud(String latitud) {
        this.latitud = latitud;
    }

    public String getLongitud() {
        return longitud;
    }

    public void setLongitud(String longitud) {
        this.longitud = longitud;
    }
}
