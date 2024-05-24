package WebProgrammingProject.Project.Repositories;

import WebProgrammingProject.Project.Models.CountryData;
import org.springframework.data.repository.CrudRepository;

public interface CountryInfoRepository extends CrudRepository<CountryData, String> {
}
