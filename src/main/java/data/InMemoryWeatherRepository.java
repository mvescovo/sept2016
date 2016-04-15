package data;

import com.google.common.collect.ImmutableList;

import java.util.HashMap;
import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by michael on 5/04/16.
 *
 * Memory model. Gets and saves data from and to the weather service API.
 */
class InMemoryWeatherRepository implements WeatherRepository {

    private final WeatherServiceApi mWeatherServiceApi;
    private List<State> mCachedStates;
    private HashMap<String,List<Station>> mCachedStations;
    private HashMap<Station,List<Observation>> mCachedObservations = new HashMap<Station, List<Observation>>();
    private List<Station> mCachedFavourites;

    InMemoryWeatherRepository(WeatherServiceApi weatherServiceApi) {
        mWeatherServiceApi = checkNotNull(weatherServiceApi);
    }

    public void getStates(final LoadStatesCallback callback) {
        checkNotNull(callback);
        if (mCachedStates == null) {
            mWeatherServiceApi.getStates(new WeatherServiceApi.WeatherServiceCallback<List<State>>() {
                public void onLoaded(List<State> data) {
                    mCachedStates = ImmutableList.copyOf(data);
                    callback.onStatesLoaded(mCachedStates);
                }
            });
        } else {
            callback.onStatesLoaded(mCachedStates);
        }
    }

    public void getStations(final String state, final boolean favourite, final LoadStationsCallback callback) {
        checkNotNull(callback);
        if (mCachedStations == null) {
            mWeatherServiceApi.getStations(favourite,new WeatherServiceApi.WeatherServiceCallback<HashMap<String, List<Station>>>() {
                public void onLoaded(HashMap<String, List<Station>> data) {
                    mCachedStations = data;
                    callback.onStationsLoaded(mCachedStations.get(state));
                }
            });
        } else {
            callback.onStationsLoaded(mCachedStations.get(state));
        }
    }

    public void getObservations(final Station station, final LoadObservationsCallback callback) {
        checkNotNull(callback);
        if ((mCachedObservations == null) || (mCachedObservations.get(station) == null)) {
            mWeatherServiceApi.getObservations(station, new WeatherServiceApi.WeatherServiceCallback<List<Observation>>() {
                public void onLoaded(List<Observation> data) {
                    if (data == null) {
                        System.out.println("data is NULL");
                    } else if (data.size() == 0) {
                        System.out.println("data size is 0");
                    }
                    mCachedObservations.put(station, data);
                    callback.onObservationsLoaded(mCachedObservations.get(station));
                }
            });
        } else {
            callback.onObservationsLoaded(mCachedObservations.get(station));
        }
    }

    public void saveFavouriteStation(Station favourite) {
        mWeatherServiceApi.saveFavouriteStation(favourite);
    }

    public void refreshData() {
        mCachedStates = null;
        mCachedStations = null;
        mCachedObservations.clear();
    }

	@Override
	public void getFavourites(final LoadFavouritesCallback callback) {
		 checkNotNull(callback);
	        if (mCachedFavourites == null) {
	            mWeatherServiceApi.getFavourites(new WeatherServiceApi.WeatherServiceCallback<List<Station>>() {
	                public void onLoaded(List<Station> data) {
	                	mCachedFavourites = ImmutableList.copyOf(data);
	                    callback.onFavouritesLoaded(mCachedFavourites);
	                }
	            });
	        } else {
	            callback.onFavouritesLoaded(mCachedFavourites);
	        }
		
	}


	public void removeFavouriteStation(Station favourite) {
		mWeatherServiceApi.removeFavouriteStation(favourite);
		
	}



}
