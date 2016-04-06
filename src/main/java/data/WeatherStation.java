package data;

/**
 * Created by michael on 5/04/16.
 *
 * Weather station entity.
 */
public class WeatherStation {

    private String mUrl;
    private String mCity;

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
}
