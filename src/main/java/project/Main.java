package project;

import com.opencsv.bean.CsvToBeanBuilder;
import net.datafaker.Faker;
import project.dao.ContinentDAOI;
import project.dao.CountryDAOI;
import project.dao.EntityDAO;
import project.entity.Continent;
import project.entity.Country;
import project.entity.County;
import project.entity.President;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class Main {

    private static final String PATH = "src/main/resources/CountryCsv.csv";

    public static void main(String[] args) {
        EntityDAO<Country, Integer> countries = new CountryDAOI();
        countries.createAll(createCountry());

        System.out.println(readData());
    }

    public static List<Country> createCountry() {
        List<Country> countries = readData();
        Faker faker = new Faker();

        Continent continent = new Continent("Europe");
        EntityDAO<Continent, Integer> continentDAO = new ContinentDAOI();
        continentDAO.createAndReturn(continent);
        countries.forEach(continent::addContinent);

        for (Country ctr : countries) {
            President president = new President();
            president.setName(faker.funnyName().name());
            president.setAge(faker.number().numberBetween(30, 70));
            ctr.setPresident(president);
        }

        countries.forEach(ctr -> {
            ctr.addCounty(new County("County 1"));
            ctr.addCounty(new County("County 2"));
        });
        return countries;
    }

    public static List<Country> readData() {
        List<Country> country2List = new ArrayList<>();
        try {
            return new CsvToBeanBuilder<Country>(new FileReader(PATH))
                    .withType(Country.class)
                    .withSkipLines(1)
                    .withSkipLines(5)
                    .build()
                    .parse();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return country2List;
    }
}