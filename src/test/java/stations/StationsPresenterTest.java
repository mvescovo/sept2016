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

import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.verify;

/**
 * Tests for the Stations presenter.
 *
 * @author michael
 */
public class StationsPresenterTest {

    private static List<State> STATES = Lists.newArrayList(
            new State("IDV60801", "Victoria"));

    private static List<Station> STATIONS = Lists.newArrayList(
            new Station("94866", "Melbourne Airport", "Victoria"),
            new Station("95936", "Melbourne Olympic Park", "Victoria"));


    // Use mock repository so real data is not affected
    @Mock
    private WeatherRepository mWetherRepository;

    // Use mock view so nothing actually needs to be displayed
    @Mock
    private StationsContract.View mStationsView;

    // Create argument captors to mock the callbacks
    @Captor
    private ArgumentCaptor<WeatherRepository.LoadStatesCallback> mLoadStatesCallbackCaptor;

    @Captor
    private ArgumentCaptor<WeatherRepository.LoadStationsCallback> mLoadStationsCallbackCaptor;

    @Captor
    private ArgumentCaptor<WeatherRepository.LoadFavouritesCallback> mLoadFavouritesCallbackCaptor;

    private StationsPresenter mStationsPresenter;

    // Setup a real stations presenter so we can test it
    @Before
    public void setupStationsPresenter() {
        MockitoAnnotations.initMocks(this);
        mStationsPresenter = new StationsPresenter(mWetherRepository, mStationsView);
    }

    /**
     * When the StationsPresenter is called upon to load states, check that:
     * - it calls the view to enable a progress bar,
     * - it gets the states from the repository,
     * - it calls the view to disable the progress bar,
     * - it passes the states to the view for display
     */
    @Test
    public void loadStatesFromRepositoryAndLoadIntoView() {
        mStationsPresenter.loadStates(true);

        verify(mStationsView).setProgressBar(true);

        verify(mWetherRepository).getStates(mLoadStatesCallbackCaptor.capture());
        mLoadStatesCallbackCaptor.getValue().onStatesLoaded(STATES);

        verify(mStationsView).setProgressBar(false);

        verify(mStationsView).showStates(STATES);
    }

    /**
     * When the StationsPresenter is called upon to load stations, check that:
     * - it calls the view to enable a progress bar,
     * - it gets the stations from the repository,
     * - it calls the view to disable the progress bar,
     * - it passes the stations to the view for display
     */
    @Test
    public void loadStationsFromRepositoryAndLoadIntoView() {
        mStationsPresenter.loadStations(STATES.get(0).getName(), true);
        verify(mStationsView).setProgressBar(true);

        verify(mWetherRepository).getStations(anyString(), mLoadStationsCallbackCaptor.capture());
        mLoadStationsCallbackCaptor.getValue().onStationsLoaded(STATIONS);

        verify(mStationsView).setProgressBar(false);

        verify(mStationsView).showStations(STATIONS);
    }

    /**
     * When the StationsPresenter is called upon to add a favourite station, check that:
     * - it passes the favourite station to the repository for saving
     */
    @Test
    public void addFavouriteStationToRepository() {
        mStationsPresenter.addFavouriteStation(STATIONS.get(0));

        verify(mWetherRepository).saveFavouriteStation(STATIONS.get(0));
    }

    /**
     * When the StationsPresenter is called upon to open an observation, check that:
     * - it passes the station to the view for display
     */
    @Test
    public void openObservationAndLoadIntoView() {
        mStationsPresenter.openObservations(STATIONS.get(0));

        verify(mStationsView).showObservationsUi(STATIONS.get(0));
    }

    /**
     * When the StationsPresenter is called upon to load favourite stations, check that:
     * - it calls the view to enable a progress bar,
     * - it gets the favourite stations from the repository,
     * - it calls the view to disable the progress bar,
     * - it passes the favourite stations to the view for display
     */
    @Test
    public void loadFavouriteStationsFromRepositoryAndLoadIntoView() {
        mStationsPresenter.loadFavouriteStations(true);
        verify(mStationsView).setProgressBar(true);

        verify(mWetherRepository).getFavouriteStations(mLoadFavouritesCallbackCaptor.capture());
        mLoadFavouritesCallbackCaptor.getValue().onFavouritesLoaded(STATIONS);

        verify(mStationsView).setProgressBar(false);

        verify(mStationsView).showFavourites(STATIONS);
    }

    /**
     * When the StationsPresenter is called upon to remove a favourite stations, check that:
     * - it passes the favourite station to the repository for removal
     */
    @Test
    public void removeFavouriteStationFromRepository() {
        mStationsPresenter.removeFavouriteStation(STATIONS.get(0));

        verify(mWetherRepository).removeFavouriteStation(STATIONS.get(0));
    }
}
