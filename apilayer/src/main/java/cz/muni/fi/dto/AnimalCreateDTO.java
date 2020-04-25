package cz.muni.fi.dto;

import java.util.Objects;

/**
 * @author Milos Ferencik 24/4/2020
 */
public class AnimalCreateDTO {

    private String name;

    private String species;

    private Long environmentId;

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

    public Long getEnvironmentId() {
        return environmentId;
    }

    public void setEnvironmentId(Long environmentId) {
        this.environmentId = environmentId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AnimalCreateDTO that = (AnimalCreateDTO) o;
        return Objects.equals(getName(), that.getName()) &&
                Objects.equals(getSpecies(), that.getSpecies()) &&
                Objects.equals(getEnvironmentId(), that.getEnvironmentId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName(), getSpecies(), getEnvironmentId());
    }
}
