package data;

/**
 * Created by michael on 5/04/16.
 *
 * Holds weather repositories. Currently just have one (memory version).
 */
public class WeatherRepositories {

    private static WeatherRepository repository = null;

    public synchronized static WeatherRepository getInMemoryRepoInstance(WeatherServiceApi weatherServiceApi) {
        if (repository == null) {
            repository = new InMemoryWeatherRepository(weatherServiceApi);
        }
        return repository;
    }
}
