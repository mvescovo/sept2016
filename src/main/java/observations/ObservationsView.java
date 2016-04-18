package observations;

import application.Main;
import data.Observation;
import data.Station;

import org.jfree.chart.*;
import org.jfree.data.time.*;
import org.jfree.ui.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

/**
 * Created by michael on 5/04/16.
 *
 * User interface for the display of weather observation data for the selected
 * Station.
 */
public class ObservationsView implements ObservationsContract.View {

	private ObservationsContract.UserActionsListener mActionsListener;
	private JProgressBar mJProgressBar;
	private JPanel mTablePanel;
	private JPanel mChartPanel;
	private JPanel mHeadPanel;
	private JScrollPane tableScrollPane;

	/**
	 * Instantiates the view and adds, a progress bar, header panel (for the
	 * title and latest weather data), and table panel and a chart of
	 * temperature data.
	 */
	public ObservationsView() {
		// Add a progress bar
		mJProgressBar = new JProgressBar();
		mJProgressBar.setIndeterminate(true);
		mJProgressBar.setVisible(false);

		GridBagConstraints cons = new GridBagConstraints();
		cons.gridx = 0;
		cons.gridy = 0;
		cons.gridwidth = 2;
		cons.fill = GridBagConstraints.BOTH;
		cons.anchor = GridBagConstraints.CENTER;
		Main.MainWindow.getInstance().getObservationsPanel().add(mJProgressBar, cons);

		// add header panel
		mHeadPanel = new JPanel();
		cons.fill = GridBagConstraints.BOTH;
		cons.anchor = GridBagConstraints.NORTHWEST;
		cons.insets = new Insets(10, 10, 10, 10);
		Main.MainWindow.getInstance().getObservationsPanel().add(mHeadPanel, cons);

		// Add table panel
		mTablePanel = new JPanel();
		mTablePanel.setLayout(new GridBagLayout());
		mTablePanel.setBackground(Main.MainWindow.getInstance().getObservationsPanel().getBackground());
		GridBagConstraints tableCons = new GridBagConstraints();
		tableCons.gridx = 0;
		tableCons.gridy = 0;
		tableCons.weighty = 0;
		tableCons.fill = GridBagConstraints.HORIZONTAL;
		tableCons.anchor = GridBagConstraints.NORTHWEST;
		JLabel tableLabel = new JLabel("Table Panel");
		mTablePanel.add(tableLabel, tableCons);
		cons.gridy = 1;
		cons.weightx = 1;
		cons.weighty = 0.5;
		cons.gridwidth = 2;
		cons.fill = GridBagConstraints.BOTH;
		cons.anchor = GridBagConstraints.CENTER;
		Main.MainWindow.getInstance().getObservationsPanel().add(mTablePanel, cons);

		tableScrollPane = new JScrollPane();
		tableCons.gridy = 1;
		tableCons.weighty = 1;
		tableCons.weightx = 1;
		tableCons.fill = GridBagConstraints.BOTH;
		mTablePanel.add(tableScrollPane, tableCons);

		// Add chart panel
		mChartPanel = new JPanel();
		mChartPanel.setLayout(new BorderLayout());
		mChartPanel.setBackground(Main.MainWindow.getInstance().getObservationsPanel().getBackground());

		cons.gridy = 2;
		cons.weightx = 1;
		cons.weighty = 0.5;
		Main.MainWindow.getInstance().getObservationsPanel().add(mChartPanel, cons);

	}

	/**
	 * Displays and hides the progress bar used while waiting for data
	 * 
	 * @param active
	 *            true displays the progress bar and false hides it.
	 */
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

	/**
	 * When the view has finished initalising load the observations from the
	 * presenter.
	 * 
	 * @param station
	 *            the selected weather station
	 */
	public void onReady(Station station) {
		mActionsListener.loadObservations(station, false);
	}

