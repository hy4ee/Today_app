package com.example.haru.fineweather;

import com.example.haru.data.FineWeatherRepository;
import com.example.haru.model_weather.FineWeather;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class FineWeatherPresenter implements FineWeatherContract.UserActionsListener {
    private final FineWeatherRepository mRepository;
    private final FineWeatherContract.View mView;

    public FineWeatherPresenter(FineWeatherRepository repository, FineWeatherContract.View view) {
        this.mRepository = repository;
        this.mView = view;
    }

    public void loadFineWeatherData() {
        if(mRepository.isAvailable()) {
            mView.loadingStart();
            mRepository.getFindWeatherData(new Callback<FineWeather>() {
                public void onResponse(Call<FineWeather> call, Response<FineWeather> response) {
                    mView.showFineWeatherResult(response.body());
                    mView.loadingEnd();
                }

                public void onFailure(Call<FineWeather> call, Throwable t) {
                    mView.showLoadError(t.getLocalizedMessage());
                    mView.loadingEnd();
                }
            });
        }
    }
}
