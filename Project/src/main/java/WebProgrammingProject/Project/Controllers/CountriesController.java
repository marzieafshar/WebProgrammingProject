package WebProgrammingProject.Project.Controllers;


import WebProgrammingProject.Project.Services.CountriesService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/countries")
public class CountriesController {

    public CountriesService service = new CountriesService();

    @RequestMapping(value = "", method = RequestMethod.GET)
    public String getCountriesList() {
        try {
            return service.getCountriesInfoFromExternalService();
        } catch (Exception e) {
            return "Sorry... We are facing a problem";
        }
    }

    @RequestMapping(value = "/{countryName}", method = RequestMethod.GET)
    public String getCountryInfo(@PathVariable("countryName") String countryName) {
        try {
            return service.getCountyInfo(countryName);
        } catch (Exception e) {
            return "Sorry... We are facing a problem";
        }
    }

    @RequestMapping(value = "/{countryName}/weather", method = RequestMethod.GET)
    public String getCountryCapitalWeatherInfo(@PathVariable("countryName") String countryName) {
        try {
            return service.getCountryCapitalWeatherInfo(countryName);
        } catch (Exception e) {
            return "Sorry... We are facing a problem";
        }
    }
}
