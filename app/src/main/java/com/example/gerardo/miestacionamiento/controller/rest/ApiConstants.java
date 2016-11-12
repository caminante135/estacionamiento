package com.example.gerardo.miestacionamiento.controller.rest;

/**
 * Created by Gerardo on 29/09/2016.
 */
public class ApiConstants {

    public static final String IP_PUERTO = "186.64.123.8:8080";
    public static final String URL_BASE = "http://"+IP_PUERTO;

    //PATHS
    public static final String PATH_MI_EST_WEB = "/mi-estacionamiento-web";
    public static final String PATH_USUARIO = "/usuario";
    public static final String PATH_ESTACIONAMIENTO = "/estacionamiento";

    //Metodos
    public static final String PATH_LOGIN = "/loginFull";
    public static final String PATH_ALL_ESTACIONAMIENTOS = "/selectAllEstAndOwners";
    public static final String PATH_REGISTRAR_USUARIO = "/insertFull";

    //PARAMETROS
    public static final String PARAM_RUT = "rut";
    public static final String PARAM_CLAVE = "clave";

    //URL'S
    public static final String URL_LOGIN = PATH_MI_EST_WEB+PATH_USUARIO+PATH_LOGIN;
    public static final String URL_GET_ESTACIONAMIENTOS = PATH_MI_EST_WEB+PATH_ESTACIONAMIENTO+PATH_ALL_ESTACIONAMIENTOS;
    public static final String URL_REGISTRAR_USUARIO = PATH_MI_EST_WEB+PATH_USUARIO+PATH_REGISTRAR_USUARIO;


}
