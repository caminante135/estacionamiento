package com.example.gerardo.miestacionamiento.model;

import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**

 * @author Gerardo Mascayano

 * @version 0.8

 * 14/10/2016.

 * Clase Usuario se utiliza para modelar el envio y la recepcion de datos con respecto a la tabla
 * Usuario de la Base de Datos

 */
public class Usuario extends RealmObject{


//    private String idUsuario;
    @PrimaryKey
    @SerializedName("rutUsuario")
    private String rutUsuario;
    @SerializedName("nombre")
    private String nombre;
    @SerializedName("apellidoPaterno")
    private String apellidoPaterno;
    @SerializedName("apellidoMaterno")
    private String apellidoMaterno;
    @SerializedName("correoUsuario")
    private String correoUsuario;
    @SerializedName("fonoUsuario")
    private int fonoUsuario;
    @SerializedName("claveUsuario")
    private String claveUsuario;
    @SerializedName("idRol")
    private int idRol;
    @SerializedName("idEstado")
    private int idEstado;

    public Usuario() {
    }

    public Usuario(String correo, String contraseña) {
        this.correoUsuario = correo;
        this.claveUsuario = contraseña;
    }

//    public String getIdUsuario() {
//        return idUsuario;
//    }
//
//    public void setIdUsuario(String idUsuario) {
//        this.idUsuario = idUsuario;
//    }

    public String getRut() {
        return rutUsuario;
    }

    public void setRut(String rut) {
        this.rutUsuario = rut;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidoPaterno() {
        return apellidoPaterno;
    }

    public void setApellidoPaterno(String apellidoPaterno) {
        this.apellidoPaterno = apellidoPaterno;
    }

    public String getApellidoMaterno() {
        return apellidoMaterno;
    }

    public void setApellidoMaterno(String apellidoMaterno) {
        this.apellidoMaterno = apellidoMaterno;
    }

    public String getCorreo() {
        return correoUsuario;
    }

    public void setCorreo(String correo) {
        this.correoUsuario = correo;
    }

    public int getTelefono() {
        return fonoUsuario;
    }

    public void setTelefono(int telefono) {
        this.fonoUsuario = telefono;
    }

    public String getContraseña() {
        return claveUsuario;
    }

    public void setContraseña(String contraseña) {
        this.claveUsuario = contraseña;
    }

    public int getTipoUsuario() {
        return idRol;
    }

    public void setTipoUsuario(int tipoUsuario) {
        this.idRol = tipoUsuario;
    }

    public int getEstado() {
        return idEstado;
    }

    public void setEstado(int estado) {
        this.idEstado = estado;
    }
}
