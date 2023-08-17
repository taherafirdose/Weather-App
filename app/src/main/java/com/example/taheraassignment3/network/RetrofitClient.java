package com.example.taheraassignment3.network;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
    private static RetrofitClient instance = null;
    private API api;

    public API getApi() {
        return api;
    }

    private RetrofitClient(){
        Gson gson = new GsonBuilder().create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(api.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        api = retrofit.create(API.class);
    }

    //singleton pattern
    public static RetrofitClient getInstance() {
        if (instance == null){
            instance = new RetrofitClient();
        }
        return instance;
    }
}
