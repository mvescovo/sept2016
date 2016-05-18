package forecasts;

import application.Main;
import data.Forecast;
import data.Station;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.swing.*;
import java.util.List;

/**
 * User interface for the display of weather forecast data for the selected Station.
 *
 * @author michael
 * @author kendall
 */
public class ForecastsView implements ForecastsContract.View {

    private static final Logger logger = LogManager.getLogger(forecasts.ForecastsView.class);
    private ForecastsContract.UserActionsListener mActionsListener;
    private JProgressBar mJProgressBar;
    private Station mStation;

    /**
     * Constructor.
     *
     * Instantiates the view and adds a progress bar, header panel (for the
     * title and latest weather data), and table panel and a chart of
     * temperature data.
     */
    public ForecastsView() {
        // Add a progress bar
        mJProgressBar = new JProgressBar();
        mJProgressBar.setIndeterminate(true);
        mJProgressBar.setVisible(false);

        // TODO: 18/05/16 Kendall to update...
        // Hi Kendall. Don't know how you want to do this. There could either be a shared progress bar
        // that each view can access, or each have their own local progress bar within their
        // own pane to show exactly which parts are loading.

        // This is what you did for the observations view:

//        GridBagConstraints cons = new GridBagConstraints();
//        cons.gridx = 0;
//        cons.gridy = 0;
//        cons.fill = GridBagConstraints.BOTH;
//        cons.anchor = GridBagConstraints.CENTER;
//        Main.MainWindow.getInstance().getObservationsPanel().add(mJProgressBar, cons);
    }

    /**
     * Displays and hides the progress bar used while waiting for data
     *
     * @param active true displays the progress bar and false hides it.
     */
    @Override
    public void setProgressBar(boolean active) {
        if (active) {
            Main.MainWindow.getInstance().getStationName().setVisible(false);
            mJProgressBar.setVisible(true);
        } else {
            mJProgressBar.setVisible(false);
            Main.MainWindow.getInstance().getStationName().setVisible(false);
        }
        Main.MainWindow.getInstance().getObservationsPanel().repaint();
    }

    // TODO: 18/05/16 Kendall to update...
    // This is just to show it's working.
    @Override
    public void showForecasts(List<Forecast> forecasts) {
        for (Forecast forecast :
                forecasts) {
            logger.info("Forecast time: " + forecast.getTime());
            logger.info("Forecast temp: " + forecast.getTemp());
        }
    }

    /**
     * Set the presenter for the view.
     *
     * @param presenter the presenter to use.
     */
    @Override
    public void setActionListener(ForecastsContract.UserActionsListener presenter) {
        mActionsListener = presenter;
    }

    /**
     * When the view has finished initialising load the forecasts from the presenter.
     *
     * @param station the selected weather station
     */
    @Override
    public void onReady(Station station) {
        mStation = station;
        // Test to change the forecast weather source
        mActionsListener.setForecastSite("openweathermap");
        mActionsListener.loadForecasts(mStation, false);
    }

    @Override
    public void setForecastSite(String forecastSite) {
        // TODO: 18/05/16 Kendall to update...
        // Use this to set to either "openweathermap" or "forecastio"
        // I haven't created static variables to hold these values properly. For now I just wanted to get it working.
        // As long as you use those exact values it should be ok. We may need to come up with a better system though.
        // Haven't had time to think about it.
    }
}
