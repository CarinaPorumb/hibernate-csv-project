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
public class Country {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private String name;

    private String capital;

    @OneToOne(cascade = CascadeType.ALL)
    private President president;

    @ManyToOne
    @JoinTable(
            name = "continent_country",
            joinColumns = @JoinColumn(name = "country_id"),
            inverseJoinColumns = @JoinColumn(name = "continent_id"))
    private Continent continent;


    @OneToMany(mappedBy = "country", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<County> counties = new HashSet<>();


    public void addCounty(County county) {
        counties.add(county);
        county.setCountry(this);
    }

    @Override
    public String toString() {
        return "Country{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", capital='" + capital + '\'' +
                ", continent=" + continent.getName() +
                '}';
    }
}