package com.jeiel.jweather.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.jeiel.jweather.model.City;
import com.jeiel.jweather.model.County;
import com.jeiel.jweather.model.Province;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jeiel on 2015/10/4.
 */
public class JWeatherDB {
    /**
     * DB name
     */
    public static final String DB_NAME = "j_weather";
    /**
     * DB version
     */
    public static final int VERSION = 1;
    private static JWeatherDB jweatherDB;
    private SQLiteDatabase db;

    /**
     * make the constructor private
     * @param context
     */
    private JWeatherDB(Context context){
        JWeatherOpenHelper dbHelper = new JWeatherOpenHelper(context, DB_NAME, null, VERSION);
        db = dbHelper.getWritableDatabase();
    }

    /**
     * get JWeather instance
     * @param context
     * @return
     */
    public synchronized  static JWeatherDB getInstance(Context context){
        if(jweatherDB == null){
            jweatherDB = new JWeatherDB(context);
        }
        return jweatherDB;
    }

    /**
     * save Province instance to DB
     * @param province
     */
    public void saveProvince(Province province){
        if(province != null){
            ContentValues values = new ContentValues();
            values.put("province_name", province.getProvinceName());
            values.put("province_code", province.getProvinceCode());
            db.insert("Province", null, values);
        }
    }

    /**
     * load all provinces
     * @return
     */
    public List<Province> loadProvinces(){
        List<Province> list = new ArrayList<Province>();
        Cursor cursor = db.query("Province", null, null, null, null, null, null);
        if(cursor.moveToFirst()){
            do{
                Province province = new Province();
                province.setId(cursor.getInt(cursor.getColumnIndex("id")));
                province.setProvinceName(cursor.getString(cursor.getColumnIndex("province_name")));
                province.setProvinceCode(cursor.getString(cursor.getColumnIndex("province_code")));
                list.add(province);
            }while (cursor.moveToNext());
        }
        if(cursor != null){
            cursor.close();
        }
        return list;
    }
    /**
     * save City instance to DB
     * @param city
     */
    public void saveCity(City city){
        if(city != null){
            ContentValues values = new ContentValues();
            values.put("city_name", city.getCityName());
            values.put("city_code", city.getCityCode());
            values.put("province_id", city.getProvinceId());
            db.insert("City", null, values);
        }
    }

    /**
     * load all cities
     * @param provinceId
     * @return
     */
    public List<City> loadCities(int provinceId){
        List<City> list = new ArrayList<City>();
        Cursor cursor = db.query("City", null, "province_id = ?",
                new String[]{ String.valueOf(provinceId) }, null, null, null);
        if(cursor.moveToFirst()){
            do{
                City city = new City();
                city.setId(cursor.getInt(cursor.getColumnIndex("id")));
                city.setCityName(cursor.getString(cursor.getColumnIndex("city_name")));
                city.setCityCode(cursor.getString(cursor.getColumnIndex("city_code")));
                city.setProvinceId(cursor.getInt(cursor.getColumnIndex("province_id")));
                list.add(city);
            }while(cursor.moveToNext());
        }
        if(cursor != null){
            cursor.close();
        }
        return list;
    }

    /**
     * save County instance to DB
     * @param county
     */
    public void saveCounty(County county){
        if(county != null){
            ContentValues values = new ContentValues();
            values.put("county_name", county.getCountyName());
            values.put("county_code", county.getCountyCode());
            values.put("city_id", county.getCityId());
            db.insert("County", null, values);
        }
    }

    /**
     * load all Counties
     * @param cityId
     * @return
     */
    public List<County> loadCounties(int cityId){
        List<County> list = new ArrayList<County>();
        Cursor cursor = db.query("County", null, "city_id = ?",
                new String[]{String.valueOf(cityId)}, null, null, null);
        if(cursor.moveToFirst()){
            do{
                County county = new County();
                county.setId(cursor.getInt(cursor.getColumnIndex("id")));
                county.setCountyName(cursor.getString(cursor.getColumnIndex("county_name")));
                county.setCountyCode(cursor.getString(cursor.getColumnIndex("county_code")));
                county.setCityId(cursor.getInt(cursor.getColumnIndex("city_id")));
                list.add(county);
            }while (cursor.moveToNext());
        }
        if(cursor != null){
            cursor.close();
        }
        return list;
    }
}
