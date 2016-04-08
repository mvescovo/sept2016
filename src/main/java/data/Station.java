package data;

import java.util.HashMap;

/**
 * Created by michael on 5/04/16.
 *
 * Observation station entity.
 */
public class Station {

    private String mId;
    private String mCity;
    private HashMap<String, Observation> mObservations;

    public Station(String id, String city) {
        mId = id;
        mCity = city;
    }

    public Station(String id, String city, HashMap<String, Observation> observations) {
        mId = id;
        mCity = city;
        mObservations = observations;
    }

    public String getId() {
        return mId;
    }

    public void setId(String id) {
        mId = id;
    }

    public String getCity() {
        return mCity;
    }

    public void setCity(String city) {
        mCity = city;
    }

    public HashMap<String, Observation> getObservations() {
        return mObservations;
    }

    public void setObservations(HashMap<String, Observation> observations) {
        mObservations = observations;
    }
}
