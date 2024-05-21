package project;

import project.dao.CountryDAOImpl;
import project.dao.EntityDAO;
import project.dto.CountryCsv;
import project.entity.Country;
import project.mapper.CountryMapper;
import project.mapper.CountryMapperImpl;
import project.parse.CountryParser;

import java.util.List;

public class Main {

    public static void main(String[] args) {

//        CountryDAOImpl countryDAO = new CountryDAOImpl();
//        CountryParser countryParser = new CountryParser();
//
//        List<CountryCsv> countryCsvList = countryParser.readData();
//
//        CountryMapper countryMapper = new CountryMapperImpl();
//
//        List<Country> countries = countryCsvList
//                .stream()
//                .map(countryMapper::countryCsvToCountry)
//                .toList();
//
//        EntityDAO<Country, Long> entityDAO = countryDAO;
//        entityDAO.createAll(countries);
    }
}