package forecasts;

import data.Forecast;
import data.Station;

import java.util.List;

/**
 * The contract between the view and the presenter.
 *
 * @author michael
 */
public interface ForecastsContract {

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
         * Show forecasts in the current view.
         *
         * @param forecasts the observations to show.
         */
        void showForecasts(List<Forecast> forecasts);

        /**
         * Set the Presenter on initialisation.
         *
         * @param presenter the presenter the view will use.
         */
        void setActionListener(ForecastsContract.UserActionsListener presenter);

        /**
         * When the view is initialised.
         *
         * @param station the station to load when the view is ready.
         */
        void onReady(Station station);

        // ***Not currently ready. Ignore for now.***
        // FYI: Interface may change.
        void setForecastSite(String forecastSite);
    }

    /**
     * The interface for the presenter.
     */
    interface UserActionsListener {

        /**
         * Load forecasts
         *
         * @param station station on which to base forecasts.
         * @param forceUpdate determines weather to use memory or force a refresh to pull latest data
         */
        void loadForecasts(Station station, boolean forceUpdate);

        // TODO: 18/05/16 Michael to add this functionality
        void setForecastSite(String forecastSite);
    }
}
