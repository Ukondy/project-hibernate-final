package io.sancta.sanctorum.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level= AccessLevel.PRIVATE)
@Entity
@Table(schema="world", name="city")
@Getter
@Setter
public class City {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    Integer id;
    String name;
    @ManyToOne
    @JoinColumn(name="country_id")
    Country country;
    String district;
    Integer population;
}
