package data;

import com.google.common.collect.ImmutableList;
import java.util.List;
import java.util.Map;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by michael on 5/04/16.
 *
 * Memory model. Gets and saves data from and to the weather service API.
 */
public class InMemoryWeatherRepository implements WeatherRepository {

    private final WeatherServiceApi mWeatherServiceApi;
    private List<State> mCachedStates;
    private List<Station> mCachedStations;
    private Map<Station, Observation> mCachedObservations;

    public InMemoryWeatherRepository(WeatherServiceApi weatherServiceApi) {
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
        }
    }

    public void getStations(final LoadStationsCallback callback) {

    }

    public void getObservations(Station station, LoadObservationsCallback callback) {

    }

    public void saveFavouriteStation(Station station, boolean favourite) {

    }

    public void refreshData() {

    }
}
