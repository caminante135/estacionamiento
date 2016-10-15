package com.example.gerardo.miestacionamiento.rest;

import com.example.gerardo.miestacionamiento.model.ResponseLogin;
import com.example.gerardo.miestacionamiento.model.Usuario;

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

    @POST(ApiConstants.URL_LOGIN2)
    Call<ResponseLogin> login(@Body Usuario usuario);


}
