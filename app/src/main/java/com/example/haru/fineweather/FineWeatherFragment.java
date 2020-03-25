package com.example.haru.fineweather;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.haru.MainActivity;
import com.example.haru.R;
import com.example.haru.data.FineWeatherRepository;
import com.example.haru.data.LocationFineWeatherRepository;
import com.example.haru.model_weather.FineWeather;

public class FineWeatherFragment extends Fragment implements FineWeatherContract.View {
    private TextView mLocationTextView;
    private TextView mTemperatureTextView;
    private TextView mWeatherTextView;
    private TextView mMaxTemperatureTextView;
    private TextView mMinTemperatureTextView;
    private FineWeatherPresenter mPresenter;
    private FineWeatherRepository mRepository;

    private SwipeRefreshLayout mSwipeRefreshLayout;

    public static FineWeatherFragment newInstance(double lat, double lng) {
        Bundle args = new Bundle();
        args.putDouble("lat", lat);
        args.putDouble("lng", lng);
        FineWeatherFragment fragment = new FineWeatherFragment();
        fragment.setArguments(args);
        return fragment;
    }
    public FineWeatherFragment() {
        // 반드시 필요함
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view;
            view = inflater.inflate(R.layout.activity_main, container, false);
            mLocationTextView = (TextView) view.findViewById(R.id.text_location);
            mWeatherTextView = (TextView) view.findViewById(R.id.text_weather);
            mTemperatureTextView = (TextView) view.findViewById(R.id.text_temperature);
            mMaxTemperatureTextView = (TextView) view.findViewById(R.id.text_max_temperature);
            mMinTemperatureTextView = (TextView) view.findViewById(R.id.text_min_temperature);

            if (savedInstanceState != null) {
                //복원
                mLocationTextView.setText(savedInstanceState.getString("location"));
                mWeatherTextView.setText(savedInstanceState.getString("weather"));
                mTemperatureTextView.setText(savedInstanceState.getString("tc"));
                mMaxTemperatureTextView.setText(savedInstanceState.getString("tmax"));
                mMinTemperatureTextView.setText(savedInstanceState.getString("tmin"));
            }
            mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.activity_main);
            mSwipeRefreshLayout.setColorSchemeColors(Color.RED, Color.YELLOW, Color.GREEN, Color.BLUE);
            mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    mPresenter.loadFineWeatherData();
                }
            });
            return view;
    }

    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (getArguments() != null) {
            double lat = getArguments().getDouble("lat");
            double lng = getArguments().getDouble("lng");
            mRepository = new LocationFineWeatherRepository(lat, lng);
        }
        else {
            mRepository = new LocationFineWeatherRepository();
            ((MainActivity)getActivity()).getLastKnownLocation();

        }
        mPresenter = new FineWeatherPresenter(mRepository, this);
        mPresenter.loadFineWeatherData();
    }

    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("location", mLocationTextView.getText().toString());
        outState.putString("weather", mWeatherTextView.getText().toString());
        outState.putString("tc", mTemperatureTextView.getText().toString());
        outState.putString("tmax", mMaxTemperatureTextView.getText().toString());
        outState.putString("tmin", mMinTemperatureTextView.getText().toString());
    }

    @Override
    public void showFineWeatherResult(FineWeather fineWeather) {
        mLocationTextView.setText(fineWeather.getWeather().getMinutely().get(0).getStation().getName());
        mWeatherTextView.setText(fineWeather.getWeather().getMinutely().get(0).getSky().getName());
        mTemperatureTextView.setText(fineWeather.getWeather().getMinutely().get(0).getTemperature().getTc() + "°");
        mMaxTemperatureTextView.setText(fineWeather.getWeather().getMinutely().get(0).getTemperature().getTmax() + "°");
        mMinTemperatureTextView.setText(fineWeather.getWeather().getMinutely().get(0).getTemperature().getTmin() + "°");
    }

    @Override
    public void showLoadError(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void loadingStart() {
        mSwipeRefreshLayout.setRefreshing(true);
    }

    @Override
    public void loadingEnd() {
        mSwipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void reload(double lat, double lng) {
        mRepository = new LocationFineWeatherRepository(lat, lng);
        mPresenter = new FineWeatherPresenter(mRepository,this);
        mPresenter.loadFineWeatherData();
    }
}
