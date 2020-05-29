package cz.muni.fi.pa165.dominatingspecies.api.dto;

import java.util.Objects;

/**
 * @author Milos Ferencik 24/4/2020
 */
public class AnimalListDTO {
    private Long id;
    private String name;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AnimalListDTO)) return false;
        AnimalListDTO that = (AnimalListDTO) o;
        return getId().equals(that.getId()) &&
                getName().equals(that.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getName());
    }
}
