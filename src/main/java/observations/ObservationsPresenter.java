package observations;

import data.Observation;
import data.Station;
import data.WeatherRepository;

import java.util.List;

/**
 * Created by michael on 5/04/16.
 *
 * Listen to user actions from the UI. Retrieve data and update the UI.
 */
public class ObservationsPresenter implements ObservationsContract.UserActionsListener {

    private final WeatherRepository mWeatherRepository;
    private final ObservationsContract.View mView;

    public ObservationsPresenter(WeatherRepository weatherRepository, ObservationsContract.View view) {
        mWeatherRepository = weatherRepository;
        mView = view;
    }

    public void loadObservations(Station station, boolean forceUpdate) {
        mView.setProgressBar(true);
        if (forceUpdate) {
            mWeatherRepository.refreshObservations();
        }
        mWeatherRepository.getObservations(station, new WeatherRepository.LoadObservationsCallback() {
            public void onObservationsLoaded(List<Observation> observations) {
                mView.setProgressBar(false);
                if(observations == null) {
                	System.out.println("Cannot get data!");
                } else {
                    mView.showLatestObservation(observations.get(0));
                    mView.showObservationTable(observations);
                    mView.showChart(observations);
                }
            }
        });
    }
}
