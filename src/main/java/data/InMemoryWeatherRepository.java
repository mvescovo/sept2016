package data;

import com.google.common.collect.ImmutableList;
import com.sun.istack.internal.NotNull;

import java.util.List;
import java.util.Map;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by michael on 5/04/16.
 *
 * Memory model. Gets and saves data from and to the weather service API.
 */
public class InMemoryWeatherRepository implements WeatherRepository {

    private final WeatherServiceApi mWeatherServiceApi;
    private List<WeatherStation> mCachedWeatherStations;
    private Map<WeatherStation, Weather> mCachedWeather;

    public InMemoryWeatherRepository(@NotNull WeatherServiceApi weatherServiceApi) {
        mWeatherServiceApi = checkNotNull(weatherServiceApi);
    }

    public void getWeatherStations(@NotNull final LoadWeatherStationsCallback callback) {
        checkNotNull(callback);
        if (mCachedWeatherStations == null) {
            mWeatherServiceApi.getWeatherStations(new WeatherServiceApi.WeatherServiceCallback<List<WeatherStation>>() {
                public void onLoaded(List<WeatherStation> data) {
                    mCachedWeatherStations = ImmutableList.copyOf(data);
                    callback.onWeatherStationsLoaded(mCachedWeatherStations);
                }
            });
        } else {
            callback.onWeatherStationsLoaded(mCachedWeatherStations);
        }
    }

    public void getWeather(WeatherStation weatherStation, @NotNull LoadWeatherCallback callback) {

    }

    public void saveFavouriteStation(WeatherStation weatherStation, boolean favourite) {

    }

    public void refreshData() {

    }
}
