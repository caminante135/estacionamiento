package com.example.gerardo.miestacionamiento.controller.util;

/**
 * Created by Gerardo on 02/10/2016.
 */
public class GlobalConstant {

    /**
     * PREFERENCIAS
     */
    public static final String PREFS_NAME = "Pref_estacionamiento";

    //PREFERENCIAS USUARIO
    public static final String PREFS_RUT = "rut";
    public static final String PREFS_NOMBRE = "nombre";
    public static final String PREFS_APELLIDO_P = "apellidop";
    public static final String PREFS_APELLIDO_M = "apellidom";
    public static final String PREFS_CORREO = "correo";
    public static final String PREFS_TELEFONO = "telefono";
    public static final String PREFS_CLAVE = "clave";
    public static final String PREFS_IDESTADO = "idEstado";
    public static final String PREFS_IDROL = "idRol";

    public static final String PREFS_AUTOLOGIN = "autologin";

    //PREFERENCIAS ESTACIONAMIENTO
    public static final String PREFS_JSON_ESTACIONAMIENTOS = "jsonEstacionamientos";

    //PREFERENCIAS VEHICULO
    public static final String PREFS_JSON_VEHICULOS = "jsonVehiculos";

    //PREFERENCIAS TARJETA
    public static final String PREFS_JSON_TARJETAS = "jsonTarjetas";

    public static final String PREFS_LATITUD = "latitud";
    public static final String PREFS_LONGITUD = "longitud";

    /**
     * BUNDLES
     */
    public static final String BUNDLE_USUARIO = "usuario";
    public static final String BUNDLE_VEHICULO = "vehiculo";
    public static final String BUNDLE_ESTACIO = "estacionamiento";

    /**
     * RESPONSES
     */
    public static final int RESPONSE_CONNECTION_ERROR = -1;

    //RESPONSES LOGIN
    public static final int RESPONSE_LOGIN_CORRECT = 1;
    public static final int RESPONSE_LOGIN_INCORRECT = 0;

    /**
     * VALORES
     */
    public static final int TIPO_DUEÑO = 1;
    public static final int TIPO_CLIENTE = 2;
    public static final int TIPO_ADMINISTRADOR = 3;
    public static final int TIPO_CONSULTOR = 4;

}
