package project.parse;

import com.opencsv.bean.CsvToBeanBuilder;
import project.dto.CountryCsv;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;

public class CountryParser {

    private static final String PATH = "src/main/resources/CountryCsv.csv";

    public List<CountryCsv> readData() {
        try {
            return new CsvToBeanBuilder<CountryCsv>(Files.newBufferedReader(Paths.get(PATH)))
                    .withType(CountryCsv.class)
                    .withIgnoreLeadingWhiteSpace(true)
                    .withSkipLines(1)
                    .build()
                    .parse();
        } catch (IOException e) {
            System.err.println("Error reading CSV file: " + e.getMessage());
            return Collections.emptyList();
        }
    }

}