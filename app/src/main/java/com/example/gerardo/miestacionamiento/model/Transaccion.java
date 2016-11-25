package com.example.gerardo.miestacionamiento.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Gerardo on 25/11/2016.
 */
public class Transaccion {

    @SerializedName("idTransaccion")
    Integer idTransaccion;
    //"2016-11-24"
    @SerializedName("fechaTransaccion")
    String fechaTransaccion;
    @SerializedName("idEstado")
    Integer idEstado;
    @SerializedName("rutUsuarioUsuario")
    String rutUsuarioUsuario;
    @SerializedName("rutUsuarioPropietario")
    String rutUsuarioPRopietario;
    @SerializedName("idArriendo")
    Integer idArriendo;

    public Transaccion() {
    }

    public Integer getIdTransaccion() {
        return idTransaccion;
    }

    public void setIdTransaccion(Integer idTransaccion) {
        this.idTransaccion = idTransaccion;
    }

    public String getFechaTransaccion() {
        return fechaTransaccion;
    }

    public void setFechaTransaccion(String fechaTransaccion) {
        this.fechaTransaccion = fechaTransaccion;
    }

    public Integer getIdEstado() {
        return idEstado;
    }

    public void setIdEstado(Integer idEstado) {
        this.idEstado = idEstado;
    }

    public String getRutUsuarioUsuario() {
        return rutUsuarioUsuario;
    }

    public void setRutUsuarioUsuario(String rutUsuarioUsuario) {
        this.rutUsuarioUsuario = rutUsuarioUsuario;
    }

    public String getRutUsuarioPRopietario() {
        return rutUsuarioPRopietario;
    }

    public void setRutUsuarioPRopietario(String rutUsuarioPRopietario) {
        this.rutUsuarioPRopietario = rutUsuarioPRopietario;
    }

    public Integer getIdArriendo() {
        return idArriendo;
    }

    public void setIdArriendo(Integer idArriendo) {
        this.idArriendo = idArriendo;
    }
}
