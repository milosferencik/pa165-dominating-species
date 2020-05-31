package cz.muni.fi.pa165.dominatingspecies.api.dto;

import java.util.Objects;

/**
 * @author Milos Ferencik 13/5/2020
 */
public class AnimalUpdateDTO {
    private Long id;
    private String name;
    private String species;
    private Long environmentId;

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
        AnimalUpdateDTO that = (AnimalUpdateDTO) o;
        return Objects.equals(getId(), that.getId()) &&
                Objects.equals(getName(), that.getName()) &&
                Objects.equals(getSpecies(), that.getSpecies()) &&
                Objects.equals(getEnvironmentId(), that.getEnvironmentId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getName(), getSpecies(), getEnvironmentId());
    }

    @Override
    public String toString() {
        return "AnimalUpdateDTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", species='" + species + '\'' +
                ", environmentId=" + environmentId +
                '}';
    }
}
