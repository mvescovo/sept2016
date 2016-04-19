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
    private List<Station> mCachedFavouriteStations;
    private HashMap<Station,List<Observation>> mCachedObservations = new HashMap<Station, List<Observation>>();

    InMemoryWeatherRepository(WeatherServiceApi weatherServiceApi) {
        mWeatherServiceApi = checkNotNull(weatherServiceApi);
    }

    @Override
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

    @Override
    public void getStations(final String state, final boolean favourite, final LoadStationsCallback callback) {
        checkNotNull(callback);
        if (mCachedStations == null) {
            mWeatherServiceApi.getStations(new WeatherServiceApi.WeatherServiceCallback<HashMap<String, List<Station>>>() {
                public void onLoaded(HashMap<String, List<Station>> data) {
                    mCachedStations = data;
                    callback.onStationsLoaded(mCachedStations.get(state));
                }
            });
        } else {
            callback.onStationsLoaded(mCachedStations.get(state));
        }
    }

    @Override
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

    @Override
    public void saveFavouriteStation(Station favourite) {
        mWeatherServiceApi.saveFavouriteStation(favourite);
    }

    @Override
    public void getFavouriteStations(final LoadFavouritesCallback callback) {
        checkNotNull(callback);
        if (mCachedFavouriteStations == null) {
            mWeatherServiceApi.getFavouriteStations(new WeatherServiceApi.WeatherServiceCallback<List<Station>>() {
                public void onLoaded(List<Station> data) {
                    mCachedFavouriteStations = ImmutableList.copyOf(data);
                    callback.onFavouritesLoaded(mCachedFavouriteStations);
                }
            });
        } else {
            callback.onFavouritesLoaded(mCachedFavouriteStations);
        }

    }

    @Override
    public void removeFavouriteStation(Station favourite) {
        mWeatherServiceApi.removeFavouriteStation(favourite);

    }

    @Override
    public void refreshStates() {
        mCachedStates = null;
    }

    @Override
    public void refreshStations() {
        mCachedStations = null;
    }

    @Override
    public void refreshFavouriteStations() {
        mCachedFavouriteStations = null;
    }

    @Override
    public void refreshObservations() {
        mCachedObservations.clear();
    }

}
