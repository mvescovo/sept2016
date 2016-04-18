package data;

import java.util.HashMap;

/**
 * Created by michael on 7/04/16.
 * 
 * An object representing a 'State or Territory' of Australia. Contains and id,
 * name and a collection of weather stations located within the 'State'
 *
 */
public class State {

	private String mId;
	private String mName;

	// uses station id for the first parameter
	private HashMap<String, Station> mWeatherStations;

	/*
	 * Output the State name attribute only.
	 * 
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 *
	 */
	@Override
	public String toString() {
		StringBuilder result = new StringBuilder();
		result.append(mName);
		return result.toString();
	}

	/**
	 * State constructor using name only
	 * 
	 * @param name
	 *            the name of the State
	 */
	public State(String name) {
		mName = name;
	}

	/**
	 * State constructor using id and name attributes.
	 * 
	 * @param id
	 *            the id string for the State
	 * @param name
	 *            the name of the State.
	 */
	public State(String id, String name) {
		mId = id;
		mName = name;
	}

	/**
	 * State constructor using all attributes: id; name; and the list of weather
	 * stations.
	 * 
	 * @param id
	 *            the weather station id string
	 * @param name
	 *            the State name
	 * @param weatherStations
	 *            A hashmap of weather stations
	 */
	public State(String id, String name, HashMap<String, Station> weatherStations) {
		mId = id;
		mName = name;
		mWeatherStations = weatherStations;
	}

	/*
	 * Class attribute getters and setters.
	 */
	public String getId() {
		return mId;
	}

	public void setId(String id) {
		mId = id;
	}

	public String getName() {
		return mName;
	}

	public void setName(String name) {
		mName = name;
	}

	public HashMap<String, Station> getWeatherStations() {
		return mWeatherStations;
	}

	public void setWeatherStations(HashMap<String, Station> weatherStations) {
		mWeatherStations = weatherStations;
	}
}
