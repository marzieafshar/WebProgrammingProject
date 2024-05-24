package WebProgrammingProject.Project.Controllers;


import WebProgrammingProject.Project.Models.CityWeatherData;
import WebProgrammingProject.Project.Models.CountriesList;
import WebProgrammingProject.Project.Models.CountryData;
import WebProgrammingProject.Project.Services.CountriesService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;


@RestController
@RequestMapping("/countries")
public class CountriesController {

    public CountriesService service = new CountriesService();

    @GetMapping
    ResponseEntity<CountriesList> getCountriesList() {
        try {
            return ResponseEntity.ok().body(service.getCountriesInfoFromExternalService());
        } catch (Exception e) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/{countryName}")
    ResponseEntity<CountryData> getCountryInfo(@PathVariable("countryName") String countryName) {
        try {
            CountryData countryData = service.getCountyInfo(countryName);
            return ResponseEntity.ok().body(countryData);
        } catch (Exception e) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/{countryName}/weather")
    ResponseEntity<CityWeatherData> getCountryCapitalWeatherInfo(@PathVariable("countryName") String countryName) {
        try {
            return ResponseEntity.ok().body(service.getCountryCapitalWeatherInfo(countryName));
        } catch (Exception e) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND);
        }
    }
}
