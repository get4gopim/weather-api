package com.weather.api.model;

public class RateInfo {

    private String goldRate22;
    private String goldRate24;
    private String silver;

    public String getGoldRate22() {
        return goldRate22;
    }

    public void setGoldRate22(String goldRate22) {
        this.goldRate22 = goldRate22;
    }

    public String getGoldRate24() {
        return goldRate24;
    }

    public void setGoldRate24(String goldRate24) {
        this.goldRate24 = goldRate24;
    }

    public String getSilver() {
        return silver;
    }

    public void setSilver(String silver) {
        this.silver = silver;
    }

    @Override
    public String toString() {
        return "RateInfo{" +
                "goldRate22='" + goldRate22 + '\'' +
                ", goldRate24='" + goldRate24 + '\'' +
                ", silver='" + silver + '\'' +
                '}';
    }
}
