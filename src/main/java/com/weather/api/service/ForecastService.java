package com.weather.api.service;

import com.weather.api.model.RateInfo;
import com.weather.api.model.WeatherInfo;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class ForecastService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ForecastService.class);

    public static void main(String[] args) {
        ForecastService ws = new ForecastService();
        System.out.println( ws.getGoldRateInfo() );
    }

    public WeatherInfo getWeatherInfo() {
        String moviesLinkUrl = String.format("https://weather.com/en-IN/weather/today/l/4ef51d4289943c7792cbe77dee741bff9216f591eed796d7a5d598c38828957d");

        WeatherInfo info = new WeatherInfo();

        try {
            LOGGER.info("weatherUrl: {}", moviesLinkUrl);

            Document doc = Jsoup.connect(moviesLinkUrl).timeout(30 * 1000).get();

            Elements segTemp = doc.select("div#WxuCurrentConditions-main-b3094163-ef75-4558-8d9a-e35e6b9b1034");
            String htmlRawText = segTemp.text();
            LOGGER.info(segTemp.text());

            int locationIndex = htmlRawText.indexOf("Weather");
            String location = htmlRawText.substring(0, locationIndex - 1);
            LOGGER.debug(location);
            info.setLocation(location);

            int asOfIndex = htmlRawText.indexOf("as of");
            int istIndex = htmlRawText.indexOf("IST");

            String asOf = htmlRawText.substring(asOfIndex + 6, istIndex - 1);
            info.setAsOf(asOf);

            String temp = htmlRawText.substring(istIndex + 4, istIndex + 7);
            LOGGER.debug(temp);
            info.setTemperature(temp);

            int condIndex = htmlRawText.indexOf(temp);

            int lowIndex = htmlRawText.indexOf("/");
            String low = htmlRawText.substring(lowIndex - 2, lowIndex);
            String high = htmlRawText.substring(lowIndex + 1, lowIndex + 4);
            LOGGER.debug(low + " " + high);
            info.setHigh(high);
            info.setLow(low);

            String condition = htmlRawText.substring(condIndex + 4, lowIndex - 3);
            LOGGER.debug(condition);
            info.setCurrentCondition(condition);

        } catch (Exception ex) {
            LOGGER.error("Unable to fetch weather info", ex);
        }

        return info;
    }

    public RateInfo getGoldRateInfo() {
        String moviesLinkUrl = String.format("https://www.sktm.in/");

        RateInfo info = new RateInfo();

        try {
            LOGGER.info("weatherUrl: {}", moviesLinkUrl);

            Document doc = Jsoup.connect(moviesLinkUrl).timeout(30 * 1000).get();

            Elements segTemp = doc.select("div.ratemenu");
            //String htmlRawText = "Today's Rate Rs 5,166.00/Gram Gold 22K : Rs 5,166.00/Gram Silver : Rs 74.40/Gram";
            String htmlRawText = segTemp.text();
            LOGGER.info(htmlRawText);

            String goldRate = getRateCard(htmlRawText, "Gold 22K", false);
            String silverRate = getRateCard(htmlRawText, "Silver", true);

            info.setGoldRate22(goldRate);
            info.setSilver(silverRate);

        } catch (Exception ex) {
            LOGGER.error("Unable to fetch gold rate info", ex);
        }

        return info;
    }

    private String getRateCard(String htmlRawText, String type, boolean needDecimal) {
        int rateIndex = htmlRawText.indexOf(type);
        htmlRawText = htmlRawText.substring(rateIndex);
        LOGGER.debug(htmlRawText);
        rateIndex = htmlRawText.indexOf("Rs");
        int gramIndex = htmlRawText.indexOf("/Gram");
        String rate = htmlRawText.substring(rateIndex + 3, gramIndex);
        rate = removeRateFormats(rate, needDecimal);
        return rate;
    }

    private String removeRateFormats(String rateString, boolean needDecimal) {
        LOGGER.debug(rateString);
        rateString = rateString.replaceAll(",", "");

        if (!needDecimal) {
            rateString = rateString.substring(0, rateString.indexOf("."));
        }

        LOGGER.debug(rateString);

        return rateString;
    }

}
