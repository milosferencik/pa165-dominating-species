package cz.muni.fi.dto;

import java.util.Objects;

public class AnimalDTO {
    private Long id;
    private String name;
    private String species;
    private EnvironmentDTO environment;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public EnvironmentDTO getEnvironment() {
        return environment;
    }

    public void setEnvironment(EnvironmentDTO environment) {
        this.environment = environment;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AnimalDTO)) return false;
        AnimalDTO animal = (AnimalDTO) o;
        return getName().equals(animal.getName()) &&
                getSpecies().equals(animal.getSpecies()) &&
                getEnvironment().equals(animal.getEnvironment());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName(), getSpecies(), getEnvironment());
    }
}
