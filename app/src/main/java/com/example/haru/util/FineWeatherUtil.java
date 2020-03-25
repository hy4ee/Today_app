package com.example.haru.util;



import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class FineWeatherUtil {
    private FineWeatherApi mGetApi;

    public FineWeatherUtil() {
        Retrofit mRetrofit = new Retrofit.Builder()
                .baseUrl(FineWeatherApi.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        mGetApi = mRetrofit.create(FineWeatherApi.class);
    }

    public FineWeatherApi getApi() {
        return mGetApi;
    }
}
