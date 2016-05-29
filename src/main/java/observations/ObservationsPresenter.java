package observations;

import application.Main;
import data.Observation;
import data.Station;
import data.WeatherRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.swing.*;
import java.util.List;

/**
 * Listen to user actions from the UI. Retrieve data and update the UI.
 *
 * @author michael
 */
public class ObservationsPresenter implements ObservationsContract.UserActionsListener {

    private static final Logger logger = LogManager.getLogger(observations.ObservationsPresenter.class);
    private final WeatherRepository mWeatherRepository;
    private final ObservationsContract.View mView;

    /**
     * Constructor.
     *
     * @param weatherRepository to access data.
     * @param view to display the UI.
     */
    public ObservationsPresenter(WeatherRepository weatherRepository, ObservationsContract.View view) {
        mWeatherRepository = weatherRepository;
        mView = view;
    }

    /**
     * Load observations from the repository.
     *
     * @param station     station on which to base observations.
     * @param forceUpdate determines weather to use memory or force a refresh to pull latest data
     */
    @Override
    public void loadObservations(Station station, boolean forceUpdate) {
        mView.setProgressBar(true);
        if (forceUpdate) {
            mWeatherRepository.refreshObservations();
        }
        mWeatherRepository.getObservations(station, new WeatherRepository.LoadObservationsCallback() {
            public void onObservationsLoaded(List<Observation> observations) {
                mView.setProgressBar(false);
                if(observations == null) {
                    logger.debug("Cannot get observation data from repository.");
                    JOptionPane.showMessageDialog(Main.MainWindow.getInstance().getContainer(),
                            "Cannot connect the the data source. Try again later...");
                } else {
                    mView.showLatestObservation(observations.get(0));
                    mView.showObservationTable(observations);
                    mView.showChart(observations);
                }
            }
        });
    }
}
