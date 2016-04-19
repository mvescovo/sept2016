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

    public void loadStations(String stateName, boolean favourite, boolean forceUpdate) {
        mView.setProgressBar(true);
        if (forceUpdate) {
            mWeatherRepository.refreshStations();
        }
        mWeatherRepository.getStations(stateName, favourite, new WeatherRepository.LoadStationsCallback() {
            public void onStationsLoaded(List<Station> stations) {
                mView.setProgressBar(false);
                mView.showStations(stations);
            }
        });
    }

    
    public void addFavouriteStation(Station favourite) {
        mWeatherRepository.saveFavouriteStation(favourite);
    }

    public void openObservations(Station station) {
        mView.showObservationsUi(station);
    }

    
	public void loadFavourites(boolean forceUpdate) {
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

	public void removeFavouriteStation(Station favourite) {
		mWeatherRepository.removeFavouriteStation(favourite);
	}

}
