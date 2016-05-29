package observations;

import com.google.common.collect.Lists;
import data.Observation;
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
 * Tests for the observations presenter.
 *
 * @author michael
 */
public class ObservationsPresenterTest {

    private static List<Observation> OBSERVATIONS = Lists.newArrayList(
            new Observation("Melbourne Olympic Park", "20160529133000", "7.7", "Mostly clear", "12.4", "0.0", "53"),
            new Observation("Melbourne Olympic Park", "20160529140000", "9.9", "-", "13.2", "0.0", "56"));

    // Use mock repository so real data is not affected
    @Mock
    private WeatherRepository mWetherRepository;

    // Use mock view so nothing actually needs to be displayed
    @Mock
    private ObservationsContract.View mObservationsView;

    // Create argument captor to mock the callback
    @Captor
    private ArgumentCaptor<WeatherRepository.LoadObservationsCallback> mLoadObservationsCallbackCaptor;

    private ObservationsPresenter mObservationsPresenter;

    // Setup a real observations presenter so we can test it
    @Before
    public void setupObservationsPresenter() {
        MockitoAnnotations.initMocks(this);
        mObservationsPresenter = new ObservationsPresenter(mWetherRepository, mObservationsView);
    }

    /**
     * When the ObservationsPresenter is called upon to load observations, check that:
     * - it calls the view to enable a progress bar,
     * - it gets the observations from the repository,
     * - it calls the view to disable the progress bar,
     * - it passes the observations to the view for display
     */
    @Test
    public void loadObservationsFromRepositoryAndLoadIntoView() {
        mObservationsPresenter.loadObservations(anyObject(), true);

        verify(mObservationsView).setProgressBar(true);

        verify(mWetherRepository).getObservations(anyObject(), mLoadObservationsCallbackCaptor.capture());
        mLoadObservationsCallbackCaptor.getValue().onObservationsLoaded(OBSERVATIONS);

        verify(mObservationsView).setProgressBar(false);

        verify(mObservationsView).showObservationTable(OBSERVATIONS);
    }
}
