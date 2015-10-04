package com.jeiel.jweather.util;

import android.text.TextUtils;
import android.util.Log;

import com.jeiel.jweather.db.JWeatherDB;
import com.jeiel.jweather.model.City;
import com.jeiel.jweather.model.County;
import com.jeiel.jweather.model.Province;

import java.util.List;

/**
 * Created by Jeiel on 2015/10/4.
 */
public class Utility {
    /**
     * parse Province datas from server
     * @param jweatherDB
     * @param response
     * @return
     */
    public synchronized static boolean handleProvinceResponse(JWeatherDB jweatherDB,
                                                              String response){
        if(!TextUtils.isEmpty(response)){
            Log.d("Province", response.toString());
            String[] allProvinces = response.split(",");
            if(allProvinces != null && allProvinces.length > 0){
                for(String p : allProvinces){
                    String[] array = p.split("\\|");
                    Province province = new Province();
                    province.setProvinceCode(array[0]);
                    province.setProvinceName(array[1]);
                    jweatherDB.saveProvince(province);
                }
                return true;
            }
        }
        return false;
    }

    /**
     * parse City datas from server
     * @param jweatherDB
     * @param response
     * @param provinceId
     * @return
     */
    public synchronized static boolean handleCityResponse(JWeatherDB jweatherDB,
                                                          String response, int provinceId){
        if(!TextUtils.isEmpty(response)){
            Log.d("City", response.toString());
            String[] allCities = response.split(",");
            if(allCities != null && allCities.length > 0){
                for(String c : allCities){
                    String[] array = c.split("\\|");
                    City city = new City();
                    city.setCityCode(array[0]);
                    city.setCityName(array[1]);
                    city.setProvinceId(provinceId);
                    jweatherDB.saveCity(city);
                }
                return true;
            }
        }
        return false;
    }

    /**
     * parse County datas form server
     * @param jweatherDB
     * @param response
     * @param cityId
     * @return
     */
    public synchronized static boolean handleCountyResponse(JWeatherDB jweatherDB,
                                                     String response, int cityId){
        if(!TextUtils.isEmpty(response)){
            String[] allCounties = response.split(",");
            if(allCounties != null && allCounties.length > 0){
                for(String c : allCounties){
                    String[] array = c.split("\\|");
                    County county = new County();
                    county.setCountyCode(array[0]);
                    county.setCountyName(array[1]);
                    county.setCityId(cityId);
                    jweatherDB.saveCounty(county);
                }
                return true;
            }
        }
        return false;
    }
}
