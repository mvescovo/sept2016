package forecasts;

import application.Main;
import data.Forecast;
import data.Observation;
import data.Station;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jfree.chart.JFreeChart;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

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
    private JPanel mFheadPanel;
    private JPanel mTablePanel;
    private JScrollPane mFTableScrollPane;

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

        GridBagConstraints cons = new GridBagConstraints();
        cons.gridx = 1;
        cons.gridy = 0;
        cons.weightx = 0.5;
        cons.fill = GridBagConstraints.BOTH;
        cons.anchor = GridBagConstraints.CENTER;
        Main.MainWindow.getInstance().getObservationsPanel().add(mJProgressBar, cons);
        
        
        // Add table panel
        mTablePanel = new JPanel();
        mTablePanel.setLayout(new GridBagLayout());
        mTablePanel.setBackground(Main.getColorcontrast1());
        GridBagConstraints tableCons = new GridBagConstraints();
        tableCons.gridx = 0;
        tableCons.gridy = 0;
        tableCons.weighty = 0;
        tableCons.insets = new Insets(10,10,0,10);
        tableCons.fill = GridBagConstraints.HORIZONTAL;
        tableCons.anchor = GridBagConstraints.NORTHWEST;
        JLabel tableLabel = new JLabel("Weather forecasts.");
        mTablePanel.add(tableLabel, tableCons);
        cons.gridx = 1;
        cons.gridwidth = 1;
        cons.gridy = 1;
        cons.weightx = 0.5;
        cons.weighty = 0.5;
        cons.insets = new Insets(0,10,10,10);
        cons.fill = GridBagConstraints.BOTH;
        cons.anchor = GridBagConstraints.CENTER;
        Main.MainWindow.getInstance().getObservationsPanel().add(mTablePanel, cons);
        mFTableScrollPane = new JScrollPane();
        tableCons.gridy = 1;
        tableCons.weighty = 1;
        tableCons.weightx = 1;
        tableCons.insets = new Insets(0,10,10,10);
        tableCons.fill = GridBagConstraints.BOTH;
        mTablePanel.add(mFTableScrollPane, tableCons);
        
        
        
        
    }

    /**
     * Displays and hides the progress bar used while waiting for data
     *
     * @param active true displays the progress bar and false hides it.
     */
    @Override
    public void setProgressBar(boolean active) {
        if (active) {
            /*Main.MainWindow.getInstance().getStationName().setVisible(false);*/
            mJProgressBar.setVisible(true);
        } else {
            mJProgressBar.setVisible(false);
            /*Main.MainWindow.getInstance().getStationName().setVisible(false);*/
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
        
        mFheadPanel = new JPanel();
        
        GridBagConstraints cons = new GridBagConstraints();
        cons.gridx = 1;
        cons.gridy = 0;
        cons.fill = GridBagConstraints.BOTH;
        cons.anchor = GridBagConstraints.NORTHWEST;
        cons.insets = new Insets(10, 10, 0, 10);
        Main.MainWindow.getInstance().getObservationsPanel().add(mFheadPanel, cons);
        Main.MainWindow.getInstance().getObservationsPanel().repaint();
 
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
    

	@Override
    public void showLatestForecast(Forecast forecast) {
		 mFheadPanel.setLayout(new GridBagLayout());
		 mFheadPanel.setBackground(Main.getColorcontrast1());
	        GridBagConstraints headCons = new GridBagConstraints();
	        headCons.gridx = 0;
	        headCons.gridy = 0;
	        headCons.weighty = 0;
	        headCons.weightx = 0.5;
	        headCons.insets = new Insets(10,10,0,10);
	        headCons.fill = GridBagConstraints.HORIZONTAL;
	        headCons.anchor = GridBagConstraints.WEST;
	        JLabel title = new JLabel();

	        title.setFont(Main.getFonttitle());
	        mFheadPanel.add(title, headCons);
	        JLabel lblLatest = new JLabel();
	        lblLatest.setText("Forecasts:");
	        lblLatest.setFont(Main.getFontnormalbold());
	        headCons.gridy = 1;
	        headCons.weightx = 1;
	        headCons.gridwidth = 1;
	        headCons.insets = new Insets(0,10,0,10);
	        mFheadPanel.add(lblLatest, headCons);
	        JLabel lblLatestDate = new JLabel();
	        SimpleDateFormat standardDateFormat = new SimpleDateFormat("yyyyMMddhhmmss");
	        Date thisDate = new Date(Long.parseLong(forecast.getTime()) * 1000);


	        Calendar c = Calendar.getInstance(TimeZone.getTimeZone("GMT"));
	        c.setTime(thisDate);
	        SimpleDateFormat dateFormat = new SimpleDateFormat("hh:mm a EE dd/MM/yy");
	        lblLatestDate.setText(dateFormat.format(c.getTime()));
	        lblLatestDate.setFont(Main.getFontnormal());
	        headCons.gridy = 2;
	        headCons.weightx = 0;
	        mFheadPanel.add(lblLatestDate, headCons);
	        JLabel lblTemp = new JLabel();
	        lblTemp.setText(forecast.getTemp() + Main.getSymboldegree() + " C");
	        lblTemp.setFont(Main.getFonttitle());
	        headCons.gridx = 1;
	        headCons.gridy = 2;
	        headCons.weightx = 0;
	        headCons.weighty = 1;
	        headCons.gridheight = 2;
	        headCons.anchor = GridBagConstraints.EAST;
	        mFheadPanel.add(lblTemp, headCons);
	        JLabel lblSummary = new JLabel();
	        lblSummary.setText(forecast.getDescription() + ", " + forecast.getHumidity() + "% humidity. Pressure - " + forecast.getPressure() + " "
	                + "Wind - " + forecast.getWindSpeed() + "kph.");
	        lblSummary.setFont(Main.getFontnormal());
	        headCons.gridy = 3;
	        headCons.weightx = 1;
	        headCons.gridx = 0;
	        headCons.gridheight = 1;
	        headCons.anchor = GridBagConstraints.WEST;
	        headCons.insets = new Insets(0,10,30,10);
	        mFheadPanel.add(lblSummary, headCons);
		
	}

	@Override
	public void showForecastTable(List<Forecast> forecasts) {
		  // Table settings
		
        String[] columnNames = { "Date Time", "Temp C" + Main.getSymboldegree(), "Description",
                "Min temp C"  + Main.getSymboldegree(), "Max temp C"  + Main.getSymboldegree(), "Humidity %", "Pressure", "Wind spd kmh", };

        JTable table = new JTable();
        DefaultTableModel dataModel = new DefaultTableModel(columnNames, 0);

        for (Forecast frc : forecasts) {
            dataModel.addRow(frc.getForecastVector());
        }

        table.setModel(dataModel);
        table.setFont(Main.getFontsmall());
        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        table.getColumn("Date Time").setPreferredWidth(160);
        mFTableScrollPane.setViewportView(table);
		
	}

	public JPanel getmTablePanel() {
		return mTablePanel;
	}

	public void setmTablePanel(JPanel mTablePanel) {
		this.mTablePanel = mTablePanel;
	}

	public JScrollPane getmFTableScrollPane() {
		return mFTableScrollPane;
	}

	public void setmFTableScrollPane(JScrollPane mFTableScrollPane) {
		this.mFTableScrollPane = mFTableScrollPane;
	}


}
