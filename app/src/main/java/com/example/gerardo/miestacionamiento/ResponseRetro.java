package com.example.gerardo.miestacionamiento;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Gerardo on 02/10/2016.
 */
public class ResponseRetro {

    @SerializedName("msg")
    private String mensaje;

    public ResponseRetro() {
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }
}
