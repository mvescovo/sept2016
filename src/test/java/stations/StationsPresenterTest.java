package stations;

import com.google.common.collect.Lists;
import data.State;
import data.Station;
import data.WeatherRepository;
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
public class StationsPresenterTest {

    private static List<State> STATES = Lists.newArrayList(
            new State("IDV60801", "Victoria"),
            new State("IDQ60801", "Queensland"));
    private static List<Station> EMPTY_STATES = new ArrayList(0);

    private static List<Station> STATIONS = Lists.newArrayList(
            new Station("94866", "Melbourne Airport"),
            new Station("95936", "Melbourne Olympic Park"));
    private static List<Station> EMPTY_STATIONS = new ArrayList(0);

    @Mock
    private WeatherRepository mWetherRepository;

    @Mock
    private StationsContract.View mStationsView;

    @Captor
    private ArgumentCaptor<WeatherRepository.LoadStatesCallback> mLoadStatesCallbackCaptor;

    private StationsPresenter mStationsPresenter;

    @Before
    public void setupStationsPresenter() {
        MockitoAnnotations.initMocks(this);
        mStationsPresenter = new StationsPresenter(mWetherRepository, mStationsView);
    }

    @Test
    public void loadStatesFromRepositoryAndLoadIntoView() {
        mStationsPresenter.loadStates(true);

        verify(mWetherRepository).getStates(mLoadStatesCallbackCaptor.capture());
        mLoadStatesCallbackCaptor.getValue().onStatesLoaded(STATES);

        verify(mStationsView).setProgressIndicator(false);
        verify(mStationsView).showStates(STATES);
    }

    @Test
    public void loadStationsFromRepositoryAndLoadIntoView() {

    }

    @Test
    public void addFavouriteStationToRepository() {

    }

    @Test
    public void clickOnStationAndShowObservations() {
        Station station = new Station("94866", "Melbourne Airport");
        mStationsPresenter.openObservations(station);
        verify(mStationsView).showObservationsUi(any(String.class));
    }
}
