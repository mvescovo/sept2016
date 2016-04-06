package data;

import java.util.List;

/**
 * Created by michael on 5/04/16.
 *
 * Service API interface for getting and saving weather data.
 */
interface WeatherServiceApi {

    interface WeatherServiceCallback<T> {
        void onLoaded(T data);
    }

    void getWeather(WeatherServiceCallback<Weather> callback);

    void saveWeather(Weather weather);

    void getWeatherStations(WeatherServiceCallback<List<WeatherStation>> callback);

    void saveFavouriteStation(WeatherStation weatherStation);
}
