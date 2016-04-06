package data;

import com.sun.istack.internal.NotNull;

        import java.util.List;

/**
 * Created by michael on 5/04/16.
 *
 * Memory repository interface.
 */
public interface WeatherRepository {

    interface LoadWeatherCallback {
        void onWeatherLoaded(Weather weather);
    }

    interface LoadWeatherStationsCallback {
        void onWeatherStationsLoaded(List<WeatherStation> weatherStations);
    }

    void getWeatherStations(@NotNull LoadWeatherStationsCallback callback);

    void getWeather(WeatherStation weatherStation, @NotNull LoadWeatherCallback callback);

    void saveFavouriteStation(WeatherStation weatherStation, boolean favourite);

    void refreshData();
}
