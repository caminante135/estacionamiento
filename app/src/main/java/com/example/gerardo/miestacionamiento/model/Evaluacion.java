package com.example.gerardo.miestacionamiento.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Gerardo on 25/11/2016.
 */
public class Evaluacion {

    @SerializedName("idEvaluacion")
    Integer idEvaluacion;
    @SerializedName("comentario")
    String comentario;
    @SerializedName("calificacion")
    Integer calificacion;
    @SerializedName("rutCalificador")
    String rutCalificador;
    @SerializedName("rutEvaluado")
    String rutEvaluado;
    @SerializedName("idEstacionamiento")
    Integer idEstacionamiento;

    public Evaluacion() {
    }

    public Integer getIdEvaluacion() {
        return idEvaluacion;
    }

    public void setIdEvaluacion(Integer idEvaluacion) {
        this.idEvaluacion = idEvaluacion;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public Integer getCalificacion() {
        return calificacion;
    }

    public void setCalificacion(Integer calificacion) {
        this.calificacion = calificacion;
    }

    public String getRutCalificador() {
        return rutCalificador;
    }

    public void setRutCalificador(String rutCalificador) {
        this.rutCalificador = rutCalificador;
    }

    public String getRutEvaluado() {
        return rutEvaluado;
    }

    public void setRutEvaluado(String rutEvaluado) {
        this.rutEvaluado = rutEvaluado;
    }

    public Integer getIdEstacionamiento() {
        return idEstacionamiento;
    }

    public void setIdEstacionamiento(Integer idEstacionamiento) {
        this.idEstacionamiento = idEstacionamiento;
    }
}
