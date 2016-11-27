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
    public static final String PATH_MARCA_VEHICULO = "/marcaVehiculo";
    public static final String PATH_MODELO_VEHICULO = "/modelo";
    public static final String PATH_ARRIENDO = "/arriendo";
    public static final String PATH_EVALUACION = "/evaluacion";

    //Metodos
    public static final String PATH_LOGIN = "/loginFull";
    public static final String PATH_ALL_ESTACIONAMIENTOS = "/selectAllEstAndOwners";
    public static final String PATH_REGISTRAR_USUARIO = "/insertFull";
    public static final String PATH_SELECT_ALL = "/selectAll";
    public static final String PATH_INSERT_TRANSACTION = "/insertAndTransaccion";
    public static final String PATH_SELECT_EVAL = "/selectEvaluacionesByUsuAndEst";
    public static final String PATH_INSERT = "/insert";

    //PARAMETROS
    public static final String PARAM_RUT = "rut";
    public static final String PARAM_CLAVE = "clave";

    //URL'S
    public static final String URL_LOGIN = PATH_MI_EST_WEB+PATH_USUARIO+PATH_LOGIN;
    public static final String URL_GET_ESTACIONAMIENTOS = PATH_MI_EST_WEB+PATH_ESTACIONAMIENTO+PATH_ALL_ESTACIONAMIENTOS;
    public static final String URL_REGISTRAR_USUARIO = PATH_MI_EST_WEB+PATH_USUARIO+PATH_REGISTRAR_USUARIO;
    public static final String URL_GET_MARCAS_VEHICULO = PATH_MI_EST_WEB+ PATH_MARCA_VEHICULO+PATH_SELECT_ALL;
    public static final String URL_GET_MODELO_VEHICULO = PATH_MI_EST_WEB+PATH_MODELO_VEHICULO+PATH_SELECT_ALL;
    public static final String URL_INSERT_TRANSACTION = PATH_MI_EST_WEB+PATH_ARRIENDO+PATH_INSERT_TRANSACTION;
    public static final String URL_SELECT_EVAL_BY_ID = PATH_MI_EST_WEB+PATH_EVALUACION+PATH_SELECT_EVAL;
    public static final String URL_SELECT_ALL_EVAL = PATH_MI_EST_WEB+PATH_EVALUACION+PATH_SELECT_ALL;
    public static final String URL_INSERT_EVALUACION = PATH_MI_EST_WEB+PATH_EVALUACION+PATH_INSERT;
}
