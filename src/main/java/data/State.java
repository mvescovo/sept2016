package data;

import java.util.HashMap;

/**
 * Created by michael on 7/04/16.
 *
 */
public class State {

    private String mId;
    private String mName;
    private HashMap<String, Station> mWeatherStations; // use station id for first param

    public State(String id, String name) {
        mId = id;
        mName = name;
    }

    public State(String id, String name, HashMap<String, Station> weatherStations) {
        mId = id;
        mName = name;
        mWeatherStations = weatherStations;
    }

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
