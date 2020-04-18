package dao.entities;

import javax.persistence.*;
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
    @Column(name="FoodChain_Id")
    private Long id;

    //@Size(min = 2)
    @NotNull(message = "Animals cannot be null")
    @OneToMany(cascade = {CascadeType.ALL},
                mappedBy = "foodChain")
    @OrderBy("indexInFoodChain")
    private List<AnimalInFoodChain> animals = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<AnimalInFoodChain> getAnimals() {
        return animals;
    }

    public void setAnimals(List<AnimalInFoodChain> animals) {
        this.animals = animals;
    }

    public void setAnimalsFromList(List<Animal> animals) {
        this.animals.clear();

        for (int i = 0; i < animals.size(); i++) {
            Animal animal = animals.get(i);
            AnimalInFoodChain tmp = new AnimalInFoodChain();
            tmp.setAnimal(animal);
            tmp.setFoodChain(this);
            tmp.setIndexInFoodChain(i);

            this.animals.add(tmp);
        }
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

