package com.weather.api.model;

import java.io.Serializable;

public class WeatherInfo implements Serializable {

    private String temperature;
    private String low;
    private String high;
    private String asOf;
    private String currentCondition;
    private String location;
    private String preciption;

    public String getTemperature() {
        return temperature;
    }

    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }

    public String getLow() {
        return low;
    }

    public void setLow(String low) {
        this.low = low;
    }

    public String getHigh() {
        return high;
    }

    public void setHigh(String high) {
        this.high = high;
    }

    public String getAsOf() {
        return asOf;
    }

    public void setAsOf(String asOf) {
        this.asOf = asOf;
    }

    public String getCurrentCondition() {
        return currentCondition;
    }

    public void setCurrentCondition(String currentCondition) {
        this.currentCondition = currentCondition;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getPreciption() {
        return preciption;
    }

    public void setPreciption(String preciption) {
        this.preciption = preciption;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("WeatherInfo{");
        sb.append("temperature='").append(temperature).append('\'');
        sb.append(", low='").append(low).append('\'');
        sb.append(", high='").append(high).append('\'');
        sb.append(", asOf='").append(asOf).append('\'');
        sb.append(", currentCondition='").append(currentCondition).append('\'');
        sb.append(", location='").append(location).append('\'');
        sb.append(", preciption='").append(preciption).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
