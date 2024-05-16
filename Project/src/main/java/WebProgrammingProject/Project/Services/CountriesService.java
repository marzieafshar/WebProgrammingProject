package WebProgrammingProject.Project.Services;

import org.json.JSONArray;
import org.json.JSONObject;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class CountriesService {

    public String getCountriesInfoFromExternalService() throws Exception {
        String getCountryInfoURI = "https://countriesnow.space/api/v0.1/countries";
        String countriesInfo = sendRequest(getCountryInfoURI);
        JSONObject jsonObject = new JSONObject(countriesInfo);
        String data = jsonObject.getString("data");
        JSONArray jsonArray = new JSONArray(data);
        StringBuilder string = new StringBuilder();
        for (int i = 0; i < jsonArray.length(); i++) {
            String countryName = jsonArray.getJSONObject(i).getString("country");
            string.append("name: ").append(countryName).append("\n");
        }
        return String.valueOf(string);
    }

    public String getCountyInfo(String countryName) throws Exception {
        String countryInfo = requestCountryInfo(countryName);
        JSONObject jsonObject = (new JSONArray(countryInfo)).getJSONObject(0);
        String capital = jsonObject.getString("capital");
        String iso2 = jsonObject.getString("iso2");
        String population = jsonObject.getString("population");
        String populationGrowth = jsonObject.getString("pop_growth");
        String currency = jsonObject.getString("currency");
        return "name: " + countryName + "\n" +
                "capital: " + capital + "\n" +
                "iso2: " + iso2 + "\n" +
                "population: " + population + "\n" +
                "pop_growth: " + populationGrowth + "\n" +
                "currency: " + currency;
    }

    private String requestCountryInfo(String countryName) throws Exception {
        String getCountryInfoURI = "https://api.api-ninjas.com/v1/country?name=" + countryName;
        return sendRequest(getCountryInfoURI);
    }

    public String getCountryCapitalWeatherInfo(String countryName) throws Exception {
        String capital = findCountryCapital(countryName);
        String capitalWeatherInfo = getCityWeatherInfo(capital);
        JSONObject JSONObject = new JSONObject(capitalWeatherInfo);
        String windSpeed = JSONObject.getString("wind_speed");
        String windDegrees = JSONObject.getString("wind_degrees");
        String temperature = JSONObject.getString("temp");
        String humidity = JSONObject.getString("humidity");
        return "country_name: " + countryName + "\n" +
                "capital: " + capital + "\n" +
                "wind_speed: " + windSpeed + "\n" +
                "wind_degrees: " + windDegrees + "\n" +
                "temp: " + temperature + "\n" +
                "humidity: " + humidity;
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
