package cz.muni.fi.dto;

import com.sun.istack.internal.NotNull;

public class AnimalCreateDTO {

    @NotNull
    private String name;

    @NotNull
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
}
