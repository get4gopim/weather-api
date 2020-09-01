package com.weather.api.service;

import com.weather.api.model.FuelRateInfo;
import com.weather.api.model.RateInfo;
import com.weather.api.model.WeatherInfo;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class ForecastService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ForecastService.class);

    public static void main(String[] args) {
        ForecastService ws = new ForecastService();
        System.out.println( ws.getWeatherInfo() );
    }

    public FuelRateInfo getLiveFuelRate() {
        String moviesLinkUrl = String.format("https://www.livechennai.com/petrol_price.asp");

        FuelRateInfo info = new FuelRateInfo();

        try {
            LOGGER.info("Url: {}", moviesLinkUrl);

            Document doc = Jsoup.connect(moviesLinkUrl).timeout(30 * 1000).get();

            Element table = doc.select("table#BC_GridView1").get(0);

            // LOGGER.debug(table.text());
            /*String lastUpdatedTime = ele.select("p.mob-cont ").text();
            if (!StringUtils.isEmpty(lastUpdatedTime) &&
                    lastUpdatedTime.indexOf(":") > 0) {
                lastUpdatedTime = lastUpdatedTime.substring(lastUpdatedTime.indexOf(":")+1);
            }
            LOGGER.debug(lastUpdatedTime);*/

            Elements rows = table.select("tr");
            Element dateRow = rows.get(1);

            String fuelDate = dateRow.child(0).text();
            String petrolPrice = dateRow.child(1).text();
            LOGGER.debug(fuelDate);
            LOGGER.debug(petrolPrice);

            table = table = doc.select("table#BC_GridView1").get(1);

            rows = table.select("tr");
            dateRow = rows.get(1);
            fuelDate = dateRow.child(0).text();
            String dieselPrice = dateRow.child(1).text();
            LOGGER.debug(fuelDate);
            LOGGER.debug(dieselPrice);

            info.setPetrolPrice(petrolPrice);
            info.setDieselPrice(dieselPrice);
            info.setLastUpdatedTime("6.00 AM");
            info.setDate(fuelDate);

            LOGGER.info(info.toString());

        } catch (Exception ex) {
            LOGGER.error("Unable to fetch gold rate info", ex);
        }

        return info;
    }

    public RateInfo getLiveGoldRate() {
        String moviesLinkUrl = String.format("http://www.livechennai.com/gold_silverrate.asp");

        RateInfo info = new RateInfo();

        try {
            LOGGER.info("Url: {}", moviesLinkUrl);

            Document doc = Jsoup.connect(moviesLinkUrl).timeout(30 * 1000).get();

            Element ele = doc.select("div.col-sm-8").get(0);
            LOGGER.debug(ele.select("p.mob-cont ").text());
            String lastUpdatedTime = ele.select("p.mob-cont ").text();
            if (!StringUtils.isEmpty(lastUpdatedTime) &&
                    lastUpdatedTime.indexOf(":") > 0) {
                lastUpdatedTime = lastUpdatedTime.substring(lastUpdatedTime.indexOf(":")+1);
            }
            LOGGER.debug(lastUpdatedTime);
            Elements table = ele.select("table.table-price");

            Elements rows = table.select("tr");
            Element dateRow = rows.get(2);

            String goldDate = dateRow.child(0).text();
            String goldPrice24 = dateRow.child(1).text();
            String goldPrice22 = dateRow.child(3).text();
            LOGGER.debug(goldDate);
            LOGGER.debug(goldPrice22);

            ele = doc.select("div.col-sm-8").get(1);
            table = ele.select("table.table-price");

            rows = table.select("tr");
            dateRow = rows.get(1);
            String silverDate = dateRow.child(0).text();
            String silverPrice = dateRow.child(1).text();
            LOGGER.debug(silverDate);
            LOGGER.debug(silverPrice);

            info.setGoldRate22(goldPrice22);
            info.setGoldRate24(goldPrice24);
            info.setSilver(silverPrice);
            info.setLastUpdateTime(lastUpdatedTime);
            info.setDate(goldDate);

            LOGGER.info(info.toString());

        } catch (Exception ex) {
            LOGGER.error("Unable to fetch gold rate info", ex);
        }

        return info;
    }

    public WeatherInfo getWeatherInfo() {
        String moviesLinkUrl = String.format("https://weather.com/en-IN/weather/today/l/4ef51d4289943c7792cbe77dee741bff9216f591eed796d7a5d598c38828957d");

        WeatherInfo info = new WeatherInfo();

        try {
            LOGGER.info("weatherUrl: {}", moviesLinkUrl);

            Document doc = Jsoup.connect(moviesLinkUrl).timeout(30 * 1000).get();

            Elements segTemp = doc.select("div#WxuCurrentConditions-main-b3094163-ef75-4558-8d9a-e35e6b9b1034");
            String htmlRawText = segTemp.text();

            Elements locTag = segTemp.select("h1._-_-node_modules--wxu-components-src-organism-CurrentConditions-CurrentConditions--location--1Ayv3");
            LOGGER.debug(locTag.text());
            String location = locTag.text();
            int locationIndex = location.indexOf(" Weather");
            location = location.substring(0, locationIndex).trim();
            LOGGER.debug(location);
            info.setLocation(location);

            Elements asOfTag = segTemp.select("div._-_-node_modules--wxu-components-src-organism-CurrentConditions-CurrentConditions--timestamp--1SWy5");
            LOGGER.debug(asOfTag.text());
            String asOf = asOfTag.text();
            int asOfIndex = asOf.indexOf("as of");
            int istIndex = asOf.indexOf(" IST");

            asOf = asOf.substring(asOfIndex + 6, istIndex).trim();
            info.setAsOf(asOf);
            LOGGER.debug(asOf);


            Elements tempTag = segTemp.select("span._-_-node_modules--wxu-components-src-organism-CurrentConditions-CurrentConditions--tempValue--3KcTQ");
            LOGGER.debug(tempTag.text());

            String temp = tempTag.text();
            if (temp.length() > 2) {
                temp = temp.substring(0, 2).trim();
            }
            LOGGER.debug(temp);
            info.setTemperature(temp.trim());


            Elements condTag = segTemp.select("div._-_-node_modules--wxu-components-src-organism-CurrentConditions-CurrentConditions--phraseValue--2xXSr");
            LOGGER.debug(condTag.text());
            String condition = condTag.text().trim();
            info.setCurrentCondition(condition);
            LOGGER.debug(condition);


            Elements hlTag = segTemp.select("div._-_-node_modules--wxu-components-src-organism-CurrentConditions-CurrentConditions--tempHiLoValue--A4RQE");
            LOGGER.debug(hlTag.text());
            String highLow = hlTag.text();

            int idx = highLow.indexOf("/");
            String high = temp;
            String low = temp;
            if (idx > 0) {
                high = highLow.substring(0, idx);
                if (high.equals("--")) {
                    high = temp;
                }
                low = highLow.substring(idx+1, highLow.length());
            }
            LOGGER.debug(low + " " + high);

            info.setHigh(high.trim());
            info.setLow(low.trim());

            Elements precipTag = segTemp.select("div._-_-node_modules--wxu-components-src-organism-CurrentConditions-CurrentConditions--precipValue--RBVJT");
            String preciption = "";
            if (precipTag != null) {
                LOGGER.debug(precipTag.text());
                preciption = precipTag.text();
            }
            info.setPreciption(preciption);

            LOGGER.info(info.toString());

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
