package WebProgrammingProject.Project.Models;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class CountryData {
    @Id
    String countryName;
    String capital;
    String iso2;
    float population;
    float populationGrowth;
    String currency;
}
