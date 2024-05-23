package project;

import project.dao.EntityDAOImpl;
import project.entity.City;
import project.entity.Continent;
import project.entity.Country;
import project.entity.Language;
import project.parse.CsvParsingService;

public class Main {

    public static void main(String[] args) {

        initializeData();

        CsvParsingService csvParsingService = new CsvParsingService();
        csvParsingService.parseCountries();

        EntityDAOImpl<City, Long> cityDAO = new EntityDAOImpl<>(City.class);
        EntityDAOImpl<Country, Long> countryDAO = new EntityDAOImpl<>(Country.class);

        Country romania = countryDAO.findByName("Romania").orElseThrow(() -> new RuntimeException("Country not found"));
        City bucharest = City.builder().name("Bucharest").isCapital(true).country(romania).build();
        cityDAO.create(bucharest);

        Country portugal = countryDAO.findByName("Portugal").orElseThrow(() -> new RuntimeException("Country not found"));
        City lisbon = City.builder().name("Lisbon").isCapital(true).country(portugal).build();
        cityDAO.create(lisbon);

        Country france = countryDAO.findByName("France").orElseThrow(() -> new RuntimeException("Country not found"));
        City paris = City.builder().name("Paris").isCapital(true).country(france).build();
        cityDAO.create(paris);

        Country germany = countryDAO.findByName("Germany").orElseThrow(() -> new RuntimeException("Country not found"));
        City berlin = City.builder().name("Berlin").isCapital(true).country(germany).build();
        cityDAO.create(berlin);

        csvParsingService.parseTouristAttractions();

        countryDAO.getAll().forEach(System.out::println);
        cityDAO.getAll().forEach(System.out::println);
        csvParsingService.getAttractionDAO().getAll().forEach(System.out::println);
    }

    public static void initializeData() {
        EntityDAOImpl<Continent, Long> continentDAO = new EntityDAOImpl<>(Continent.class);
        EntityDAOImpl<Language, Long> languageDAO = new EntityDAOImpl<>(Language.class);

        Continent europe = new Continent("Europe");
        Continent asia = new Continent("Asia");
        Continent northAmerica = new Continent("North America");

        continentDAO.create(europe);
        continentDAO.create(asia);
        continentDAO.create(northAmerica);

        Language english = new Language(null, "English", true, null);
        Language french = new Language(null, "French", true, null);
        Language german = new Language(null, "German", true, null);

        languageDAO.create(english);
        languageDAO.create(french);
        languageDAO.create(german);
    }


}