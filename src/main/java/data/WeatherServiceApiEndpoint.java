package data;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.*;

import java.io.*;
import java.util.*;


/**
 * Get data from storage and online weather sources
 *
 * @author steve - implementation
 * @author michael - shell methods
 */
class WeatherServiceApiEndpoint {

    private static final Logger logger = LogManager.getLogger(data.WeatherServiceApiEndpoint.class);

    /**
     * Get states from file
     *
     * @return the list of states
     */
    static List<State> loadPersistedStates() {
        List<State> states = new ArrayList<>();
        JsonParser jsonParser = new JsonParser();
        JsonArray jsonArray;
        Reader reader = new InputStreamReader(WeatherServiceApiEndpoint.class.getResourceAsStream("/stationsTemp.json"));
        jsonArray = jsonParser.parse(reader).getAsJsonArray();

        for (int i = 0; i < jsonArray.size(); i++) {
            String stateName = jsonArray.get(i).getAsJsonObject().get("state").getAsString();
            State state = new State(stateName);
            states.add(state);
        }
        return states;
    }

    /**
     * Get stations from file
     *
     * @return a map of the list of stations.
     */
    static HashMap<String,List<Station>> loadPersistedStations() {
        HashMap<String, List<Station>> allStations = new HashMap<>();
        JsonParser jsonParser = new JsonParser();
        JsonArray jsonArray;
        Reader reader = new InputStreamReader(WeatherServiceApiEndpoint.class.getResourceAsStream("/stationsTemp.json"));
        jsonArray = jsonParser.parse(reader).getAsJsonArray();

        for (int i = 0; i < jsonArray.size(); i++) {
            String stateName = jsonArray.get(i).getAsJsonObject().get("state").getAsString();
            List<Station> stations = new ArrayList<>();
            JsonArray stationArray = jsonArray.get(i).getAsJsonObject().get("stations").getAsJsonArray();
            for (int j = 0; j < stationArray.size(); j++) {
                String url = stationArray.get(j).getAsJsonObject().get("url").getAsString();
                String city = stationArray.get(j).getAsJsonObject().get("city").getAsString();
                String latitude = stationArray.get(j).getAsJsonObject().get("lat").getAsString();
                String longitude = stationArray.get(j).getAsJsonObject().get("long").getAsString();
                Station station = new Station(url, city, stateName, latitude, longitude);
                stations.add(station);
            }
            allStations.put(stateName, stations);
        }
        return allStations;
    }

