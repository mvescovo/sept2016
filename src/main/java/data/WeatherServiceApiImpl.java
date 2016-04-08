package data;

import java.util.ArrayList;
import java.util.HashMap;
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
    private static final HashMap<String,List<Station>> STATIONS_SERVICE_DATA = WeatherServiceApiEndpoint.loadPersistedStations();

    public void getStates(WeatherServiceCallback<List<State>> callback) {
        List<State> states = new ArrayList<State>(STATES_SERVICE_DATA);
        callback.onLoaded(states);
    }

    public void getStations(WeatherServiceCallback<HashMap<String,List<Station>>> callback) {
        HashMap<String,List<Station>> stations = new HashMap<String, List<Station>>(STATIONS_SERVICE_DATA);
        callback.onLoaded(stations);
    }

    public void getObservations(Station station, final WeatherServiceCallback<List<Observation>> callback) {
        WeatherServiceApiEndpoint.getObservations(station, new WeatherServiceApi.WeatherServiceCallback<List<Observation>>() {
            public void onLoaded(List<Observation> data) {
                callback.onLoaded(data);
            }
        });
    }

    public void saveFavouriteStation(Station station) {
        // TODO Implement me
    }
}
