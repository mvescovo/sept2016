package observations;

import data.Observation;
import data.Station;

import java.util.List;

/**
 * The contract between the view and the presenter.
 *
 * @author michael
 * @author kendall
 */
public interface ObservationsContract {

    /**
     * The interface for the view.
     */
    interface View {
        /**
         * Enable/disable a progress bar while data is loading.
         *
         * @param active true for enable, false for disable.
         */
        void setProgressBar(boolean active);

        /**
         * Show latest observation
         *
         * @param observation the observation to show.
         */
        void showLatestObservation(Observation observation);

        /**
         * Show observations in the current view.
         *
         * @param observations the observations to show.
         */
        void showObservationTable(List<Observation> observations);

        /**
         * Show chart.
         *
         * @param observations the observations on which to base to chart.
         */
        void showChart(List<Observation> observations);

        /**
         * Set the Presenter on initialisation.
         *
         * @param presenter the presenter the view will use.
         */
        void setActionListener(ObservationsContract.UserActionsListener presenter);

        /**
         * When the view is initialised.
         *
         * @param station the station to load when the view is ready.
         */
        void onReady(Station station);
    }

    /**
     * The interface for the presenter.
     */
    interface UserActionsListener {

        /**
         * Load observations somehow. Defined in presenter.
         *
         * @param station station on which to base observations.
         * @param forceUpdate determines weather to use memory or force a refresh to pull latest data
         */
        void loadObservations(Station station, boolean forceUpdate);
    }
}
