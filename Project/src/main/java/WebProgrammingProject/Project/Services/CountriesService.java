package WebProgrammingProject.Project.Services;

import WebProgrammingProject.Project.models.CityWeatherData;
import WebProgrammingProject.Project.models.CountriesList;
import WebProgrammingProject.Project.models.CountryData;
import WebProgrammingProject.Project.models.CountryName;
import org.json.JSONArray;
import org.json.JSONObject;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;

public class CountriesService {

    public CountriesList getCountriesInfoFromExternalService() throws Exception {
        String getCountryInfoURI = "https://countriesnow.space/api/v0.1/countries";
        String countriesInfo = sendRequest(getCountryInfoURI);
        JSONObject jsonObject = new JSONObject(countriesInfo);
        String data = jsonObject.getString("data");
        JSONArray jsonArray = new JSONArray(data);
        ArrayList<CountryName> countries = new ArrayList<>();
        for (int i = 0; i < jsonArray.length(); i++) {
            String countryName = jsonArray.getJSONObject(i).getString("country");
            countries.add(new CountryName(countryName));
        }
        return new CountriesList(countries, jsonArray.length());
    }

    public CountryData getCountyInfo(String countryName) throws Exception {
        String countryInfo = requestCountryInfo(countryName);
        JSONObject jsonObject = (new JSONArray(countryInfo)).getJSONObject(0);
        String capital = jsonObject.getString("capital");
        String iso2 = jsonObject.getString("iso2");
        String population = jsonObject.getString("population");
        String populationGrowth = jsonObject.getString("pop_growth");
        String currency = jsonObject.getString("currency");
        return new CountryData(countryName, capital, iso2, Float.parseFloat(population), Float.parseFloat(populationGrowth), currency);
    }

    private String requestCountryInfo(String countryName) throws Exception {
        String getCountryInfoURI = "https://api.api-ninjas.com/v1/country?name=" + countryName;
        return sendRequest(getCountryInfoURI);
    }

    public CityWeatherData getCountryCapitalWeatherInfo(String countryName) throws Exception {
        String capital = findCountryCapital(countryName);
        String capitalWeatherInfo = getCityWeatherInfo(capital);
        JSONObject JSONObject = new JSONObject(capitalWeatherInfo);
        String windSpeed = JSONObject.getString("wind_speed");
        String windDegrees = JSONObject.getString("wind_degrees");
        String temperature = JSONObject.getString("temp");
        String humidity = JSONObject.getString("humidity");
        return new CityWeatherData(countryName, capital, Float.parseFloat(windSpeed), Float.parseFloat(windDegrees), Float.parseFloat(temperature), Float.parseFloat(humidity));
    }

    private String findCountryCapital(String countryName) throws Exception {
        String countryInfo = requestCountryInfo(countryName);
        JSONArray JSONArray = new JSONArray(countryInfo);
        return JSONArray.getJSONObject(0).getString("capital");
    }

    private String getCityWeatherInfo(String cityName) throws Exception {
        String getCityInfoURI = "https://api.api-ninjas.com/v1/weather?city=" + cityName;
        return sendRequest(getCityInfoURI);
    }


    private String sendRequest(String URI) throws Exception {
        String APIKeyHeaderKey = "X-Api-Key";
        String APIKeyHeaderValue = "Ifx4yLgvYaWvdJNG2ekCyA==emuebVb3i5XZ65pz";
        HttpRequest request = HttpRequest.newBuilder(new URI(URI)).header(APIKeyHeaderKey, APIKeyHeaderValue).GET().build();
        HttpResponse<String> response;
        try (HttpClient client = HttpClient.newBuilder().build()) {
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
        }
        return response.body();
    }
}
