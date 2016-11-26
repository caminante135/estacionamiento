package com.example.gerardo.miestacionamiento.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Gerardo on 24/11/2016.
 */
public class Arriendo {

    @SerializedName("idArriendo")
    private Object idArriendo;
    @SerializedName("horasArrendadas")
    private Integer horasArrendadas;
    @SerializedName("idVehiculo")
    private Integer idVehiculo;
    @SerializedName("idEstacionamiento")
    private Integer idEstacionamiento;
    @SerializedName("idTipoArriendo")
    private Integer idTipoArriendo;
    @SerializedName("idEstado")
    private Integer idEstado;
    @SerializedName("costoHora")
    private Integer costoHora;
    @SerializedName("fechaInicio")
    private String fechaInicio;
    @SerializedName("fechaTermino")
    private String fechaTermino;

    public Arriendo() {
    }

    public Integer getIdArriendo() {
        return (Integer) idArriendo;
    }

    public void setIdArriendo(Object idArriendo) {
        this.idArriendo = idArriendo;
    }

    public Integer getHorasArrendadas() {
        return horasArrendadas;
    }

    public void setHorasArrendadas(Integer horasArrendadas) {
        this.horasArrendadas = horasArrendadas;
    }

    public Integer getIdVehiculo() {
        return idVehiculo;
    }

    public void setIdVehiculo(Integer idVehiculo) {
        this.idVehiculo = idVehiculo;
    }

    public Integer getIdEstacionamiento() {
        return idEstacionamiento;
    }

    public void setIdEstacionamiento(Integer idEstacionamiento) {
        this.idEstacionamiento = idEstacionamiento;
    }

    public Integer getIdTipoArriendo() {
        return idTipoArriendo;
    }

    public void setIdTipoArriendo(Integer idTipoArriendo) {
        this.idTipoArriendo = idTipoArriendo;
    }

    public Integer getIdEstado() {
        return idEstado;
    }

    public void setIdEstado(Integer idEstado) {
        this.idEstado = idEstado;
    }

    public Integer getCostoHora() {
        return costoHora;
    }

    public void setCostoHora(Integer costoHora) {
        this.costoHora = costoHora;
    }

    public String getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(String fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public String getFechaTermino() {
        return fechaTermino;
    }

    public void setFechaTermino(String fechaTermino) {
        this.fechaTermino = fechaTermino;
    }
}
