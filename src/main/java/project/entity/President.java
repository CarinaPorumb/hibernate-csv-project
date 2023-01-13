package project.entity;

import com.github.javafaker.Animal;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
public class President {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(nullable = false)
    private String name;

    private Integer age;

    @ToString.Exclude
    @OneToOne(mappedBy = "president", cascade = CascadeType.ALL)
    private Country country;

}