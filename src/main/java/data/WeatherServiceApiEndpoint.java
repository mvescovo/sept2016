package data;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by michael on 5/04/16.
 *
 * For communication with the BOM.
 */
public class WeatherServiceApiEndpoint {

    /*
    * Some fake data
    * */
    private final static List<State> STATES = new ArrayList<State>();
    static {
        State sState1 = new State("IDV60801", "Victoria");
        State sState2 = new State("IDQ60801", "Queensland");
        STATES.add(sState1);
        STATES.add(sState2);
    }
    public static List<State> loadPersistedStates() {
        return STATES;
    }

    private final static List<Station> STATIONS = new ArrayList<Station>();
    static {
        Station sStation1 = new Station("94866", "Melbourne Airport");
        Station sStation2 = new Station("95936", "Melbourne Olympic Park");
        STATIONS.add(sStation1);
        STATIONS.add(sStation2);
    }
    public static List<Station> loadPersistedStations() {
        return STATIONS;
    }

    private final static List<Observation> OBSERVATIONS = new ArrayList<Observation>();
    static {
        Observation sObservation1 = new Observation("94866", "Melbourne Airport", "20160407220000", "10.2", "Mostly clear", "12.0", "0.0", "85");
        Observation sObservation2 = new Observation("94866", "Melbourne Airport", "20160407213000", "11.9", "Mostly clear", "13.2", "0.0", "81");
        Observation sObservation3 = new Observation("95936", "Melbourne Olympic Park", "20160408010000", "13.1", "-", "12.7", "0.0", "92");
        Observation sObservation4 = new Observation("95936", "Melbourne Olympic Park", "20160408003000", "13.7", "-", "13.1", "0.0", "92");
        OBSERVATIONS.add(sObservation1);
        OBSERVATIONS.add(sObservation2);
        OBSERVATIONS.add(sObservation3);
        OBSERVATIONS.add(sObservation4);
    }
    public static List<Observation> loadPersistedObservations() {
        return OBSERVATIONS;
    }

}
