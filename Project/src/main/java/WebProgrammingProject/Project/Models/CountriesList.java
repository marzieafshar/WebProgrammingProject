package WebProgrammingProject.Project.Models;

import jakarta.persistence.Id;
import lombok.Data;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.ArrayList;

@Data
//@Entity
@AllArgsConstructor
@NoArgsConstructor
public class CountriesList implements Serializable {
    ArrayList<CountryName> countryNames;
    int count;
}
