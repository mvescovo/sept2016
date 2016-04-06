package data;

import com.sun.istack.internal.NotNull;

/**
 * Created by michael on 5/04/16.
 *
 * Holds weather repositories. Currently just have one (memory version).
 */
public class WeatherRepositories {

    private WeatherRepositories() {
        // no instance
    }

    private static WeatherRepository repository = null;

    public synchronized static WeatherRepository getInMemoryRepoInstance(@NotNull WeatherServiceApi weatherServiceApi) {
        if (repository == null) {
            repository = new InMemoryWeatherRepository(weatherServiceApi);
        }
        return repository;
    }
}
