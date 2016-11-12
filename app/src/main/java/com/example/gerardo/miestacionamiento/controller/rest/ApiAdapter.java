package com.example.gerardo.miestacionamiento.controller.rest;

import com.example.gerardo.miestacionamiento.controller.util.ToStringConverterFactory;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Gerardo on 29/09/2016.
 */
public class ApiAdapter {

    private static IApiService API_SERVICE;

    public static IApiService getApiService(){
        if (API_SERVICE==null){

            HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();


            Retrofit adapter = new Retrofit.Builder()
                    .baseUrl(ApiConstants.URL_BASE)
                    .client(client)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            API_SERVICE = adapter.create(IApiService.class);

        }
        return API_SERVICE;
    }

    public static IApiService getApiServiceString(){
        if (API_SERVICE==null){

            HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();


            Retrofit adapter = new Retrofit.Builder()
                    .baseUrl(ApiConstants.URL_BASE)
                    .client(client)
                    .addConverterFactory(new ToStringConverterFactory())
                    .build();

            API_SERVICE = adapter.create(IApiService.class);

        }
        return API_SERVICE;
    }

}
