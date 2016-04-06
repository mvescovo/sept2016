package weather_stations;

import com.sun.istack.internal.NotNull;
import data.Weather;
import data.WeatherRepository;
import data.WeatherStation;

import java.util.List;

/**
 * Created by michael on 5/04/16.
 *
 * Listen to user actions from the UI. Retrieve data and update the UI.
 *
 */
public class WeatherStationsPresenter implements WeatherStationsContract.UserActionsListener {

    private final WeatherRepository mWeatherRepository;
    private final WeatherStationsContract.View mView;

    public WeatherStationsPresenter(@NotNull WeatherRepository weatherRepository, @NotNull WeatherStationsContract.View view) {
        mWeatherRepository = weatherRepository;
        mView = view;
    }

    public void loadWeatherStations(final boolean forceUpdate) {
        mView.showWeatherStations(null); // Temp duplicate line to make it show something before implementing.

        mView.setProgressIndicator(true);
        mWeatherRepository.getWeatherStations(new WeatherRepository.LoadWeatherStationsCallback() {
            public void onWeatherStationsLoaded(List<WeatherStation> weatherStations) {
                mView.setProgressIndicator(false);
                mView.showWeatherStations(weatherStations);
            }
        });
    }

    public void addFavouriteStation(WeatherStation weatherStation) {

    }

    public void openWeatherDetails(@NotNull Weather weather) {

    }
}
