package stations;

import data.State;
import data.Station;

import java.util.List;

/**
 * The contract between the view and the presenter.
 *
 * @author michael
 * @author kendall
 */
public interface StationsContract {

    /**
     * The view interface.
     */
    interface View {
        /**
         * Enable/disable a progress bar while data is loading.
         *
         * @param active true for enable, false for disable.
         */
        void setProgressBar(boolean active);

        /**
         * Show a list of states in the current view
         *
         * @param states the states to show.
         */
        void showStates(List<State> states);

        /**
         * Show a list of stations in the current view
         *
         * @param stations the stations to show.
         */
        void showStations(List<Station> stations);

        /**
         * Show favourite stations in the current view
         *
         * @param favourites the favourites to show.
         */
        void showFavourites(List<Station> favourites);

        /**
         * Show observations in the the observations view
         *
         * @param station the station to determine which observations to show.
         */
        void showObservationsUi(Station station);

        /**
         * Set the Presenter on initialisation
         *
         * @param presenter the presenter to use.
         */
        void setActionListener(UserActionsListener presenter);

        /**
         * When the view is initialised
         */
        void onReady();
    }

    /**
     *
     */
    interface UserActionsListener {

        /**
         * Load states.
         *
         * @param forceUpdate force latest data. Not currently used as there's no cloud model for states.
         */
        void loadStates(boolean forceUpdate);

        /**
         * Load stations.
         *
         * @param stateName the state to determine the stations.
         * @param forceUpdate force latest data. Not currently used as there's no cloud model for stations.
         */
        void loadStations(String stateName, boolean forceUpdate);

        /**
         * Add a favourite station to the repository.
         *
         * @param favourite the station to add.
         */
        void addFavouriteStation(Station favourite);

        /**
         * Open an observations view.
         *
         * @param station the station to base the observations view.
         */
        void openObservations(Station station);

        /**
         * Load favourite stations from repository.
         *
         * @param forceUpdate force latest data. Not currently used as there's no cloud model for stations.
         */
        void loadFavouriteStations(boolean forceUpdate);

        /**
         * Remove a favourite station from repository.
         *
         * @param selectedStation the station to remove.
         */
        void removeFavouriteStation(Station selectedStation);
    }

}
