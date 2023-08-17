package com.example.taheraassignment3.network;


import com.example.taheraassignment3.model.Weather;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface API {
    //    base URL must ends with /
    //base URL must use HTTP Secured
    String BASE_URL = "https://api.weatherapi.com/v1/";


    @GET("./current.json")
    Call<Weather> retrieveWeatherInfo(@Query("key") String key, @Query("q") String location);

}















