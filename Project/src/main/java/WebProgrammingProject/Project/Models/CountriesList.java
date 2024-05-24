package WebProgrammingProject.Project.Models;

import lombok.Data;
import org.springframework.data.redis.core.RedisHash;

import java.io.Serializable;
import java.util.ArrayList;

@RedisHash
@Data
public class CountriesList implements Serializable {
    ArrayList<CountryName> countryNames;
    int count;

    public CountriesList(ArrayList<CountryName> countryNames, int count) {
        this.countryNames = countryNames;
        this.count = count;
    }
}
