package com.example.taheraassignment3.model;

public class Weather {


    public CurrentWeather current;

    @Override
    public String toString() {
        return "Weather{" +

                ", current=" + current +
                '}';
    }


    public CurrentWeather getCurrent() {
        return current;
    }

    public void setCurrent(CurrentWeather current) {
        this.current = current;
    }
}
