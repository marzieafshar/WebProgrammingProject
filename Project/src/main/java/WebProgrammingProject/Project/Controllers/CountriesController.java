package WebProgrammingProject.Project.Controllers;


import WebProgrammingProject.Project.Models.CityWeatherData;
import WebProgrammingProject.Project.Models.CountriesList;
import WebProgrammingProject.Project.Models.CountryData;
import WebProgrammingProject.Project.Services.CountriesService;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/countries")
public class CountriesController {

    public CountriesService service = new CountriesService();

    @GetMapping
    @CacheEvict(value = "countries", allEntries = true)
    public ResponseEntity<CountriesList> getCountriesList() {
        try {
            return ResponseEntity.ok().body(service.getCountriesInfoFromExternalService());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(null);
        }
    }

    @GetMapping("/{countryName}")
    @CacheEvict(value = "{countryName} info", allEntries = true)
    public ResponseEntity<CountryData> getCountryInfo(@PathVariable("countryName") String countryName) {
        try {
            CountryData countryData = service.getCountyInfo(countryName);
            return ResponseEntity.ok().body(countryData);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(null);
        }
    }

    @GetMapping("/{countryName}/weather")
    @CacheEvict("{countryName}'s capital weather info")
    public ResponseEntity<CityWeatherData> getCountryCapitalWeatherInfo(@PathVariable("countryName") String countryName) {
        try {
            return ResponseEntity.ok().body(service.getCountryCapitalWeatherInfo(countryName));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(null);
        }
    }
}
