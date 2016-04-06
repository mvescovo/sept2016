package application;

import data.WeatherRepositories;
import data.WeatherServiceApiImpl;
import weather_stations.WeatherStationsPresenter;
import weather_stations.WeatherStationsView;

/**
 * Created by michael on 20/03/16.
 *
 * Start up the app - contains the main method.
 */
public class Main {

    public static void main(String[] args) {
        System.out.println("Hello Assignment");

        // Display weather stations
        WeatherStationsPresenter weatherStationsPresenter = new WeatherStationsPresenter(WeatherRepositories.getInMemoryRepoInstance(new WeatherServiceApiImpl()),
                new WeatherStationsView());
        weatherStationsPresenter.loadWeatherStations(true);
        // display main window...


        // Display and any windows that were left open on the last run of the app.


        // The user actions will take over from there.

    }
}
