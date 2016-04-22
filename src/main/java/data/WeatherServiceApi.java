package data;

import java.util.HashMap;
import java.util.List;

/**
 * Service API interface for getting and saving weather data.
 *
 * @author michael
 */
interface WeatherServiceApi {

    interface WeatherServiceCallback<T> {
        void onLoaded(T data);
    }

    void getStates(WeatherServiceCallback<List<State>> callback);

    void getStations(WeatherServiceCallback<HashMap<String, List<Station>>> callback);

    void getFavouriteStations(WeatherServiceCallback<List<Station>> callback);

    void saveFavouriteStation(Station favourite);

    void removeFavouriteStation(Station favourite);

    void getObservations(Station station, WeatherServiceCallback<List<Observation>> callback);

}
