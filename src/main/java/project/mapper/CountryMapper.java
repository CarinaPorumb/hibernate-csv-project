package project.mapper;

import org.mapstruct.Mapper;
import project.dto.CountryCsv;
import project.entity.Country;

@Mapper
public interface CountryMapper {

    Country countryCsvToCountry(CountryCsv csv);

    CountryCsv countryToCountryCsv(Country country);

}