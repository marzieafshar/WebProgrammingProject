package WebProgrammingProject.Project.models;


import lombok.Data;

@Data
public class CountryData {
    String countryName;
    String capital;
    String iso2;
    float population;
    float populationGrowth;
    String currency;

    public CountryData(String countryName, String capital, String iso2, float population, float populationGrowth, String currency) {
        this.countryName = countryName;
        this.capital = capital;
        this.iso2 = iso2;
        this.population = population;
        this.populationGrowth = populationGrowth;
        this.currency = currency;
    }
}
