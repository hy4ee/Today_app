package com.example.haru.data;

import com.example.haru.model_weather.FineWeather;
import com.example.haru.util.FineWeatherUtil;

import retrofit2.Callback;

public class LocationFineWeatherRepository implements FineWeatherRepository {
    private FineWeatherUtil mFineWeatherUtil;
    private double mLatitude;
    private double mLongitude;

    public LocationFineWeatherRepository() {
        mFineWeatherUtil = new FineWeatherUtil();
    }

    public LocationFineWeatherRepository(double lat, double lng) {
        this();
        this.mLatitude = lat;
        this.mLongitude = lng;
    }

    public boolean isAvailable() {
        if(mLatitude != 0.0 && mLongitude != 0.0) {
            return true;
        }
        return false;
    }

    public void getFindWeatherData(Callback<FineWeather> callback) {
        mFineWeatherUtil.getApi().getFineWeather(mLatitude,mLongitude)
                .enqueue(callback);
    }
}
