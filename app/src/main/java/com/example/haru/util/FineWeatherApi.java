package com.example.haru.util;



import com.example.haru.model_weather.FineWeather;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;

public interface FineWeatherApi {
    String BASE_URL = "https://api2.sktelecom.com/";

    @Headers("appKey: 2a32caf3-cf48-4ddd-b29a-3f0161ec2ad5")
    @GET("weather/current/minutely?version=1")
    Call<FineWeather> getFineWeather(@Query("lat") double latitude,
                                     @Query("lon") double longitude);


}
