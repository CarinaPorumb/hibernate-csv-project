package project.dto;

import com.opencsv.bean.CsvBindByPosition;
import lombok.Data;
import project.entity.Continent;

@Data
public class CountryCsv {

    @CsvBindByPosition(position = 0)
    private String name;

    @CsvBindByPosition(position = 1)
    private String governmentType;

    @CsvBindByPosition(position = 2)
    private String currency;

    @CsvBindByPosition(position = 3)
    private Integer population;

    @CsvBindByPosition(position = 4)
    private Long area;

    @CsvBindByPosition(position = 5)
    private Continent continent;
}