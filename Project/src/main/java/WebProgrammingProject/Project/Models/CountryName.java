package WebProgrammingProject.Project.models;

import lombok.Data;

@Data
public class CountryName {
    String name;

    public CountryName(String countryName) {
        this.name = countryName;
    }
}
