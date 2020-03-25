package com.example.haru.fineweather;


import com.example.haru.model_weather.FineWeather;

public interface FineWeatherContract {
    interface View {
        void showFineWeatherResult(FineWeather fineWeather);
        void showLoadError(String message);
        void loadingStart();
        void loadingEnd();
        void reload(double lat, double lng);
    }

public interface UserActionsListener {
        void loadFineWeatherData(); //유저가 로드함
    }
}
