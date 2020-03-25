package com.example.haru.data;


import com.example.haru.model_weather.FineWeather;

import retrofit2.Callback;

public interface FineWeatherRepository {
    boolean isAvailable();
    void getFindWeatherData(Callback<FineWeather> callback);
}
