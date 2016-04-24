package com.example.musicianslist.utils;

import com.example.musicianslist.models.Artist;

import java.util.List;

import retrofit.Call;
import retrofit.http.GET;
import retrofit.http.Url;

/**
 * Created by Murager on 17.04.2016.
 */
public interface ApiInterface {

    @GET
    Call<List<Artist>> getAllMusicianList(@Url String url);

}
