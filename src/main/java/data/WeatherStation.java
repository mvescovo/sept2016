package data;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by michael on 5/04/16.
 *
 * Weather station entity.
 */
public class WeatherStation {

    private String mUrl;
    private String mCity;
    private ArrayList<Weather> weather;

    public WeatherStation(String url, String city) {
        mUrl = url;
        mCity = city;
    }

    public String getUrl() {
        return mUrl;
    }

    public void setUrl(String url) {
        mUrl = url;
    }

    public String getCity() {
        return mCity;
    }

    public void setCity(String city) {
        mCity = city;
    }

    public Weather getWeather(int id) {
        return weather.get(id);
    }
}
