package com.example.gerardo.miestacionamiento.rest;

/**
 * Created by Gerardo on 29/09/2016.
 */
public class ApiConstants {

    public static final String IP_PUERTO = "186.64.123.8:8080";
    public static final String URL_BASE = "http://"+IP_PUERTO;

    //PATHS
    public static final String PATH_MI_EST_WEB = "/mi-estacionamiento-web";
    public static final String PATH_SEARCH = "/search";
    public static final String PATH_API = "/api";
    public static final String PATH_GET_USER = "/getSearchResult2";

    //PARAMETROS
    public static final String PARAM_RUT = "rut";
    public static final String PARAM_CLAVE = "clave";

    public static final String URL_LOGIN = PATH_MI_EST_WEB+PATH_SEARCH+PATH_API+PATH_GET_USER;

}
