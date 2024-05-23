package project.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import project.dao.EntityDAOImpl;
import project.dto.CountryCsv;
import project.entity.Continent;
import project.entity.Country;

import java.util.Optional;

@Mapper
public interface CountryMapper {

    @Mapping(source = "continentName", target = "continent", qualifiedByName = "mapContinent")
    Country countryCsvToCountry(CountryCsv csv);

    @Mapping(source = "continent.name", target = "continentName")
    CountryCsv countryToCountryCsv(Country country);

    @Named("mapContinent")
    default Continent mapContinent(String continentName) {
        if (continentName == null) {
            return null;
        }

        EntityDAOImpl<Continent, Long> continentDAO = new EntityDAOImpl<>(Continent.class);
        Optional<Continent> existingContinent = continentDAO.findByName(continentName);

        return existingContinent.orElseGet(() -> {
            Continent continent = new Continent();
            continent.setName(continentName);
            return continent;
        });
    }

}