package cz.muni.fi.pa165.dominatingspecies.api.dto;

import java.util.Objects;

/**
 * @author Milos Ferencik 24/4/2020
 */
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
        if (o == null || getClass() != o.getClass()) return false;
        AnimalDTO animalDTO = (AnimalDTO) o;
        return Objects.equals(getId(), animalDTO.getId()) &&
                Objects.equals(getName(), animalDTO.getName()) &&
                Objects.equals(getSpecies(), animalDTO.getSpecies()) &&
                Objects.equals(getEnvironment(), animalDTO.getEnvironment());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getName(), getSpecies(), getEnvironment());
    }
}
