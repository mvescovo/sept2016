package data;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by michael on 5/04/16.
 *
 * Implementation of weather service API.
 */
public class WeatherServiceApiImpl implements WeatherServiceApi {

    /*
    * Get data from WeatherServiceApiEndpoint
    * */
    private static final List<State> STATES_SERVICE_DATA = WeatherServiceApiEndpoint.loadPersistedStates();
    private static final List<Station> STATIONS_SERVICE_DATA = WeatherServiceApiEndpoint.loadPersistedStations();
    private static final List<Observation> OBSERVATIONS_SERVICE_DATA = WeatherServiceApiEndpoint.loadPersistedObservations();

    public void getStates(WeatherServiceCallback<List<State>> callback) {
        List<State> states = new ArrayList<State>(STATES_SERVICE_DATA);
        callback.onLoaded(states);
    }

    public void getStations(WeatherServiceCallback<List<Station>> callback) {
        List<Station> stations = new ArrayList<Station>(STATIONS_SERVICE_DATA);
        callback.onLoaded(stations);
    }

    public void getObservations(WeatherServiceCallback<List<Observation>> callback) {
        List<Observation> observations = new ArrayList<Observation>(OBSERVATIONS_SERVICE_DATA);
        callback.onLoaded(observations);
    }

    public void saveFavouriteStation(Station station) {

    }
}
