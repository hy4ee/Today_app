package com.example.haru.model_weather;

import java.util.List;

public class Weather {
    private List<Minutely> minutely;

    public List<Minutely> getMinutely() {
        return minutely;
    }

    public void setMinutely(List<Minutely> minutely) {
        this.minutely = minutely;
    }
}
