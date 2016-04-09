package data;

import java.util.List;

/**
 * Created by michael on 5/04/16.
 *
 * Weather repository interface.
 */
public interface WeatherRepository {

    interface LoadStatesCallback {
        void onStatesLoaded(List<State> states);
    }

    interface LoadStationsCallback {
        void onStationsLoaded(List<Station> stations);
    }

    interface LoadObservationsCallback {
        void onObservationsLoaded(List<Observation> observations);
    }

    void getStates(LoadStatesCallback callback);

    void getStations(String state, boolean favourite, LoadStationsCallback callback);

    void getObservations(Station station, LoadObservationsCallback callback);

    void saveFavouriteStation(Station station);

    void refreshData();
}