    /**
     * Save a favourite station to a file.
     *
     * @param favourite the station to save.
     */
    static void saveFavouriteStation(Station favourite) {
        List<Station> list = getFavourites();

        if (!list.contains(favourite)) {
            list.add(favourite);

            try (OutputStream file = new FileOutputStream("favourites.ser");
                 OutputStream buffer = new BufferedOutputStream(file);
                 ObjectOutput output = new ObjectOutputStream(buffer);) {
                output.writeObject(list);
                logger.info("Written favourite to file");
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    /**
     * Get observations from the BOM. Uses Retrofit library which does work on a background thread.
     *
     * @param station to determine what observations to get.
     * @param callback to return data when it's ready.
     */
    @SuppressWarnings("rawtypes")
    static void getObservations(final Station station, final WeatherServiceApi.WeatherServiceCallback<List<Observation>> callback) {
        final List<Observation> observations = new ArrayList<>();

        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl("http://www.bom.gov.au/fwo/")
                .build();

        WeatherService service = retrofit.create(WeatherService.class);
        Call<JsonObject> call = service.loadObservations(station.getUrl());
        call.enqueue(new Callback<JsonObject>() {
            @SuppressWarnings("unchecked")
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if (response.body() != null) {
                    JsonObject observationsObject = response.body().getAsJsonObject("observations");
                    JsonArray dataArray = observationsObject.get("data").getAsJsonArray();
                    for (int i = 0; i < dataArray.size(); i++) {
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
                        observation.setmStateName(station.getState());
                        observations.add(observation);
                    }
                } else {
                    logger.info("BOM Observation response body is null");
                }
                callback.onLoaded(observations);
            }

            @SuppressWarnings("unchecked")
            public void onFailure(Call<JsonObject> call, Throwable t) {
                callback.onLoaded(observations);
            }
        });
    }

    /**
     * Get forecasts from online sites. Uses Retrofit library which does work on a background thread.
     *
     * @param station to determine what forecasts to get.
     * @param callback to return data when it's ready.
     */
    static void getForecasts(final Station station, final WeatherServiceApi.WeatherServiceCallback<List<Forecast>> callback) {
        final List<Forecast> forecasts = new ArrayList<>();
        String forecastSite = "";
        String siteBaseUrl = "";
        String apiKey = "";
        String units = "";
        Properties properties = new Properties();
        InputStream input = null;

        try {
            input = new FileInputStream("config.properties");
            properties.load(input);
            forecastSite = properties.getProperty("forecastsite");
            if (forecastSite.equals("forecastio")) {
                apiKey = properties.getProperty("forecastioapikey");
                siteBaseUrl = properties.getProperty("forecastiobaseurl");
                units = properties.getProperty("forecastiounits");
            } else {
                apiKey = properties.getProperty("openweathermapapikey");
                siteBaseUrl = properties.getProperty("openweathermapbaseurl");
                units = properties.getProperty("openweathermapunits");
            }
        } catch (IOException e) {
            e.printStackTrace();
            // If any of this failed then return with empty forecasts list
            callback.onLoaded(forecasts);
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(siteBaseUrl)
                .build();

        WeatherService service = retrofit.create(WeatherService.class);

        if (forecastSite.equals("forecastio")) {
            // using forecastio
            Call<JsonObject> call = service.loadForecastsFromForecastIo(apiKey,
                    station.getLatitude(),
                    station.getLongitude(),
                    units);

            call.enqueue(new Callback<JsonObject>() {
                public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                    if (response.body() != null) {
                        JsonObject forecastsObject = response.body().getAsJsonObject("hourly");
                        JsonArray dataArray = forecastsObject.get("data").getAsJsonArray();
                        for (int i = 0; i < dataArray.size(); i++) {
                            // TODO: 18/05/16 Steve to analyse API and update with all necessary data
                            String time = "";
                            String temp = "";
                            String temp_min = "";
                            String temp_max = "";
                            String pressure = "";
                            String humidity = "";
                            String name = "";
                            String description = "";
                            String rain = "";
                            String lon = "";
                            String lat = "";
                            String speed = "";
                            if (!dataArray.get(i).getAsJsonObject().get("time").isJsonNull()) {
                            	time = dataArray.get(i).getAsJsonObject().get("time").getAsString();
                            }
                            if (!dataArray.get(i).getAsJsonObject().get("temp").isJsonNull()) {
                            	temp = dataArray.get(i).getAsJsonObject().get("temp").getAsString();
                            }
                            if (!dataArray.get(i).getAsJsonObject().get("temp_min").isJsonNull()) {
                            	temp_min = dataArray.get(i).getAsJsonObject().get("temp_min").getAsString();
                            }
                            if (!dataArray.get(i).getAsJsonObject().get("temp_max").isJsonNull()) {
                            	temp_max = dataArray.get(i).getAsJsonObject().get("temp_max").getAsString();
                            }
                            if (!dataArray.get(i).getAsJsonObject().get("pressure").isJsonNull()) {
                            	pressure = dataArray.get(i).getAsJsonObject().get("pressure").getAsString();
                            }
                            if (!dataArray.get(i).getAsJsonObject().get("humidity").isJsonNull()) {
                            	humidity = dataArray.get(i).getAsJsonObject().get("humidity").getAsString();
                            }
                            if (!dataArray.get(i).getAsJsonObject().get("name").isJsonNull()) {
                            	name = dataArray.get(i).getAsJsonObject().get("name").getAsString();
                            }
                            if (!dataArray.get(i).getAsJsonObject().get("description").isJsonNull()) {
                            	description = dataArray.get(i).getAsJsonObject().get("description").getAsString();
                            }
                            if (!dataArray.get(i).getAsJsonObject().get("rain").isJsonNull()) {
                            	rain = dataArray.get(i).getAsJsonObject().get("rain").getAsString();
                            }
                            if (!dataArray.get(i).getAsJsonObject().get("lon").isJsonNull()) {
                            	lon = dataArray.get(i).getAsJsonObject().get("lon").getAsString();
                            }
                            if (!dataArray.get(i).getAsJsonObject().get("lat").isJsonNull()) {
                            	lat = dataArray.get(i).getAsJsonObject().get("lat").getAsString();
                            }
                            if (!dataArray.get(i).getAsJsonObject().get("speed").isJsonNull()) {
                            	speed = dataArray.get(i).getAsJsonObject().get("speed").getAsString();
                            }
                            

                            Forecast forecast = new Forecast(time, temp, temp_min, temp_max, pressure, humidity, name, description, rain, lon, lat, speed);
                            forecasts.add(forecast);
                        }
                    } else {
                        logger.info("Forecast response body is null");
                    }
                    callback.onLoaded(forecasts);
                }

                public void onFailure(Call<JsonObject> call, Throwable t) {
                    callback.onLoaded(forecasts);
                }
            });
        } else {
            // Using openweathermap
            Map<String, String> queryMap = new HashMap<>();
            queryMap.put("appid", apiKey);
            queryMap.put("lat", station.getLatitude());
            queryMap.put("lon", station.getLongitude());
            queryMap.put("units", units);
            Call<JsonObject> call = service.loadForecastsFromOpenWeatherMap("", queryMap);

            call.enqueue(new Callback<JsonObject>() {
                public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                    if (response.body() != null) {
                        JsonArray dataArray = response.body().getAsJsonArray("list");
                        for (int i = 0; i < dataArray.size(); i++) {
                            JsonObject dataObject = dataArray.get(i).getAsJsonObject();
                            // TODO: 18/05/16 Steve to analyse API and update with all necessary data
                            String time = "";
                            String temp = "";
                            String temp_min = "";
                            String temp_max = "";
                            String pressure = "";
                            String humidity = "";
                            String name = "";
                            String description = "";
                            String rain = "";
                            String lon = "";
                            String lat = "";
                            String speed = "";
                            
                           
                            if (!dataObject.get("dt").isJsonNull()) {
                                time = dataObject.get("dt").getAsString();
                            }

                            dataObject = dataObject.get("main").getAsJsonObject();

                            if (!dataObject.get("temp").isJsonNull()) {
                                temp = dataObject.get("temp").getAsString();
                            }
                            
                            if (!dataObject.get("temp_max").isJsonNull()) {
                            	temp_max = dataObject.get("temp_max").getAsString();
                            }
                            
                            if (!dataObject.get("temp_min").isJsonNull()) {
                            	temp_min = dataObject.get("temp_min").getAsString();
                            }
                            
                            if (!dataObject.get("pressure").isJsonNull()) {
                            	pressure = dataObject.get("pressure").getAsString();
                            }
                            
                            if (!dataObject.get("humidity").isJsonNull()) {
                            	humidity = dataObject.get("humidity").getAsString();
                            }
                            
//                            if (!dataObject.get("name").isJsonNull()) {
//                            	name = dataObject.get("name").getAsString();
//                            }
//                            if (!dataObject.get("description").isJsonNull()) {
//                            	description = dataObject.get("description").getAsString();
//                            }
//                            if (!dataObject.get("rain").isJsonNull()) {
//                            	description = dataObject.get("rain").getAsString();
//                            }

//                            if (!dataObject.get("speed").isJsonNull()) {
//                            	speed = dataObject.get("speed").getAsString();
//                            }
                            
                            
                            
                            Forecast forecast = new Forecast(time, temp, temp_min, temp_max, humidity, pressure, speed);

                            //Forecast forecast = new Forecast(time, temp);
                            forecasts.add(forecast);
                        }
                    } else {
                        logger.info("Forecast response body is null");
                    }
                    callback.onLoaded(forecasts);
                }

                public void onFailure(Call<JsonObject> call, Throwable t) {
                    callback.onLoaded(forecasts);
                }
            });
        }
    }

    /**
     * Interface for use with Retrofit to get weather data.
     */
    private interface WeatherService {
        @GET
        Call<JsonObject> loadObservations(@Url String url);

        // The type is for optionally entering "daily" or some other extension to the path
        @GET("{type}")
        Call<JsonObject> loadForecastsFromOpenWeatherMap(@Path("type") String type,
                                                         @QueryMap Map<String, String> options);

        @GET("{apikey}/{lat},{lon}")
        Call<JsonObject> loadForecastsFromForecastIo(@Path("apikey") String apikey,
                                                     @Path("lat") String lat,
                                                     @Path("lon") String lon,
                                                     @Query("units") String units);
    }

    /**
     * Get current favourites.
     *
     * @return list of favourite stations.
     */
    @SuppressWarnings("unchecked")
    static List<Station> getFavourites() {
        List<Station> restoredFavs = new ArrayList<Station>();
        // deserialize the favourites.ser file
        try (InputStream file = new FileInputStream("favourites.ser");
             InputStream buffer = new BufferedInputStream(file);
             ObjectInput input = new ObjectInputStream(buffer);) {
            // deserialize the List
            restoredFavs = (List<Station>) input.readObject();
            // display its data
            for (Station s : restoredFavs) {
                logger.info("Recovered Favourite: " + s);
            }
        } catch (FileNotFoundException e) {
            return new ArrayList<Station>();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            e.printStackTrace();
        }
        return restoredFavs;
    }

    /**
     * Remove a favourite station.
     *
     * @param favourite the station to remove.
     */
    static void removeFavouriteStation(Station favourite) {
        List<Station> list = getFavourites();

        if (list.contains(favourite)) {
            list.remove(favourite);

            try (OutputStream file = new FileOutputStream("favourites.ser");
                 OutputStream buffer = new BufferedOutputStream(file);
                 ObjectOutput output = new ObjectOutputStream(buffer);) {
                output.writeObject(list);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

}
