package com.example.paprika.API;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitService {

    public static final String URL_001 = "http://192.168.1.35:9090";

    public static Retrofit getRetrofit(){
        Retrofit retrofit = new Retrofit.Builder().baseUrl(URL_001).addConverterFactory(GsonConverterFactory.create()).build();
        return retrofit;
    }

}
