package com.example.gerardo.miestacionamiento.model;


import com.google.gson.annotations.SerializedName;

/**
 * Created by Gerardo on 25/11/2016.
 */
public class FullTransaccionArriendo {

    @SerializedName("arriendo")
    Arriendo arriendo;

    @SerializedName("transaccion")
    Transaccion transaccion;

    public FullTransaccionArriendo() {
    }

    public Arriendo getArriendo() {
        return arriendo;
    }

    public void setArriendo(Arriendo arriendo) {
        this.arriendo = arriendo;
    }

    public Transaccion getTransaccion() {
        return transaccion;
    }

    public void setTransaccion(Transaccion transaccion) {
        this.transaccion = transaccion;
    }
}
