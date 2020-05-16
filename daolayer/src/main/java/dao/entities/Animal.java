package dao.entities;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


/**
 * @author Matusova on 26/03/2020.
 */
@Entity
@Table(name = "Animal")
public class Animal implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="Animal_Id")
    private Long id;

    @NotNull(message = "FoodChain cannot be null")
    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE},
                mappedBy = "animal")
    private List<AnimalInFoodChain> foodChains = new ArrayList<>();

    @Column(unique=true)
    @NotNull(message = "Name cannot be null")
    @NotEmpty(message = "Name cannot be empty")
    private String name;

    @NotNull(message = "Species cannot be null")
    @NotEmpty(message = "Species cannot be empty")
    private String species;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @NotNull(message = "Environment cannot be null")
    private Environment environment;

    public Animal(Long id, String name, String species, Environment environment) {
        this.id = id;
        this.name = name;
        this.species = species;
        this.environment = environment;
    }

    public Animal() {
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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Animal)) return false;
        Animal animal = (Animal) o;
        return getName().equals(animal.getName()) &&
                getSpecies().equals(animal.getSpecies()) &&
                getEnvironment().equals(animal.getEnvironment());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName(), getSpecies(), getEnvironment());
    }
}
