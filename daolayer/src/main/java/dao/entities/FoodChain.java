package dao.entities;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Created by Ondrej Slimak on 25/03/2020.
 */

@Entity()
@Table(name = "FoodChain")
public class FoodChain implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(min = 2)
    @NotNull(message = "Animals cannot be null")
    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
            name = "FoodChain_Animals",
            joinColumns = { @JoinColumn(name = "foodchain_id")},
            inverseJoinColumns = {@JoinColumn(name="animal_id")}
            )
    private List<Animal> Animals = new ArrayList<Animal>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<Animal> getAnimals() {
        return Animals;
    }

    public void setAnimals(List<Animal> animals) {
        Animals = animals;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof FoodChain)) return false;
        FoodChain foodChain = (FoodChain) o;
        return getAnimals().equals(foodChain.getAnimals());
    }

    @Override
    public int hashCode() {
        return Objects.hash( getAnimals());
    }

}

