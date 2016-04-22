package data;

/**
 * Holds weather repositories. Currently just have one (memory version).
 *
 * @author michael
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
