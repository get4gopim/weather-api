package com.weather.api.model;

public class RateInfo {

    private String goldRate22;
    private String goldRate24;
    private String silver;
    private String lastUpdateTime;
    private String date;

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

    public String getLastUpdateTime() {
        return lastUpdateTime;
    }

    public void setLastUpdateTime(String lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "RateInfo{" +
                "goldRate22='" + goldRate22 + '\'' +
                ", goldRate24='" + goldRate24 + '\'' +
                ", silver='" + silver + '\'' +
                ", lastUpdateTime='" + lastUpdateTime + '\'' +
                ", date='" + date + '\'' +
                '}';
    }
}
