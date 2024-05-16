package WebProgrammingProject.Project.Controllers;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/countries")
public class CountriesController {

    @GetMapping
    public String getCountryNames(){
        return "Just Iran!";
    }

    @GetMapping("/{countryName}")
    public String getCountryInfo(@PathVariable("countryName") String countryName){
        return countryName + " is a good country!";
    }

    @GetMapping("/{countryName}/weather")
    public String getCountryCapitalWeatherInfo(@PathVariable("countryName") String countryName){
        return "The weather of " + countryName + "'s capital is great!";
    }
}
