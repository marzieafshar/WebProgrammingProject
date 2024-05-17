package WebProgrammingProject.Project.models;

import lombok.Data;

@Data
public class CityWeatherData {

    String countryName;
    String cityName;
    float windSpeed;
    float windDegrees;
    float temperature;
    float humidity;

    public CityWeatherData(String countryName, String cityName, float windSpeed, float windDegrees, float temperature, float humidity) {
        this.countryName = countryName;
        this.cityName = cityName;
        this.windSpeed = windSpeed;
        this.windDegrees = windDegrees;
        this.temperature = temperature;
        this.humidity = humidity;
    }
}