	/**
	 * Creates a header panel containing the selection station details and a
	 * summary of the latest weather observation for that station.
	 * 
	 * @param obs
	 *            a collection of observations for a weather station.
	 */
	public void showLatestObservation(Observation obs) {
		// Update Station name
		String stationTitle = obs.getmName() + " - " + obs.getmStateName();

		mHeadPanel.setLayout(new GridBagLayout());
		mHeadPanel.setBackground(Main.MainWindow.getInstance().getObservationsPanel().getBackground());
		GridBagConstraints headCons = new GridBagConstraints();
		headCons.gridx = 0;
		headCons.gridy = 0;
		headCons.weighty = 0;
		headCons.weightx = 1;
		headCons.gridwidth = 2;
		headCons.fill = GridBagConstraints.HORIZONTAL;
		headCons.anchor = GridBagConstraints.WEST;

		JLabel title = new JLabel();
		title.setText(stationTitle);
		title.setFont(Main.getFonttitle());
		mHeadPanel.add(title, headCons);

		JLabel lblLatest = new JLabel();
		lblLatest.setText("Latest weather observation:");
		lblLatest.setFont(Main.getFontnormalbold());
		headCons.gridy = 1;
		headCons.weightx = 1;
		headCons.gridwidth = 1;
		mHeadPanel.add(lblLatest, headCons);

		JLabel lblLatestDate = new JLabel();
		SimpleDateFormat standardDateFormat = new SimpleDateFormat("yyyyMMddhhmmss");
		Date thisDate = null;
		try {
			thisDate = standardDateFormat.parse(obs.getmDateTime());
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		Calendar c = Calendar.getInstance(TimeZone.getTimeZone("GMT"));
		c.setTime(thisDate);

		SimpleDateFormat dateFormat = new SimpleDateFormat("hh:mm a EE dd/MM/yy");

		lblLatestDate.setText(dateFormat.format(c.getTime()));
		lblLatestDate.setFont(Main.getFontnormal());
		headCons.gridy = 2;
		headCons.weightx = 0;
		mHeadPanel.add(lblLatestDate, headCons);

		JLabel lblTemp = new JLabel();
		lblTemp.setText(obs.getmAirtemp() + Main.getSymboldegree() + " C");
		lblTemp.setFont(Main.getFonttitle());
		headCons.gridx = 1;
		headCons.gridy = 2;
		headCons.weightx = 1;
		headCons.weighty = 1;
		headCons.gridheight = 2;
		headCons.anchor = GridBagConstraints.EAST;
		mHeadPanel.add(lblTemp, headCons);

		JLabel lblCloud = new JLabel();
		lblCloud.setText(obs.getmCloud() + ", " + obs.getmHumidity() + "% humidity. Wind - " + obs.getmWindDir() + " "
				+ obs.getmWindSpdKmh() + "kph.");
		lblCloud.setFont(Main.getFontnormal());
		headCons.gridy = 3;
		headCons.weightx = 1;
		headCons.gridx = 0;
		headCons.gridheight = 1;
		headCons.anchor = GridBagConstraints.WEST;
		mHeadPanel.add(lblCloud, headCons);

	}

	/**
	 * Create a table to display all observations and attributes and add it to
	 * the table scroll pane.
	 * 
	 * @param observations
	 *            a collection of observations for a weather station.
	 */
	public void showObservationTable(List<Observation> observations) {

		// Table settings
		String[] columnNames = { "Date Time", "Temp C" + Main.getSymboldegree(), "App Temp C" + Main.getSymboldegree(),
				"Dew point", "Humidity %", "Delta-T C" + Main.getSymboldegree(), "Wind Dir", "Wind spd kmh",
				"Wind gust kmh", "Wind spd kts", "Wind gust kts", "Press QNH hPa", "Press MSL hPa",
				"Rain since 9am, mm" };

		JTable table = new JTable();
		DefaultTableModel dataModel = new DefaultTableModel(columnNames, 0);

		for (Observation obs : observations) {
			dataModel.addRow(obs.getObsVector());
		}

		table.setModel(dataModel);
		table.setFont(Main.getFontsmall());
		table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		table.getColumn("Date Time").setPreferredWidth(160);
		tableScrollPane.setViewportView(table);

		// set component visibilities
		// Main.MainWindow.getInstance().getToolbar().get.setVisible(true);
		Main.MainWindow.getInstance().getMenubar().getMenu(1).getItem(0).setEnabled(true);

	}

	/**
	 * Creates and displays a graph of temperatures for the selected weather
	 * station. Uses JFreeChart to build the image.
	 * 
	 * @param observations
	 *            a collection of observations for a weather station.
	 */
	public void showChart(List<Observation> observations) {

		String chtTitle = observations.get(0).getmName() + " - Temperature observations";

		String chtXAxisLabel = "Date and time";
		String chtYAxisLabel = "Temperature " + Main.getSymboldegree() + "C";

		TimeSeries seriesTemp = new TimeSeries("Temp");
		// TimeSeries seriesMin = new TimeSeries("Min");
		// TimeSeries seriesMax = new TimeSeries("Max");

		double temp = 0.0;
		SimpleDateFormat standardDateFormat = new SimpleDateFormat("yyyyMMddhhmmss");

		for (Observation obs : observations) {

			try {
				temp = Double.parseDouble(obs.getmAirtemp());
			} catch (NumberFormatException e) {
				temp = 0.0;
			}

			Date myDate = null;
			try {
				myDate = standardDateFormat.parse(obs.getmDateTime());

				seriesTemp.addOrUpdate(new Hour(myDate), temp);

			} catch (ParseException e) {
				e.printStackTrace();
			}

		}

		TimeSeriesCollection dataset = new TimeSeriesCollection();
		dataset.addSeries(seriesTemp);

		JFreeChart chart = ChartFactory.createTimeSeriesChart(chtTitle, chtXAxisLabel, chtYAxisLabel, dataset, false,
				true, false);
		chart.getTitle().setHorizontalAlignment(HorizontalAlignment.LEFT);
		chart.getTitle().setFont(Main.getFontnormalbold());

		chart.setBackgroundPaint(Main.MainWindow.getInstance().getObservationsPanel().getBackground());
		chart.getXYPlot().getDomainAxis().setLabelFont(Main.getFontsmall());
		chart.getXYPlot().getRangeAxis().setLabelFont(Main.getFontsmall());

		ChartPanel chartPanel = new ChartPanel(chart);

		mChartPanel.add(chartPanel, BorderLayout.CENTER);
		Main.MainWindow.getInstance().getObservationsPanel().revalidate();
		Main.MainWindow.getInstance().getObservationsPanel().repaint();
	}

	/*
	 * Setter for this view's action listener.
	 */
	public void setActionListener(ObservationsContract.UserActionsListener actionListener) {
		mActionsListener = actionListener;
	}

	/*
	 * Getters and setters for the table scroll pane.
	 */
	public JScrollPane getTableScrollPane() {
		return tableScrollPane;
	}

	public void setTableScrollPane(JScrollPane tableScrollPane) {
		this.tableScrollPane = tableScrollPane;
	}

}
