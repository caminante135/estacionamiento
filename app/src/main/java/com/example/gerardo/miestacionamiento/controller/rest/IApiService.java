package com.example.gerardo.miestacionamiento.controller.rest;

import com.example.gerardo.miestacionamiento.model.Evaluacion;
import com.example.gerardo.miestacionamiento.model.FullTransaccionArriendo;
import com.example.gerardo.miestacionamiento.model.Marca;
import com.example.gerardo.miestacionamiento.model.RegistroFullUsuario;
import com.example.gerardo.miestacionamiento.model.ResponseAllEstacionamientos;
import com.example.gerardo.miestacionamiento.model.ResponseLogin;
import com.example.gerardo.miestacionamiento.model.Transaccion;
import com.example.gerardo.miestacionamiento.model.Usuario;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by Gerardo on 29/09/2016.
 */
public interface IApiService {

//    @GET(ApiConstants.URL_LOGIN)
//    Call<ResponseLogin> login(@Query(ApiConstants.PARAM_RUT) String rut,
//                                @Query(ApiConstants.PARAM_CLAVE) String clave);

    @POST(ApiConstants.URL_LOGIN)
    Call<ResponseLogin> login(@Body Usuario usuario);

    @POST(ApiConstants.URL_GET_ESTACIONAMIENTOS)
    Call<List<ResponseAllEstacionamientos>> getEstacionamientos();

    @POST(ApiConstants.URL_REGISTRAR_USUARIO)
    Call<RegistroFullUsuario.ResponseRegistroFull> registrarFullUsuario(@Body RegistroFullUsuario registroFullUsuario);

//    @POST(ApiConstants.URL_GET_MARCAS_VEHICULO)
//    Call<List<Marca>> getMarcasVehiculo();

    @POST(ApiConstants.URL_GET_MARCAS_VEHICULO)
    Call<ResponseBody> getMarcasVehiculo();

    @POST(ApiConstants.URL_GET_MODELO_VEHICULO)
    Call<ResponseBody> getModeloVehiculo();

    @POST(ApiConstants.URL_INSERT_TRANSACTION)
     Call<FullTransaccionArriendo.responseTransaccionArriendo> insertTransaction(@Body FullTransaccionArriendo full);

    @POST(ApiConstants.URL_INSERT_EVALUACION)
    Call<ResponseBody> insertEvaluacion(@Body Evaluacion evaluacion);

    @POST(ApiConstants.URL_SELECT_EVAL_BY_ID)
    Call<ResponseBody> selectEvalById (@Body Evaluacion rutEid);

    @POST(ApiConstants.URL_SELECT_ALL_EVAL)
    Call<ResponseBody> selectallEval();

    @POST(ApiConstants.URL_SELECT_TRANSACCION_ARRIENDO)
    Call<Transaccion.TransaccionArriendo> selectTransaccionArriendoByRut (@Body Transaccion rutUsuarioPropietario);

}
