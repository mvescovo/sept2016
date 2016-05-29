package data;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Matchers.anyObject;
import static org.mockito.Mockito.verify;

/**
 * Tests for the in memory weather repository.
 *
 * It wasn't deemed useful to add tests for the other methods in the InMemoryWeatherRepository class,
 * since the simplest case is just returning a local value to a call back.
 *
 * @author michael
 */
public class InMemoryWeatherRepositoryTest {

    // Use mock WeatherServiceApi so we aren't trying to get real data
    @Mock
    private WeatherServiceApi mWeatherServiceApi;

    private InMemoryWeatherRepository mInMemoryWeatherRepository;

    // Setup InMemoryWeatherRepository so we can test it
    @Before
    public void setupInMemoryWeatherRepository() {
        MockitoAnnotations.initMocks(this);
        mInMemoryWeatherRepository = new InMemoryWeatherRepository(mWeatherServiceApi);

    }

    /**
     * When the InMemoryWeatherRepository is called upon to save a favourite station, check that:
     * - it calls the WeatherServiceApi to save the station
     */
    @Test
    public void saveFavouriteStationToWeatherServiceApi() {
        mInMemoryWeatherRepository.saveFavouriteStation(anyObject());

        verify(mWeatherServiceApi).saveFavouriteStation(anyObject());
    }

    /**
     * When the InMemoryWeatherRepository is called upon to remove a favourite station, check that:
     * - it calls the WeatherServiceApi to remove the station
     */
    @Test
    public void removeFavouriteStationFromWeatherServiceApi() {
        mInMemoryWeatherRepository.removeFavouriteStation(anyObject());

        verify(mWeatherServiceApi).removeFavouriteStation(anyObject());
    }
}
