package forecasts;

import com.google.common.collect.Lists;
import data.Forecast;
import data.WeatherRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.mockito.Matchers.anyObject;
import static org.mockito.Mockito.verify;

/**
 * Tests for the forecasts presenter.
 *
 * @author michael
 */
public class ForecastsPresenterTest {

    private static List<Forecast> FORECASTS = Lists.newArrayList(
            new Forecast("20160529133000", "overcast clouds", "7.7", "12.8", "53", "1018.56", "0.93"),
            new Forecast("20160529140000", "few clouds", "9.9", "15.0", "56", "1018.96", "1.58"));

    // Use mock repository so real data is not affected
    @Mock
    private WeatherRepository mWetherRepository;

    // Use mock view so nothing actually needs to be displayed
    @Mock
    private ForecastsContract.View mForecastsView;

    // Create argument captor to mock the callback
    @Captor
    private ArgumentCaptor<WeatherRepository.LoadForecastsCallback> mLoadForecastsCallbackCaptor;

    private ForecastsPresenter mForecastsPresenter;

    // Setup a real forecasts presenter so we can test it
    @Before
    public void setupForecastsPresenter() {
        MockitoAnnotations.initMocks(this);
        mForecastsPresenter = new ForecastsPresenter(mWetherRepository, mForecastsView);
    }

    /**
     * When the ForecastsPresenter is called upon to load forecasts, check that:
     * - it calls the view to enable a progress bar,
     * - it gets the forecasts from the repository,
     * - it calls the view to disable the progress bar,
     * - it passes the forecasts to the view for display
     */
    @Test
    public void loadForecastsFromRepositoryAndLoadIntoView() {
        mForecastsPresenter.loadForecasts(anyObject(), true);

        verify(mForecastsView).setProgressBar(true);

        verify(mWetherRepository).getForecasts(anyObject(), mLoadForecastsCallbackCaptor.capture());
        mLoadForecastsCallbackCaptor.getValue().onForecastsLoaded(FORECASTS);

        verify(mForecastsView).setProgressBar(false);

        verify(mForecastsView).showForecasts(FORECASTS);
    }
}
