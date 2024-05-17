package WebProgrammingProject.Project.models;

import lombok.Data;

import java.util.ArrayList;

@Data
public class CountriesList {
    ArrayList<CountryName> countryNames;
    int count;

    public CountriesList(ArrayList<CountryName> countryNames, int count) {
        this.countryNames = countryNames;
        this.count = count;
    }
}
