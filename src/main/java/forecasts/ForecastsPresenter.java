package forecasts;

import data.Forecast;
import data.Station;
import data.WeatherRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.Properties;

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
                    mView.showLatestForecast(forecasts.get(0));
                    mView.showForecastTable(forecasts);
                    mView.showForecastChart(forecasts);
                }
            }
        });
    }

    @Override
    public void setForecastSite(String forecastSite) {
        Properties prop = new Properties();
        OutputStream output = null;

        try {
            output = new FileOutputStream("config.properties");
            prop.setProperty("forecastsite", forecastSite);
            prop.setProperty("openweathermapbaseurl", "http://api.openweathermap.org/data/2.5/forecast/");
            prop.setProperty("openweathermapapikey", "1c5030359eaa0273a6025f61e0b9a6b3");
            prop.setProperty("openweathermapunits", "metric");
            prop.setProperty("forecastiobaseurl", "https://api.forecast.io/forecast/");
            prop.setProperty("forecastioapikey", "3efdaa790f027904b294fb350b74c28e");
            prop.setProperty("forecastiounits", "si");
            prop.store(output, null);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (output != null) {
                try {
                    output.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
