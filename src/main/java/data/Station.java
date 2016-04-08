package data;

import java.util.HashMap;

/**
 * Created by michael on 5/04/16.
 *
 * Observation station entity.
 */
public class Station {

    private String mUrl;
    private String mCity;
    private HashMap<String, Observation> mObservations;

    public Station(String url, String city) {
        mUrl = url;
        mCity = city;
    }

    public Station(String url, String city, HashMap<String, Observation> observations) {
        mUrl = url;
        mCity = city;
        mObservations = observations;
    }

    public String getUrl() {
        return mUrl;
    }

    public void setUrl(String url) {
        mUrl = url;
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
