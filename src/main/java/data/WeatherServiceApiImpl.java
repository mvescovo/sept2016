package data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Implementation of weather service API. Determines how to get and save data in this particular implementation.
 *
 * @author michael
 */
public class WeatherServiceApiImpl implements WeatherServiceApi {

    private static final List<State> STATES_SERVICE_DATA = WeatherServiceApiEndpoint.loadPersistedStates();
    private static final HashMap<String, List<Station>> STATIONS_SERVICE_DATA = WeatherServiceApiEndpoint.loadPersistedStations();

    /**
     * Get a list of states.
     *
     * @param callback to return the data when it's been retrieved.
     */
    public void getStates(WeatherServiceCallback<List<State>> callback) {
        List<State> states = new ArrayList<State>(STATES_SERVICE_DATA);
        callback.onLoaded(states);
    }

    /**
     * Get a list of stations.
     *
     * @param callback to return the data when it's been retrieved.
     */
    @Override
    public void getStations(WeatherServiceCallback<HashMap<String, List<Station>>> callback) {
        HashMap<String, List<Station>> stations = new HashMap<String, List<Station>>(STATIONS_SERVICE_DATA);
        callback.onLoaded(stations);
    }

    /**
     * Get a list of observations.
     *
     * @param station to determine what observations to get.
     * @param callback to return the data when it's been retrieved.
     */
    @Override
    public void getObservations(Station station, final WeatherServiceCallback<List<Observation>> callback) {
        WeatherServiceApiEndpoint.getObservations(station, new WeatherServiceApi.WeatherServiceCallback<List<Observation>>() {
            public void onLoaded(List<Observation> data) {
                callback.onLoaded(data);
            }
        });
    }

    /**
     * Save a favourite station.
     *
     * @param favourite the station to save.
     */
    @Override
    public void saveFavouriteStation(Station favourite) {
        WeatherServiceApiEndpoint.saveFavouriteStation(favourite);
    }

    /**
     * Get a favourite station.
     *
     * @param callback to return the station when it's been retrieved.
     */
    @Override
    public void getFavouriteStations(WeatherServiceCallback<List<Station>> callback) {
        callback.onLoaded(WeatherServiceApiEndpoint.getFavourites());
    }

    /**
     * Remove a favourite station.
     *
     * @param favourite the station to remove.
     */
    @Override
    public void removeFavouriteStation(Station favourite) {
        WeatherServiceApiEndpoint.removeFavouriteStation(favourite);
    }

}
