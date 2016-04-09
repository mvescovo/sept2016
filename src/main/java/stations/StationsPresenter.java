package stations;

import data.State;
import data.Station;
import data.WeatherRepository;

import java.util.List;

/**
 * Created by michael on 5/04/16.
 *
 * Listen to user actions from the UI. Retrieve data and update the UI.
 *
 */
public class StationsPresenter implements StationsContract.UserActionsListener {

    private final WeatherRepository mWeatherRepository;
    private final StationsContract.View mView;

    public StationsPresenter(WeatherRepository weatherRepository, StationsContract.View view) {
        mWeatherRepository = weatherRepository;
        mView = view;
    }

    public void loadStates(final boolean forceUpdate) {
        mView.setProgressBar(true);
        mWeatherRepository.getStates(new WeatherRepository.LoadStatesCallback() {
            public void onStatesLoaded(List<State> states) {
                mView.setProgressBar(false);
                mView.showStates(states);
            }
        });
    }

    public void loadStations(String stateName, boolean favourite, boolean forceUpdate) {
        mView.setProgressBar(true);
        mWeatherRepository.getStations(stateName, favourite, new WeatherRepository.LoadStationsCallback() {
            public void onStationsLoaded(List<Station> stations) {
                mView.setProgressBar(false);
                mView.showStations(stations);
            }
        });
    }

    public void addFavouriteStation(Station station) {
        mWeatherRepository.saveFavouriteStation(station);
    }

    public void openObservations(Station station) {
        mView.showObservationsUi(station);
    }

}
