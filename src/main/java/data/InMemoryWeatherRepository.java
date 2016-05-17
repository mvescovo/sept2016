package data;

import com.google.common.collect.ImmutableList;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Memory model. Gets and saves data from and to the weather service API.
 *
 * @author michael
 */
class InMemoryWeatherRepository implements WeatherRepository {

    private static final Logger logger = LogManager.getLogger(data.InMemoryWeatherRepository.class);
    private final WeatherServiceApi mWeatherServiceApi;
    private List<State> mCachedStates;
    private HashMap<String,List<Station>> mCachedStations;
    private List<Station> mCachedFavouriteStations;
    private HashMap<Station,List<Observation>> mCachedObservations = new HashMap<>();
    private HashMap<Station,List<Forecast>> mCachedForecasts = new HashMap<>();

    /**
     * Constructor.
     *
     * @param weatherServiceApi the service api to get and save data.
     */
    InMemoryWeatherRepository(WeatherServiceApi weatherServiceApi) {
        mWeatherServiceApi = checkNotNull(weatherServiceApi);
    }

    /**
     * Get a list of states via callback.
     *
     * @param callback to pass data back when it's ready.
     */
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

    /**
     * Get a list of stations via callback.
     *
     * @param state to determine the stations
     * @param callback to pass data back when it's ready.
     */
    @Override
    public void getStations(final String state, final LoadStationsCallback callback) {
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

    /**
     * Get a list of observations via callback.
     *
     * @param station to determine the observations.
     * @param callback to pass data back when it's ready.
     */
    @Override
    public void getObservations(final Station station, final LoadObservationsCallback callback) {
        checkNotNull(callback);
        if ((mCachedObservations == null) || (mCachedObservations.get(station) == null)) {
            mWeatherServiceApi.getObservations(station, new WeatherServiceApi.WeatherServiceCallback<List<Observation>>() {
                public void onLoaded(List<Observation> data) {
                    if (data == null) {
                        logger.debug("data is NULL");
                    } else if (data.size() == 0) {
                        logger.debug("data size is 0");
                    }
                    mCachedObservations.put(station, data);
                    callback.onObservationsLoaded(mCachedObservations.get(station));
                }
            });
        } else {
            callback.onObservationsLoaded(mCachedObservations.get(station));
        }
    }

    /**
     * Get a list of forecasts via callback.
     *
     * @param station to determine the forecasts.
     * @param callback to pass data back when it's ready.
     */
    @Override
    public void getForecasts(Station station, LoadForecastsCallback callback) {
        checkNotNull(callback);
        if ((mCachedForecasts == null) || (mCachedForecasts.get(station) == null)) {
            mWeatherServiceApi.getForecasts(station, new WeatherServiceApi.WeatherServiceCallback<List<Forecast>>() {
                public void onLoaded(List<Forecast> data) {
                    if (data == null) {
                        logger.debug("data is NULL");
                    } else if (data.size() == 0) {
                        logger.debug("data size is 0");
                    }
                    mCachedForecasts.put(station, data);
                    callback.onForecastsLoaded(mCachedForecasts.get(station));
                }
            });
        } else {
            callback.onForecastsLoaded(mCachedForecasts.get(station));
        }
    }

    /**
     * Save a favourite station so that it's available the next time the user opens the app,
     * and for convenience when selecting favourite stations.
     *
     * @param favourite the station to save.
     */
    @Override
    public void saveFavouriteStation(Station favourite) {
        mWeatherServiceApi.saveFavouriteStation(favourite);
    }

    /**
     * Get a list of the favourite stations via callback.
     *
     * @param callback to pass data back when it's ready.
     */
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

    /**
     * Remove a station from the favourites so only the currently desired favourites exist.
     *
     * @param favourite the station to remove.
     */
    @Override
    public void removeFavouriteStation(Station favourite) {
        mWeatherServiceApi.removeFavouriteStation(favourite);
    }

    /**
     * Delete the memory model of states, forcing any future request to call the service api.
     */
    @Override
    public void refreshStates() {
        mCachedStates = null;
    }

    /**
     * Delete the memory model of stations, forcing any future request to call the service api.
     */
    @Override
    public void refreshStations() {
        mCachedStations = null;
    }

    /**
     * Delete the memory model of favourite stations, forcing any future request to call the service api.
     */
    @Override
    public void refreshFavouriteStations() {
        mCachedFavouriteStations = null;
    }

    /**
     * Delete the memory model of observations, forcing any future request to call the service api.
     */
    @Override
    public void refreshObservations() {
        mCachedObservations.clear();
    }

    /**
     * Delete the memory model of forecasts, forcing any future request to call the service api.
     */
    @Override
    public void refreshForecasts() {
        mCachedObservations.clear();
    }

}
