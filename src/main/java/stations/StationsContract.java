package stations;

import data.State;
import data.Station;

import java.util.List;

/**
 * Created by michael on 5/04/16.
 *
 * The contract between the view and the presenter.
 */
interface StationsContract {

    interface View {

        // Enable/disable a progress bar while data is loading
        void setProgressBar(boolean active);

        // Show a list of states in the current view
        void showStates(List<State> states);

        // Show a list of stations in the current view
        void showStations(List<Station> stations);

        // Show a station as a favourite in the current view
        void showStationAsFavourited(String stationId, boolean favourite);

        // Show observations in the the observations view
        void showObservationsUi(Station station);
    }

    interface UserActionsListener {

        void loadStates(boolean forceUpdate);

        void loadStations(String stateName, boolean favourite, boolean forceUpdate);

        void addFavouriteStation(Station station);

        void openObservations(Station station);
    }

}
