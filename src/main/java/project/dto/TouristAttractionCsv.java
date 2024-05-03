package project.dto;

import com.opencsv.bean.CsvBindByPosition;
import lombok.Data;

@Data
public class TouristAttractionCsv {

    @CsvBindByPosition(position = 0)
    private String name;

    @CsvBindByPosition(position = 1)
    private String description;

    @CsvBindByPosition(position = 2)
    private String type;

    @CsvBindByPosition(position = 3)
    private String cityName;

    @CsvBindByPosition(position = 4)
    private String countryName;

}