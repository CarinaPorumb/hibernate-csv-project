package project.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import project.dto.TouristAttractionCsv;
import project.entity.City;
import project.entity.Country;
import project.entity.TouristAttraction;

@Mapper
public interface TouristAttractionMapper {

    @Mapping(source = "countryName", target = "country", qualifiedByName = "mapCountry")
    @Mapping(source = "cityName", target = "city", qualifiedByName = "mapCity")
    TouristAttraction touristAttractionCSVToTouristAttraction(TouristAttractionCsv csv);

    @Mapping(source = "country.name", target = "countryName")
    @Mapping(source = "city.name", target = "cityName")
    TouristAttractionCsv touristAttractionToTouristAttractionCsv(TouristAttraction touristAttraction);

    @Named("mapCountry")
    default Country mapCountry(String countryName) {
        if (countryName == null) {
            return null;
        }
        Country country = new Country();
        country.setName(countryName);
        return country;
    }

    @Named("mapCity")
    default City mapCity(String cityName) {
        if (cityName == null) {
            return null;
        }
        City city = new City();
        city.setName(cityName);
        return city;
    }
}