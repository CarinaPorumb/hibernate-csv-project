package project.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@ToString
public class Continent {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String name;

    public Continent(String name) {
        this.name = name;
    }

    @OneToMany(mappedBy = "continent", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<Country> countries = new HashSet<>();

    public void addContinent(Country country) {
        countries.add(country);
        country.setContinent(this);
    }
}