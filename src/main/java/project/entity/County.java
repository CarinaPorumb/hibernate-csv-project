package project.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class County {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private String name;

    public County(String name) {
        this.name = name;
    }

    @ManyToOne
    @JoinTable(
            name = "country_county",
            joinColumns = @JoinColumn(name = "county_id"),
            inverseJoinColumns = @JoinColumn(name = "country_id"))
    private Country country;

    @Override
    public String toString() {
        return "County{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", country=" + country.getName() +
                '}';
    }
}