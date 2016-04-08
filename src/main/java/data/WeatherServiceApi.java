package data;

import java.util.List;

/**
 * Created by michael on 5/04/16.
 *
 * Service API interface for getting and saving weather data.
 */
interface WeatherServiceApi {

    interface WeatherServiceCallback<T> {
        void onLoaded(T data);
    }

    void getStates(WeatherServiceCallback<List<State>> callback);

    void getStations(WeatherServiceCallback<List<Station>> callback);

    void getObservations(WeatherServiceCallback<List<Observation>> callback);

    void saveFavouriteStation(Station station);
}
