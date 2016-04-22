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

import java.util.List;

import static org.mockito.Mockito.verify;

/**
 * Test for the Stations presenter.
 *
 * @author michael
 */
public class StationsPresenterTest {

    private static List<State> STATES = Lists.newArrayList(
            new State("IDV60801", "Victoria"),
            new State("IDQ60801", "Queensland"));

    private static List<Station> STATIONS = Lists.newArrayList(
            new Station("94866", "Melbourne Airport", "Victoria"),
            new Station("95936", "Melbourne Olympic Park", "Victoria"));

    @Mock
    private WeatherRepository mWetherRepository;

    @Mock
    private StationsContract.View mStationsView;

    @Captor
    private ArgumentCaptor<WeatherRepository.LoadStatesCallback> mLoadStatesCallbackCaptor;

    @Captor
    private ArgumentCaptor<WeatherRepository.LoadStationsCallback> mLoadStationsCallbackCaptor;

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

        verify(mStationsView).setProgressBar(false);
        verify(mStationsView).showStates(STATES);
    }

    @Test
    public void loadStationsFromRepositoryAndLoadIntoView() {
        mStationsPresenter.loadStations(STATES.get(0).getName(), true);

        verify(mWetherRepository).getStations(STATES.get(0).getName(), mLoadStationsCallbackCaptor.capture());
        mLoadStationsCallbackCaptor.getValue().onStationsLoaded(STATIONS);

        verify(mStationsView).setProgressBar(false);
        verify(mStationsView).showStations(STATIONS);
    }

    @Test
    public void addFavouriteStationToRepository() {
        mStationsPresenter.addFavouriteStation(STATIONS.get(0));

        verify(mWetherRepository).saveFavouriteStation(STATIONS.get(0));
    }

    @Test
    public void clickOnStationAndShowObservations() {
        Station station = new Station("94866", "Melbourne Airport", "Victoria");
        mStationsPresenter.openObservations(station);
        verify(mStationsView).showObservationsUi(station);
    }
}
