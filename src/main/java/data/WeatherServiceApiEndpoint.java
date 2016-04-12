package data;

import com.google.gson.JsonArray;
import java.io.BufferedWriter;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Url;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.io.FileWriter;
/**
 * Created by michael on 5/04/16.
 *
 * Get data from storage and the BOM
 */
class WeatherServiceApiEndpoint {

    // Get states from file
    static List<State> loadPersistedStates() {
        List<State> states = new ArrayList<State>();
        JsonParser jsonParser = new JsonParser();
        JsonArray jsonArray;

        try {
            jsonArray = (JsonArray) jsonParser.parse(new FileReader(System.getProperty("user.dir") + "/src/main/resources/stations.json"));
            for (int i = 0; i < jsonArray.size(); i++) {
                String stateName = jsonArray.get(i).getAsJsonObject().get("state").getAsString();
                State state = new State(stateName);
                states.add(state);
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return states;
    }

    // Get stations from file
    static HashMap<String,List<Station>> loadPersistedStations(boolean favourite) {

        // TODO Steve. Modify to also return favourite stations if favourite is true.
        // First implement saving of favourites in other method below.

        HashMap<String, List<Station>> allStations = new HashMap<String, List<Station>>();
        JsonParser jsonParser = new JsonParser();
        JsonArray jsonArray;

        try {
            jsonArray = (JsonArray) jsonParser.parse(new FileReader(System.getProperty("user.dir") + "/src/main/resources/stations.json"));
            for (int i = 0; i < jsonArray.size(); i++) {
                String stateName = jsonArray.get(i).getAsJsonObject().get("state").getAsString();
                List<Station> stations = new ArrayList<Station>();
                JsonArray stationArray = jsonArray.get(i).getAsJsonObject().get("stations").getAsJsonArray();
                for (int j = 0; j < stationArray.size(); j++) {
                    String url = stationArray.get(j).getAsJsonObject().get("url").getAsString();
                    String city = stationArray.get(j).getAsJsonObject().get("city").getAsString();
                    Station station = new Station(url, city);
                    stations.add(station);
                }
                allStations.put(stateName, stations);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return allStations;
    }

    static void saveFavouriteStation(Station station) {

        // TODO Steve implement me. Save to file under resources.
        try{
            FileWriter fstream = new FileWriter(System.currentTimeMillis() + "stations.txt");
                BufferedWriter out = new BufferedWriter(fstream);
            out.write(station.getmCity());
            out.write(station.getUrl());
            out.close();
            }catch (Exception e){
              System.err.println("Error: " + e.getMessage());
            }
    }
  
    // Get observations from files rather than from the BOM (historical data)
    static void getObservationsByDate(String date) {

        // TODO Steve implement me. First implement the other TODO below to save the appropriate data.
        // Change method paramaters as appropriate.
    }

    // Get observations from the BOM
    static void getObservations(Station station, final WeatherServiceApi.WeatherServiceCallback callback) {
        final List<Observation> observations = new ArrayList<Observation>();

        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl("http://www.bom.gov.au/fwo/")
                .build();

        BomWeatherService service = retrofit.create(BomWeatherService.class);
        Call<JsonObject> call = service.loadObservations(station.getUrl());
        call.enqueue(new Callback<JsonObject>() {
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if (response.body() != null) {
                    JsonObject observationsObject = response.body().getAsJsonObject("observations");
                    JsonArray dataArray = observationsObject.get("data").getAsJsonArray();
                    for (int i = 0; i < dataArray.size(); i++) {

                        // TODO Steve implement me. Only two fields have been done. Need all relevant fields.
                        // Follow the chain through and also update other relevant methods.


                        // TODO Steve implement me. Data also needs to be saved to files (under resources directory).
                        // Possibly name files by date or whatever makes sense. Then implement the
                        // getObservationsByDate function.
                    	
                    	String wmo = "";
                    	String history_product = "";
                    	String local_date_time = "";
                    	String local_date_time_full = "";
                    	String aifstime_utc = "";
                    	String lat = "";
                    	String lon = "";
                    	String apparent_t = "";
                    	String cloud = "";
                    	String cloud_base_m = "";
                    	String cloud_oktas = "";
                    	String cloud_type = "";
                    	
                        String name = "";
                        String air_temp = "";
                        if (!dataArray.get(i).getAsJsonObject().get("name").isJsonNull()) {
                            name = dataArray.get(i).getAsJsonObject().get("name").getAsString();
                        }
                        if (!dataArray.get(i).getAsJsonObject().get("air_temp").isJsonNull()) {
                            air_temp = dataArray.get(i).getAsJsonObject().get("air_temp").getAsString();
                        }
                        Observation observation = new Observation(air_temp, name);
                        observations.add(observation);
                    }
                } else {
                    System.out.println("Response body is null");
                }
                callback.onLoaded(observations);
            }

            public void onFailure(Call<JsonObject> call, Throwable t) {
                callback.onLoaded(observations);
            }
        });
    }

    private interface BomWeatherService {
        @GET
        Call<JsonObject> loadObservations(@Url String url);
    }

}
