package forecasts;

import data.Forecast;
import data.Station;
import data.WeatherRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

/**
 * Listen to user actions from the UI. Retrieve data and update the UI.
 *
 * @author michael
 */
public class ForecastsPresenter implements ForecastsContract.UserActionsListener {

    private static final Logger logger = LogManager.getLogger(forecasts.ForecastsPresenter.class);
    private final WeatherRepository mWeatherRepository;
    private final ForecastsContract.View mView;

    /**
     * Constructor.
     *
     * @param weatherRepository to access data.
     * @param view to display the UI.
     */
    public ForecastsPresenter(WeatherRepository weatherRepository, ForecastsContract.View view) {
        mWeatherRepository = weatherRepository;
        mView = view;
    }

    /**
     * Load forecasts from the repository.
     *
     * @param station     station on which to base forecasts.
     * @param forceUpdate determines weather to use memory or force a refresh to pull latest data
     */
    @Override
    public void loadForecasts(Station station, boolean forceUpdate) {
        mView.setProgressBar(true);
        if (forceUpdate) {
            mWeatherRepository.refreshForecasts();
        }
        mWeatherRepository.getForecasts(station, new WeatherRepository.LoadForecastsCallback() {
            public void onForecastsLoaded(List<Forecast> forecasts) {
                mView.setProgressBar(false);
                if(forecasts == null) {
                    logger.debug("Cannot get forecast data from repository.");
                } else {
                    mView.showForecasts(forecasts);
                }
            }
        });
    }

    @Override
    public void setForecastSite(String forecastSite) {
        // TODO: 18/05/16 Michael to update
    }
}
