package com.example.musicianslist.utils;

import android.content.Context;

import com.example.musicianslist.models.Artist;
import com.squareup.okhttp.Cache;
import com.squareup.okhttp.Interceptor;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.File;
import java.io.IOException;
import java.util.List;

import retrofit.Call;
import retrofit.GsonConverterFactory;
import retrofit.Retrofit;
import retrofit.http.GET;
import retrofit.http.Url;

/**
 * Created by Murager on 24.04.2016.
 */
public class RestClient {

    private static final String BASE_URL = "http://cache-kz01.cdn.yandex.net/download.cdn.yandex.net/mobilization-2016/artists.json";

    private ApiInterface apiInterface;

    public RestClient() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        apiInterface = retrofit.create(ApiInterface.class);
    }

    public ApiInterface getApiInterface() {
        return apiInterface;
    }

}
