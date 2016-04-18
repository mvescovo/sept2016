package observations;

import data.Observation;
import data.Station;

import java.util.List;

/**
 * Created by michael on 5/04/16.
 *
 * The contract between the view and the presenter.
 */
interface ObservationsContract {

    interface View {

        // Enable/disable a progress bar while data is loading
        void setProgressBar(boolean active);

        // show latest observation
        void showLatestObservation(Observation obs);
        
        // Show observations in the current view
        void showObservationTable(List<Observation> observations);

        // Show chart
        void showChart(List<Observation> observations);
    }

    interface UserActionsListener {

        void loadObservations(Station station, boolean forceUpdate);
    }
}
