package weather_stations;

import com.google.common.collect.Lists;
import data.WeatherRepository;
import data.WeatherStation;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;

/**
 * Created by michael on 5/04/16.
 *
 */
public class WeatherStationsPresenterTest {

    private static List<WeatherStation> STATIONS = Lists.newArrayList(new WeatherStation("Title1", "Description1"), new WeatherStation("Title2", "Description2"));

    private static List<WeatherStation> EMPTY_STATIONS = new ArrayList(0);

    @Mock
    private WeatherRepository mWetherRepository;

    @Mock
    private WeatherStationsContract.View mWeatherStationsView;

    /**
     * {@link ArgumentCaptor} is a powerful Mockito API to capture argument values and use them to
     * perform further actions or assertions on them.
     */
    @Captor
    private ArgumentCaptor<WeatherRepository.LoadWeatherStationsCallback> mLoadWeatherStationsCallbackCaptor;

    private WeatherStationsPresenter mWeatherStationsPresenter;

    @Before
    public void setupWeatherStationsPresenter() {
        // Mockito has a very convenient way to inject mocks by using the @Mock annotation. To
        // inject the mocks in the test the initMocks method needs to be called.
        MockitoAnnotations.initMocks(this);

        // Get a reference to the class under test
        mWeatherStationsPresenter = new WeatherStationsPresenter(mWetherRepository, mWeatherStationsView);
    }

    @Test
    public void loadWeatherStationsFromRepositoryAndLoadIntoView() {
        // Given an initialized WeatherStationsPresenter with initialized weather stations
        // When loading of WeatherStations is requested
       mWeatherStationsPresenter.loadWeatherStations(true);

        // Callback is captured and invoked with stubbed notes
        verify(mWetherRepository).getWeatherStations(mLoadWeatherStationsCallbackCaptor.capture());
        mLoadWeatherStationsCallbackCaptor.getValue().onWeatherStationsLoaded(STATIONS);

        // Then progress indicator is hidden and stations are shown in UI
        verify(mWeatherStationsView).setProgressIndicator(false);
        verify(mWeatherStationsView).showWeatherStations(STATIONS);
    }

    @Test
    public void clickOnStation_ShowsDetailUi() {
        // Given a stubbed Weather
        WeatherStation requestedWeather = new WeatherStation("Details Requested", "For this note");

        // When open weatherStation details is requested
        mWeatherStationsPresenter.openWeatherDetails(requestedWeather);

        // Then weather detail UI is shown
        verify(mWeatherStationsView).showWeatherDetailsUi(any(String.class));
    }

}
