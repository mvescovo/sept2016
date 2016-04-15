package stations;

import application.Main;
import data.State;
import data.Station;
import data.WeatherRepositories;
import data.WeatherServiceApiImpl;
import observations.ObservationsPresenter;
import observations.ObservationsView;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by michael on 5/04/16.
 *
 * UI for the stations list.
 */
public class StationsView implements StationsContract.View, ActionListener, ListSelectionListener {
	// TODO Kendall to modify as necessary - delete comment when done.

	private StationsContract.UserActionsListener mActionsListener;
	private JProgressBar mJProgressBar;
	private JComboBox<State> mStatesComboList;
	private JList<Station> mStationsJList;
	private HashMap<String, Station> mStationHashMap = new HashMap<String, Station>();
	private ObservationsView mObservationsView;
	private ObservationsPresenter mObservationsPresenter;
	private Station selectedStation;
	private List<Station> mFavouritesList = new ArrayList<Station>();
	private JList<Station> mFavouritesJList;

	public StationsView() {
		// Add a progress bar
		mJProgressBar = new JProgressBar();
		mJProgressBar.setIndeterminate(true);
		mJProgressBar.setVisible(false);
		Main.MainWindow.getInstance().getStationsPanel().add(mJProgressBar);
		

	}

	// Set the presenter for this view
	public void setActionListener(StationsContract.UserActionsListener actionListener) {
		mActionsListener = actionListener;
	}

	public void setProgressBar(final boolean active) {
		if (active) {
			mJProgressBar.setVisible(true);
		} else {
			mJProgressBar.setVisible(false);
		}
	}

	// When everything is initialised
	public void onReady() {
		mActionsListener.loadStates(true);
		mActionsListener.loadFavourites(true);
	}

	// Show the states
	public void showStates(List<State> states) {

		mStatesComboList = new JComboBox<State>();
		DefaultComboBoxModel<State> comboModel = new DefaultComboBoxModel<State>();
		for (State s : states) {
			comboModel.addElement(s);
		}
		mStatesComboList.setModel(comboModel);
		mStatesComboList.setSelectedIndex(0);
		mStatesComboList.addActionListener(this);
		GridBagConstraints cons = new GridBagConstraints();
		cons.gridx = 0;
		cons.gridy = 3;
		cons.weighty = 0;
		cons.insets = new Insets(0, 10, 0, 10);
		cons.anchor = GridBagConstraints.WEST;
		cons.fill = GridBagConstraints.BOTH;
		Main.MainWindow.getInstance().getStationsPanel().add(mStatesComboList, cons);
	}

	// Show the stations
	public void showStations(List<Station> stations) {
		mStationHashMap.clear();
		for (int i = 0; i < stations.size(); i++) {
			mStationHashMap.put(stations.get(i).getCity(), stations.get(i));
		}

		if (mStationsJList != null) {
			Main.MainWindow.getInstance().getStationsScrollPane().remove(mStationsJList);
			Main.MainWindow.getInstance().getStationsScrollPane().revalidate();
			Main.MainWindow.getInstance().getStationsScrollPane().repaint();
		}
		mStationsJList = new JList<Station>();
		DefaultListModel<Station> listModel = new DefaultListModel<Station>();
		for (Station s : stations) {
			listModel.addElement(s);
		}
		mStationsJList.setModel(listModel);
		mStationsJList.setSelectedIndex(-1);
		mStationsJList.addListSelectionListener(this);

		Main.MainWindow.getInstance().getStationsScrollPane().setViewportView(mStationsJList);
		Main.MainWindow.getInstance().getStationsScrollPane().repaint();

	}

	public void showFavourites(List<Station> favourites) {
		mFavouritesList.clear();
		for( Station s : favourites) {
			mFavouritesList.add(s);
		}
		if (mFavouritesJList != null) {
			Main.MainWindow.getInstance().getFavouritesScrollPane().remove(mFavouritesJList);
		}
		mFavouritesJList = new JList<Station>();
		DefaultListModel<Station> listModel = new DefaultListModel<Station>();
		for (Station s : favourites) {
			listModel.addElement(s);
		}
		mFavouritesJList.setModel(listModel);
		mFavouritesJList.setSelectedIndex(-1);
		mFavouritesJList.addListSelectionListener(this);

		Main.MainWindow.getInstance().getFavouritesScrollPane().setViewportView(mFavouritesJList);
		Main.MainWindow.getInstance().getFavouritesScrollPane().revalidate();
		Main.MainWindow.getInstance().getFavouritesScrollPane().repaint();
	}

	public void showObservationsUi(Station station) {
		// Clear previous favourite listener upon selecting a new station.
		if (Main.MainWindow.getInstance().getBtnFavourite().getActionListeners() != null) {
			Main.MainWindow.getInstance().getBtnFavourite().removeActionListener(this);
		}
		if (Main.MainWindow.getInstance().getBtnRefresh().getActionListeners() != null) {
			Main.MainWindow.getInstance().getBtnRefresh().removeActionListener(this);
		}
		if (Main.MainWindow.getInstance().getBtnRemove().getActionListeners() != null) {
			Main.MainWindow.getInstance().getBtnRemove().removeActionListener(this);
		}
		Main.MainWindow.getInstance().clearObservationsPanel();

		Main.MainWindow.getInstance().getBtnFavourite().addActionListener(this);
		Main.MainWindow.getInstance().getBtnRefresh().addActionListener(this);
		Main.MainWindow.getInstance().getBtnRemove().addActionListener(this);

		mObservationsView = new ObservationsView();
		mObservationsPresenter = new ObservationsPresenter(
				WeatherRepositories.getInMemoryRepoInstance(new WeatherServiceApiImpl()), mObservationsView);
		mObservationsView.setActionListener(mObservationsPresenter);
		mObservationsView.onReady(station);
	}

	@SuppressWarnings("unchecked")
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == mStatesComboList) {
			JComboBox<State> cb = (JComboBox<State>) e.getSource();
			String stateName = cb.getSelectedItem().toString();
			// second argument false means load non-favourite stations
			mActionsListener.loadStations(stateName, false, true);
			
		} else if (e.getSource() instanceof JButton) {
			JButton btn = (JButton) e.getSource();
			if (btn.getName().equals("refresh")) {
				System.out.println("Refresh clicked");
				
			} else if (btn.getName().equals("add")) {
				mActionsListener.addFavouriteStation(getSelectedStation());	
				mActionsListener.loadFavourites(true);
				
				
			} else if (btn.getName().equals("remove")) {
				mActionsListener.removeFavouriteStation(getSelectedStation());	
				//mActionsListener.loadFavourites(true);
			}

			
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public void valueChanged(ListSelectionEvent e) {

		if (e.getSource() == mStationsJList) {
			// Ensure the user has finished selecting
			if (!e.getValueIsAdjusting()) {
				JList<Station> list = (JList<Station>) e.getSource();

				Station thisStation = list.getSelectedValue();
				String stationName = thisStation.toString();
				this.setSelectedStation(thisStation);

				mActionsListener.openObservations(mStationHashMap.get(stationName));
			} 
		}
		else if (e.getSource() == mFavouritesJList) {
			JList<Station> list = (JList<Station>) e.getSource();
			Station thisStation = list.getSelectedValue();
			this.setSelectedStation(thisStation);
			mActionsListener.openObservations(thisStation);
		}

	}

	public Station getSelectedStation() {
		return selectedStation;
	}

	public void setSelectedStation(Station selectedStation) {
		this.selectedStation = selectedStation;
	}







}
