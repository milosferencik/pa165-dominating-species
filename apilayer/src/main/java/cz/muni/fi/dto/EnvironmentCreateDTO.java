package cz.muni.fi.dto;

/**
 * Created by Kostka on 25/04/2020.
 */
public class EnvironmentCreateDTO {
    private String name;
    private String description;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
