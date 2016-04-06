package weather_stations;

import data.Weather;
import data.WeatherStation;

import java.util.List;

/**
 * Created by michael on 5/04/16.
 *
 * The contract between the view and the presenter.
 */
interface WeatherStationsContract {

    interface View {

        void setProgressIndicator(boolean active);

        void showWeatherStations(List<WeatherStation> weatherStations);

        void setStationFavourited(String stationId, boolean favourite);

        void showWeatherDetailsUi(String weatherId);
    }

    interface UserActionsListener {

        void loadWeatherStations(boolean forceUpdate);

        void addFavouriteStation(WeatherStation weatherStation);

        void openWeatherDetails(Weather weather);
    }
}
