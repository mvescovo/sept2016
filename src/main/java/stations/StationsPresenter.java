package stations;

import data.State;
import data.Station;
import data.WeatherRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

/**
 * Listen to user actions from the UI. Retrieve data and update the UI.
 *
 * @author michael
 */
public class StationsPresenter implements StationsContract.UserActionsListener {

    private static final Logger logger = LogManager.getLogger(stations.StationsPresenter.class);
    private final WeatherRepository mWeatherRepository;
    private final StationsContract.View mView;

    /**
     * Constructor.
     *
     * @param weatherRepository the repository to get and save data.
     * @param view the view to update the UI.
     */
    public StationsPresenter(WeatherRepository weatherRepository, StationsContract.View view) {
        mWeatherRepository = weatherRepository;
        mView = view;
    }

    /**
     * Load states.
     *
     * @param forceUpdate force latest data. Not currently used as there's no cloud model for states.
     */
    @Override
    public void loadStates(final boolean forceUpdate) {
        mView.setProgressBar(true);
        if (forceUpdate) {
            mWeatherRepository.refreshStates();
        }
        mWeatherRepository.getStates(new WeatherRepository.LoadStatesCallback() {
            public void onStatesLoaded(List<State> states) {
                mView.setProgressBar(false);
                mView.showStates(states);
            }
        });
    }

    /**
     * Load stations.
     *
     * @param stateName   the state to determine the stations.
     * @param forceUpdate force latest data. Not currently used as there's no cloud model for stations.
     */
    @Override
    public void loadStations(String stateName, boolean forceUpdate) {
        mView.setProgressBar(true);
        if (forceUpdate) {
            mWeatherRepository.refreshStations();
        }
        mWeatherRepository.getStations(stateName, new WeatherRepository.LoadStationsCallback() {
            public void onStationsLoaded(List<Station> stations) {
                mView.setProgressBar(false);
                mView.showStations(stations);
            }
        });
    }

    /**
     * Add a favourite station.
     *
     * @param favourite the station to add.
     */
    @Override
    public void addFavouriteStation(Station favourite) {
        mWeatherRepository.saveFavouriteStation(favourite);
    }

    /**
     * Open an observations view.
     *
     * @param station the station to base the observations view.
     */
    @Override
    public void openObservations(Station station) {
        mView.showObservationsUi(station);
    }

    /**
     * Load the list of favourite stations form the repository.
     *
     * @param forceUpdate force latest data. Not currently used as there's no cloud model for stations.
     */
    @Override
	public void loadFavouriteStations(boolean forceUpdate) {
        if (forceUpdate) {
            mWeatherRepository.refreshFavouriteStations();
        }
		mWeatherRepository.getFavouriteStations(new WeatherRepository.LoadFavouritesCallback() {
			@Override
			public void onFavouritesLoaded(List<Station> favourites) {
				mView.showFavourites(favourites);
			}
		});
	}

    /**
     * Remove a favourite station from the repository.
     *
     * @param favourite the station to remove.
     */
    @Override
	public void removeFavouriteStation(Station favourite) {
		mWeatherRepository.removeFavouriteStation(favourite);
	}

}
