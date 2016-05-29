package data;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Holds weather repositories. Currently just have one (memory version).
 *
 * @author michael
 */
public class WeatherRepositories {

    private static final Logger logger = LogManager.getLogger(data.WeatherRepositories.class);
    private static WeatherRepository repository = null;

    public synchronized static WeatherRepository getInMemoryRepoInstance(WeatherServiceApi weatherServiceApi) {
        if (repository == null) {
            repository = new InMemoryWeatherRepository(weatherServiceApi);
        }
        return repository;
    }
}
