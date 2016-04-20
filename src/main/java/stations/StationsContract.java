package stations;

import data.State;
import data.Station;

import java.util.List;

/**
 * Created by michael on 5/04/16.
 *
 * The contract between the view and the presenter.
 */
public interface StationsContract {

    interface View {

        // Enable/disable a progress bar while data is loading
        void setProgressBar(boolean active);

        // Show a list of states in the current view
        void showStates(List<State> states);

        // Show a list of stations in the current view
        void showStations(List<Station> stations);

        // Show favourite stations in the current view
        void showFavourites(List<Station> favourites);

        // Show observations in the the observations view
        void showObservationsUi(Station station);

        // Set the Presenter on initialisation
        void setActionListener(UserActionsListener presenter);

        // When the view is initialised
        void onReady();
    }

    interface UserActionsListener {

        void loadStates(boolean forceUpdate);

        void loadStations(String stateName, boolean forceUpdate);

        void addFavouriteStation(Station favourite);

        void openObservations(Station station);
        
        void loadFavouriteStations(boolean forceUpdate);

		void removeFavouriteStation(Station selectedStation);

    }

}
