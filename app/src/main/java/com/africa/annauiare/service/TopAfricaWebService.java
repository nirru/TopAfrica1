package com.africa.annauiare.service;

import com.africa.annauiare.Config;
import com.africa.annauiare.modal.weather.WeatherModal;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by ericbasendra on 10/06/16.
 */
public interface TopAfricaWebService {
    @GET("/data/2.5/weather?appid=" + Config.OPEN_WEATHER_MAP_KEY) Observable<WeatherModal> getWeatherForLatLon(@Query("lat") double lat, @Query("lng") double lng);

}
