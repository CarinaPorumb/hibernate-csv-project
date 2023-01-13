package project;

import com.github.javafaker.Faker;
import project.dao.ContinentDAOI;
import project.dao.CountryDAOI;
import project.dao.EntityDAO;
import project.entity.Continent;
import project.entity.Country;
import project.entity.County;
import project.entity.President;

import java.util.HashSet;
import java.util.Set;

public class Main {
    public static void main(String[] args) {
        EntityDAO<Country, Integer> countries = new CountryDAOI();
        countries.createAll(createCountry());

    }

    public static Set<Country> createCountry() {
        Set<Country> countries = new HashSet<>();
        EntityDAO<Country, Integer> countryDAO = new CountryDAOI();
        Faker faker = new Faker();

        Country country = new Country();
        country.setName("Portugal");
        country.setCapital("Lisbon");
        countries.add(country);

        Country country1 = new Country();
        country1.setName("France");
        country1.setCapital("Paris");
        countries.add(country1);

        Country country2 = new Country();
        country2.setName("Italy");
        country2.setCapital("Rome");
        countries.add(country2);

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
}