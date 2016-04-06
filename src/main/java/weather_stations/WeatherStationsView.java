package weather_stations;

import data.WeatherStation;

import javax.swing.*;
import java.util.List;

/**
 * Created by michael on 5/04/16.
 *
 * UI for the weather stations list.
 */
public class WeatherStationsView implements WeatherStationsContract.View {

    private WeatherStationsContract.UserActionsListener mActionsListener;


    public void setProgressIndicator(boolean active) {

    }

    public void showWeatherStations(List<WeatherStation> weatherStations) {
        JFrame f = new JFrame("Swing Paint Demo");
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setSize(250,250);
        f.setVisible(true);
    }

    public void setStationFavourited(String stationId, boolean favourite) {

    }

    public void showWeatherDetailsUi(String weatherId) {
        // From this weather id, open up a chart to display the weather details.
    }

    /**
     * Listener for clicks on weather stations.
     * Create this listener on the view object that will listen for clicks. i.e. each weather station view (whatever that may be).
     */
    WeatherStationListener mWeatherStationListener = new WeatherStationListener() {
        public void onWeatherStationClick(WeatherStation weatherStation) {
            mActionsListener.openWeatherDetails(weatherStation.getWeather(0));
        }
    };

    private interface WeatherStationListener {
        void onWeatherStationClick(WeatherStation weatherStation);
    }

}
