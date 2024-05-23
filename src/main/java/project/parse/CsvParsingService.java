package project.parse;

import com.opencsv.bean.CsvToBeanBuilder;
import org.mapstruct.factory.Mappers;
import project.dao.EntityDAOImpl;
import project.dto.CountryCsv;
import project.dto.TouristAttractionCsv;
import project.entity.Continent;
import project.entity.Country;
import project.entity.TouristAttraction;
import project.mapper.CountryMapper;
import project.mapper.TouristAttractionMapper;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

public class CsvParsingService {

    private static final String COUNTRY_CSV_PATH = "src/main/resources/countries.csv";
    private static final String ATTRACTION_CSV_PATH = "src/main/resources/touristAttractions.csv";

    private final CountryMapper countryMapper = Mappers.getMapper(CountryMapper.class);
    private final TouristAttractionMapper touristAttractionMapper = Mappers.getMapper(TouristAttractionMapper.class);

    private final EntityDAOImpl<Country, Long> countryDAO = new EntityDAOImpl<>(Country.class);
    private final EntityDAOImpl<TouristAttraction, Long> touristAttractionDAO = new EntityDAOImpl<>(TouristAttraction.class);
    private final EntityDAOImpl<Continent, Long> continentDAO = new EntityDAOImpl<>(Continent.class);

    public void parseCountries() {
        try {
            List<CountryCsv> countriesCsv = new CsvToBeanBuilder<CountryCsv>(Files.newBufferedReader(Paths.get(COUNTRY_CSV_PATH)))
                    .withType(CountryCsv.class)
                    .withIgnoreLeadingWhiteSpace(true)
                    .withSkipLines(1)
                    .build()
                    .parse();

            for (CountryCsv countryCsv : countriesCsv) {
                Continent continent = continentDAO.findByName(countryCsv.getContinentName())
                        .orElseGet(() -> {
                            Continent newContinent = new Continent();
                            newContinent.setName(countryCsv.getContinentName());
                            continentDAO.create(newContinent);
                            return newContinent;
                        });


                Country country = countryMapper.countryCsvToCountry(countryCsv);
                country.setContinent(continent);
                countryDAO.create(country);
            }
        } catch (Exception e) {
            System.err.println("Error reading CSV file: " + e.getMessage());
        }
    }

    public void parseTouristAttractions() {
        try {
            List<TouristAttractionCsv> attractionsCSV = new CsvToBeanBuilder<TouristAttractionCsv>(Files.newBufferedReader(Paths.get(ATTRACTION_CSV_PATH)))
                    .withType(TouristAttractionCsv.class)
                    .withSkipLines(1)
                    .build()
                    .parse();

            for (TouristAttractionCsv attractionCsv : attractionsCSV) {
                Optional<Country> countryOptional = countryDAO.findByName(attractionCsv.getCountryName());
                if (countryOptional.isPresent()) {
                    TouristAttraction attraction = touristAttractionMapper.touristAttractionCSVToTouristAttraction(attractionCsv);
                    attraction.setCountry(countryOptional.get());
                    touristAttractionDAO.create(attraction);
                } else
                    System.err.println("Country not found: " + attractionCsv.getCountryName());
            }

        } catch (Exception e) {
            System.err.println("Error reading CSV file: " + e.getMessage());
        }
    }

    public EntityDAOImpl<TouristAttraction, Long> getAttractionDAO() {
        return touristAttractionDAO;
    }

}