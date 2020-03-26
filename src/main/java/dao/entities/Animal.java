package dao.entities;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Objects;

/**
 * Created by Matusova on 26/03/2020.
 */
@Entity
public class Animal {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull(message = "Name cannot be null")
    private String name;

    @NotNull(message = "Species cannot be null")
    private String species;

    @ManyToOne (cascade=CascadeType.ALL)
    @NotNull(message = "Environment cannot be null")
    private Environment environment;

    public Animal(Long id, String name, String species, Environment environment) {
        this.id = id;
        this.name = name;
        this.species = species;
        this.environment = environment;
    }

    public Animal(){
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSpecies() {
        return species;
    }

    public void setSpecies(String species) {
        this.species = species;
    }

    public Environment getEnvironment() {
        return environment;
    }

    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName(), getSpecies(), getEnvironment());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Animal animal = (Animal) o;
        return  getName().equals(animal.getName()) &&
                getSpecies().equals(animal.getSpecies()) &&
                getEnvironment().equals(animal.getEnvironment());
    }

}
