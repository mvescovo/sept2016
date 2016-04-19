package data;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Url;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


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
                    Station station = new Station(url, city, stateName);
                    stations.add(station);
                }
                allStations.put(stateName, stations);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return allStations;
    }


	static void saveFavouriteStation(Station favourite) {
    	
		List<Station> list = loadPersistedFavourites();

		if (!list.contains(favourite)) {
			list.add(favourite);

			// serialize the List
			try (OutputStream file = new FileOutputStream("favourites.ser");
					OutputStream buffer = new BufferedOutputStream(file);
					ObjectOutput output = new ObjectOutputStream(buffer);) {
				output.writeObject(list);
			} catch (IOException ex) {

			}
		}
        
    	
        // TODO Steve implement me. Save to file under resources.
       /* try{
            FileWriter fstream = new FileWriter(System.currentTimeMillis() + "stations.txt");
                BufferedWriter out = new BufferedWriter(fstream);
            out.write(station.getmCity());
            out.write(station.getUrl());
            out.close();
            }catch (Exception e){
              System.err.println("Error: " + e.getMessage());
            }*/
    }
  
    // Get observations from files rather than from the BOM (historical data)
    static void getObservationsByDate(String date) {

        // TODO Steve implement me. First implement the other TODO below to save the appropriate data.
        // Change method paramaters as appropriate.
    }

    // Get observations from the BOM
    @SuppressWarnings("rawtypes")
	static void getObservations(final Station station, final WeatherServiceApi.WeatherServiceCallback callback) {
        final List<Observation> observations = new ArrayList<Observation>();

        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl("http://www.bom.gov.au/fwo/")
                .build();

        BomWeatherService service = retrofit.create(BomWeatherService.class);
        Call<JsonObject> call = service.loadObservations(station.getUrl());
        call.enqueue(new Callback<JsonObject>() {
            @SuppressWarnings("unchecked")
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
                    	String cloud_type_id = "";
                    	String delta_t = "";
                    	String gust_kmh = "";
                    	String gust_kt = "";
                    	String dewpt = "";
                    	String press = "";
                    	String press_msl = "";
                    	String press_qnh = "";
                    	String press_tend = "";
                    	String rain_trace = "";
                    	
                    	String rel_hum = "";
                    	String sea_state = "";
                    	String swell_dir_worded = "";
                    	String swell_height = "";
                    	String swell_period = "";
                    	String vis_km = "";
                    	
                    	String weather = "";
                    	String wind_dir = "";
                    	
                    	String wind_spd_kmh = "";
                    	String wind_spd_kt = "";
                        String name = "";
                        String air_temp = "";
                        String date = "";
                        if (!dataArray.get(i).getAsJsonObject().get("name").isJsonNull()) {
                            name = dataArray.get(i).getAsJsonObject().get("name").getAsString();
                        }
                        if (!dataArray.get(i).getAsJsonObject().get("local_date_time_full").isJsonNull()) {
                            date = dataArray.get(i).getAsJsonObject().get("local_date_time_full").getAsString();
                        }
                        if (!dataArray.get(i).getAsJsonObject().get("air_temp").isJsonNull()) {
                            air_temp = dataArray.get(i).getAsJsonObject().get("air_temp").getAsString();
                        }
                        if (!dataArray.get(i).getAsJsonObject().get("wind_spd_kt").isJsonNull()) {
                        	wind_spd_kt = dataArray.get(i).getAsJsonObject().get("wind_spd_kt").getAsString();
                        }
                        if (!dataArray.get(i).getAsJsonObject().get("wind_spd_kmh").isJsonNull()) {
                        	wind_spd_kmh = dataArray.get(i).getAsJsonObject().get("wind_spd_kmh").getAsString();
                        }
                        if (!dataArray.get(i).getAsJsonObject().get("wind_dir").isJsonNull()) {
                        	wind_dir = dataArray.get(i).getAsJsonObject().get("wind_dir").getAsString();
                        }
                        if (!dataArray.get(i).getAsJsonObject().get("weather").isJsonNull()) {
                        	weather = dataArray.get(i).getAsJsonObject().get("weather").getAsString();
                        }
                        
                        if (!dataArray.get(i).getAsJsonObject().get("vis_km").isJsonNull()) {
                        	vis_km = dataArray.get(i).getAsJsonObject().get("vis_km").getAsString();
                        }
                        
                        if (!dataArray.get(i).getAsJsonObject().get("swell_period").isJsonNull()) {
                        	swell_period = dataArray.get(i).getAsJsonObject().get("swell_period").getAsString();
                        }
                        if (!dataArray.get(i).getAsJsonObject().get("swell_height").isJsonNull()) {
                        	swell_height = dataArray.get(i).getAsJsonObject().get("swell_height").getAsString();
                        }
                        if (!dataArray.get(i).getAsJsonObject().get("swell_dir_worded").isJsonNull()) {
                        	swell_dir_worded = dataArray.get(i).getAsJsonObject().get("swell_dir_worded").getAsString();
                        }
                        if (!dataArray.get(i).getAsJsonObject().get("sea_state").isJsonNull()) {
                        	sea_state = dataArray.get(i).getAsJsonObject().get("sea_state").getAsString();
                        }
                        if (!dataArray.get(i).getAsJsonObject().get("rel_hum").isJsonNull()) {
                        	rel_hum = dataArray.get(i).getAsJsonObject().get("rel_hum").getAsString();
                        }
                        
                        
                        
                        if (!dataArray.get(i).getAsJsonObject().get("rain_trace").isJsonNull()) {
                        	rain_trace = dataArray.get(i).getAsJsonObject().get("rain_trace").getAsString();
                        }
                        if (!dataArray.get(i).getAsJsonObject().get("press_tend").isJsonNull()) {
                        	press_tend = dataArray.get(i).getAsJsonObject().get("press_tend").getAsString();
                        }
                        if (!dataArray.get(i).getAsJsonObject().get("press_qnh").isJsonNull()) {
                        	press_qnh = dataArray.get(i).getAsJsonObject().get("press_qnh").getAsString();
                        }
                        if (!dataArray.get(i).getAsJsonObject().get("press_msl").isJsonNull()) {
                        	press_msl = dataArray.get(i).getAsJsonObject().get("press_msl").getAsString();
                        }
                        if (!dataArray.get(i).getAsJsonObject().get("press").isJsonNull()) {
                        	press = dataArray.get(i).getAsJsonObject().get("press").getAsString();
                        }
                        if (!dataArray.get(i).getAsJsonObject().get("dewpt").isJsonNull()) {
                        	dewpt = dataArray.get(i).getAsJsonObject().get("dewpt").getAsString();
                        }
                        if (!dataArray.get(i).getAsJsonObject().get("gust_kt").isJsonNull()) {
                        	gust_kt = dataArray.get(i).getAsJsonObject().get("gust_kt").getAsString();
                        }
                        if (!dataArray.get(i).getAsJsonObject().get("gust_kmh").isJsonNull()) {
                        	gust_kmh = dataArray.get(i).getAsJsonObject().get("gust_kmh").getAsString();
                        }
                        if (!dataArray.get(i).getAsJsonObject().get("delta_t").isJsonNull()) {
                        	delta_t = dataArray.get(i).getAsJsonObject().get("delta_t").getAsString();
                        }
                        if (!dataArray.get(i).getAsJsonObject().get("cloud_type_id").isJsonNull()) {
                        	cloud_type_id = dataArray.get(i).getAsJsonObject().get("cloud_type_id").getAsString();
                        }
                        if (!dataArray.get(i).getAsJsonObject().get("cloud_oktas").isJsonNull()) {
                        	cloud_oktas = dataArray.get(i).getAsJsonObject().get("cloud_oktas").getAsString();
                        }
                        if (!dataArray.get(i).getAsJsonObject().get("cloud_base_m").isJsonNull()) {
                        	cloud_base_m = dataArray.get(i).getAsJsonObject().get("cloud_base_m").getAsString();
                        }
                        if (!dataArray.get(i).getAsJsonObject().get("cloud").isJsonNull()) {
                        	cloud = dataArray.get(i).getAsJsonObject().get("cloud").getAsString();
                        }
                        if (!dataArray.get(i).getAsJsonObject().get("apparent_t").isJsonNull()) {
                        	apparent_t = dataArray.get(i).getAsJsonObject().get("apparent_t").getAsString();
                        }
                        if (!dataArray.get(i).getAsJsonObject().get("lon").isJsonNull()) {
                        	lon = dataArray.get(i).getAsJsonObject().get("lon").getAsString();
                        }
                        
                        if (!dataArray.get(i).getAsJsonObject().get("lat").isJsonNull()) {
                        	lat = dataArray.get(i).getAsJsonObject().get("lat").getAsString();
                        }
                        if (!dataArray.get(i).getAsJsonObject().get("aifstime_utc").isJsonNull()) {
                        	aifstime_utc = dataArray.get(i).getAsJsonObject().get("aifstime_utc").getAsString();
                        }
                        if (!dataArray.get(i).getAsJsonObject().get("local_date_time_full").isJsonNull()) {
                        	local_date_time_full = dataArray.get(i).getAsJsonObject().get("local_date_time_full").getAsString();
                        }
                        if (!dataArray.get(i).getAsJsonObject().get("local_date_time").isJsonNull()) {
                        	local_date_time = dataArray.get(i).getAsJsonObject().get("local_date_time").getAsString();
                        }
                        if (!dataArray.get(i).getAsJsonObject().get("history_product").isJsonNull()) {
                        	history_product = dataArray.get(i).getAsJsonObject().get("history_product").getAsString();
                        }
                        if (!dataArray.get(i).getAsJsonObject().get("wmo").isJsonNull()) {
                        	wmo = dataArray.get(i).getAsJsonObject().get("wmo").getAsString();
                        }
                        
                        
                        Observation observation = new Observation(air_temp, name, wmo, history_product, local_date_time, local_date_time_full,
                        		aifstime_utc, lat, lon, apparent_t, cloud, cloud_base_m, cloud_oktas, cloud_type, cloud_type_id, delta_t, gust_kmh,
                        		dewpt, press, press_msl, press_qnh, press_tend, rain_trace, rel_hum, sea_state, swell_dir_worded, swell_height, swell_period,
                        		vis_km, weather, wind_dir, wind_spd_kmh, wind_spd_kt);
                        observation.setmStateName(station.getmStateName());
                        observations.add(observation);
                    }
                } else {
                    System.out.println("Response body is null");
                }
                callback.onLoaded(observations);
            }

            @SuppressWarnings("unchecked")
			public void onFailure(Call<JsonObject> call, Throwable t) {
                callback.onLoaded(observations);
            }
        });
    }

    private interface BomWeatherService {
        @GET
        Call<JsonObject> loadObservations(@Url String url);
    }


	 @SuppressWarnings("unchecked")
	static List<Station> loadPersistedFavourites() {
		 List<Station> restoredFavs = new ArrayList<Station>();
			// deserialize the favourites.ser file
			try (InputStream file = new FileInputStream("favourites.ser");
					InputStream buffer = new BufferedInputStream(file);
					ObjectInput input = new ObjectInputStream(buffer);) {
				// deserialize the List
				restoredFavs = (List<Station>) input.readObject();
				// display its data
				for (Station s : restoredFavs) {
					System.out.println("Recovered Favourite: " + s);
				}
			} catch (FileNotFoundException e) {
				return new ArrayList<Station>();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			return restoredFavs;
	    }

	public static void removeFavouriteStation(Station favourite) {
		List<Station> list = loadPersistedFavourites();

		if (list.contains(favourite)) {
			list.remove(favourite);

			// serialize the List
			try (OutputStream file = new FileOutputStream("favourites.ser");
					OutputStream buffer = new BufferedOutputStream(file);
					ObjectOutput output = new ObjectOutputStream(buffer);) {
				output.writeObject(list);
			} catch (IOException ex) {

			}
		}
		
	}
	
}
