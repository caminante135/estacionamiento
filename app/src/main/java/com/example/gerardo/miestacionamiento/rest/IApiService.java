package com.example.gerardo.miestacionamiento.rest;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Gerardo on 29/09/2016.
 */
public interface IApiService {

    @GET(ApiConstants.URL_LOGIN)
    Call<Response<?>> login(@Query(ApiConstants.PARAM_RUT) String rut,
                            @Query(ApiConstants.PARAM_CLAVE) String clave);


}
